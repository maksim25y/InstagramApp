package com.example.demo.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "Данные об изображении")
@Builder
public class ImageResponse {
    @JsonProperty("name")
    @Schema(description = "Название изображения", example = "image.png")
    private String name;
    @JsonProperty("encoded_image")
    @Schema(description = "Изображение в формате base64", example = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgA")
    private String encodedImage;
}
