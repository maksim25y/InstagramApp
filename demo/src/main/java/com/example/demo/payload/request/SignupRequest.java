package com.example.demo.payload.request;

import com.example.demo.annotations.PasswordMatches;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
@Schema(description = "Данные для регистрации")
public class SignupRequest {
    @Email(message = "Некорректный формат email")
    @NotBlank(message = "Адрес электронной почты пользователя должен быть указан")
    @Schema(description = "email пользователя", example = "demelist@mail.ru")
    private String email;
    @Schema(description = "Имя пользователя", example = "Иван")
    @NotEmpty(message = "Имя пользователя должно быть указано")
    private String firstname;
    @Schema(description = "Фамилия пользователя", example = "Иванов")
    @NotEmpty(message = "Фамилия пользователя должна быть указана")
    private String lastname;
    @Schema(description = "Ник пользователя", example = "demelist")
    @NotEmpty(message = "Ник пользователя должен быть указан")
    private String username;
    @NotEmpty(message = "Пароль должен быть указан")
    @Size(min = 6, message = "Длина пароля от 6 символов")
    @Schema(description = "Пароль пользователя", example = "password123")
    private String password;
    @Schema(description = "Повторение пароля", example = "password123")
    private String confirmPassword;
}
