package com.example.demo.security;


import io.jsonwebtoken.SignatureAlgorithm;

//Набор констант - которые нужны для security
public class SecurityConstants {
    //При каком url срабатывает
    public static final String SIGN_UP_URLS = "/api/auth/**";
    //Секрет для jwt
    public static final String SECRET = "dskfjsdkfhsdfhjsdhfjsdhfjsdfjdfhdjfhhj";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    //Тип ответа
    public static final String CONTENT_TYPE = "application/json";
    //Время хранения токена на стороне клиента
    public static final long EXPIRATION_TIME = 600_000;//10 min
}
