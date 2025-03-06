package com.example.demo.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
@Schema(description = "Данные о комментарии")
public class CommentDTO {
    @Schema(description = "id комментария", example = "1")
    private Long id;
    @NotEmpty
    @Schema(description = "Текст комментария", example = "Норм")
    private String message;
    @Schema(description = "username автора комментария", example = "maks1")
    private String username;
}
