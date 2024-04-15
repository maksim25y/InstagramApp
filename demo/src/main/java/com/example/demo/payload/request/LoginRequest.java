package com.example.demo.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

//Передаем на сервер когда пытается юзер авторизироваться
@Data
public class LoginRequest {
    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
