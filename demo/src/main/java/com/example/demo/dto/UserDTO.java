package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
@Schema(description = "Данные о пользователе")
public class UserDTO {
    @Schema(description = "id пользователя", example = "1")
    private Long id;
    @NotEmpty
    @Schema(description = "Имя пользователя", example = "Иван")
    private String firstname;
    @NotEmpty
    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String lastname;
    @NotEmpty
    @Schema(description = "username пользователя", example = "maks2")
    private String username;
    @Schema(description = "Описание пользователя", example = "Крутой человек")
    private String bio;
}
