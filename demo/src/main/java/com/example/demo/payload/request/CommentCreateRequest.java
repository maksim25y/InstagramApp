package com.example.demo.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Schema(description = "Данные для создания комментария")
public class CommentCreateRequest {
    @NotEmpty(message = "Текст комментария должен быть указан")
    @Schema(description = "Текст комментария", example = "Норм")
    private String message;
}
