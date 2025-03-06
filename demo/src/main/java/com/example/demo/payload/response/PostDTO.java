package com.example.demo.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Schema(description = "Данные о записи")
public class PostDTO {
    @Schema(description = "id записи", example = "1")
    private Long id;
    @NotEmpty
    @Schema(description = "Заголовок записи", example = "Запись 1")
    private String title;
    @NotEmpty
    @Schema(description = "Описание записи", example = "Тестовое описание")
    private String caption;
    @Schema(description = "Местоположение", example = "Москва")
    private String location;
    @Schema(description = "username пользователя", example = "demelist")
    private String username;
    @Schema(description = "Количество лайков у записи", example = "7")
    private Integer likes;
    @Schema(description = "Лайкнувшие пользователи", example = "maks, ivan")
    private Set<String> usersLiked;
}
