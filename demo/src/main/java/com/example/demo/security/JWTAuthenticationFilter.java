package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


public class JWTAuthenticationFilter extends OncePerRequestFilter {
    //Логгер для записи логов
    public static final Logger LOG = LoggerFactory.getLogger(JWTTokenProvider.class);
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    //Получение jwt из риквеста
    private String getJWTFromRequest(HttpServletRequest httpServletRequest){
        //Получение хэдера "Bearer ......"
        String bearToken = httpServletRequest.getHeader(SecurityConstants.HEADER_STRING);
        //Если строка начинается с Bearer - токен есть
        if(StringUtils.hasText(bearToken)&&bearToken.startsWith(SecurityConstants.TOKEN_PREFIX)){
            return bearToken.split(" ")[1];
        }
        return null;
    }
    //Проверка токена
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = getJWTFromRequest(request);
            //Проверка что токен валидный и не пустой
            if(StringUtils.hasText(jwt)&&jwtTokenProvider.validateToken(jwt)){
                //Получение id из токена
                Long id = jwtTokenProvider.getUserIdFromToken(jwt);
                //Загрузка юзера из БД по id
                User userDetails = customUserDetailsService.loadUserById(id);
                //Создание аутентификации на основе данных пользователя
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,null, Collections.emptyList()
                );
                //Установка деталей аутентификации
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Установка аутентификации в контексте безопасности
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            LOG.error("Could not set user authentication");
        }
        filterChain.doFilter(request,response);
    }
}
