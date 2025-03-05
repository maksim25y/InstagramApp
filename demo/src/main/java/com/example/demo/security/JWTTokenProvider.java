package com.example.demo.security;

import com.example.demo.entity.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {
    public static final Logger LOG = LoggerFactory.getLogger(JWTTokenProvider.class);
    //Генерация токена по аутентифицированному пользователю
    public String generateToken(Authentication authentication){
        //Получение пользователя
        User user = (User) authentication.getPrincipal();
        //Время создания токена
        Date now = new Date(System.currentTimeMillis());
        //Время до которого годен токен
        Date expiryDate = new Date(now.getTime()+SecurityConstants.EXPIRATION_TIME);
        //Id пользователя
        String userId = Long.toString(user.getId());
        //Занесение значений в словарь
        Map<String, Object>claimsMap = new HashMap<>();
        claimsMap.put("id",userId);
        claimsMap.put("username",user.getEmail());
        claimsMap.put("firstname",user.getName());
        claimsMap.put("lastname",user.getLastname());
        //Создание токена и возврат
        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }
    //Валидация токена
    public boolean validateToken(String token){
        //Если спарсится без исключения - токен годный
        try{
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException |
                ExpiredJwtException |
                MalformedJwtException |
                UnsupportedJwtException|
                IllegalArgumentException ex){
        LOG.error(ex.getMessage());
        }
        return false;
    }
    //Получение id из jwt токена
    public Long getUserIdFromToken(String token){
        //Извлечение токена с помощью секрета
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
        //Получение id из токена
        String id = (String)claims.get("id");
        return Long.parseLong(id);
    }
}
