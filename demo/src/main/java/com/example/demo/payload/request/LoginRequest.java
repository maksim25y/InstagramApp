package com.example.demo.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

//Передаем на сервер когда пытается юзер авторизироваться
@Data
@Schema(description = "Данные для входа в аккаунт")
public class LoginRequest {
    @Schema(description = "Имя пользователя (email)", example = "demelist@mail.ru")
    @NotEmpty(message = "Имя пользователя не может быть пустым")
    private String username;
    @Schema(description = "Пароль пользователя", example = "password123")
    @NotEmpty(message = "Пароль должен быть указан")
    private String password;
}
