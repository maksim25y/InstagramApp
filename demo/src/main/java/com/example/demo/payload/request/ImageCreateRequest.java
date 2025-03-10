package com.example.demo.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "Данные для загрузки изображения")
public class ImageCreateRequest {
    @JsonProperty("name")
    @NotNull(message = "Изображение должно иметь название")
    @Schema(description = "Название изображения", example = "image.png")
    private String name;
    @NotNull(message = "Изображение в формате base64 должно быть передано")
    @JsonProperty("encoded_image")
    @Schema(description = "Изображение в формате base64", example = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgA")
    private String encodedImage;
}
