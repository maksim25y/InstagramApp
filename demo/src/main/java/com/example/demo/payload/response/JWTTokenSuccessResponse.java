package com.example.demo.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Информация о токене")
public class JWTTokenSuccessResponse {
    private boolean success;
    @Schema(description = "JWT токен для аутентифицированного пользователя",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}
