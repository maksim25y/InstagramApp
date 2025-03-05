package com.example.demo.security;

import com.example.demo.payload.response.InvalidLoginResponse;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Возвращает объект - что параметры которые внесены неверные
//Ловит ошибки авторизации - ошибка 401
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException{
        //Создание риспонса
        InvalidLoginResponse loginResponse = new InvalidLoginResponse();
        //Перевод риспонса в json строку
        String jsonLoginResponse = new Gson().toJson(loginResponse);
        //Установка в риспонсе типа application/json
        httpServletResponse.setContentType(SecurityConstants.CONTENT_TYPE);
        //Установка в статусе риспонса 401 - ошибка (для доступа нужна авторизация)
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        //Запись json-строки в риспонс
        httpServletResponse.getWriter().println(jsonLoginResponse);
    }
}
