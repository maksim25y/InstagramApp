package com.example.demo.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
@Schema(description = "Данные для создания поста")
public class PostCreateRequest {
    @NotEmpty
    @Schema(description = "Заголовок записи", example = "Запись 1")
    private String title;
    @NotEmpty
    @Schema(description = "Описание записи", example = "Тестовое описание")
    private String caption;
    @Schema(description = "Местоположение", example = "Москва")
    private String location;
}
