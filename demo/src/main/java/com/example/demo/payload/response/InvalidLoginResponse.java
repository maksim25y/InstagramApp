package com.example.demo.payload.response;

import lombok.Getter;
//Риспонс - который говорит, что введённые данные - неверные
@Getter
public class InvalidLoginResponse {
    private String username;
    private String password;

    public InvalidLoginResponse(){
        this.username="Invalid Username";
        this.password="Invalid Password";
    }
}
