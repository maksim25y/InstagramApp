package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "encoded_image")
    private String encodedImage;
    @JsonIgnore
    private Long userId;
    @JsonIgnore
    private Long postId;

    public ImageModel() {
    }
}
