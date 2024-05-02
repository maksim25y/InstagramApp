package ru.mudan.SeansService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "seans")
@Data
public class Seans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    @NotEmpty(message = "Заголовок не может быть пустым")
    private String title;
    @Column(name = "user_id")
    @NotNull(message = "Пациент отсутствует")
    private Long userId;
    @Column(name = "doctor_id")
    @NotNull(message = "Врач отсутствует")
    private Long doctorId;
    public Seans(){

    }
}
