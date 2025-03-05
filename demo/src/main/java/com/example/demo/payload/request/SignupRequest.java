package com.example.demo.payload.request;

import com.example.demo.annotations.PasswordMatches;
import com.example.demo.annotations.ValidEmail;
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
    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @Schema(description = "email пользователя", example = "demelist")
    @ValidEmail
    private String email;
    @Schema(description = "Имя пользователя", example = "Иван")
    @NotEmpty(message = "Please enter your firstname")
    private String firstname;
    @Schema(description = "Фамилия пользователя", example = "Иванов")
    @NotEmpty(message = "Please enter your lastname")
    private String lastname;
    @Schema(description = "Ник пользователя", example = "demelist")
    @NotEmpty(message = "Please enter your username")
    private String username;
    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    @Schema(description = "Пароль пользователя", example = "password123")
    private String password;
    @Schema(description = "Повторение пароля", example = "password123")
    private String confirmPassword;
}
