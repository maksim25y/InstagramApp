package com.example.demo.payload.request;

import lombok.Data;

@Data
public class ImageCreateRequest {
    private String name;
    private String encodedImage;
}
