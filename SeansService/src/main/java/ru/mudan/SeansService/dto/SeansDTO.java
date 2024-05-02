package ru.mudan.SeansService.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeansDTO {
    private Long id;
    @NotEmpty(message = "Заголовок не может быть пустым")
    private String title;
    @NotNull(message = "Пациент отсутствует")
    private Long userId;
    @NotNull(message = "Врач отсутствует")
    private Long doctorId;
}
