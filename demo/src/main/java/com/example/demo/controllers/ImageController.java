package com.example.demo.controllers;

import com.example.demo.payload.request.ImageCreateRequest;
import com.example.demo.payload.response.ImageResponse;
import com.example.demo.services.ImageUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
@Tag(name = "Изображения", description = "API управления изображениями")
@RequiredArgsConstructor
public class ImageController {
    private final ImageUploadService imageUploadService;

    @Operation(summary = "Обновление изображения профиля пользователя",
            description = "Обновляет изображение профиля пользователю")
    @PostMapping("/upload")
    public ImageResponse uploadImageToUser(@RequestBody ImageCreateRequest imageCreateRequest, Principal principal) throws IOException {
        return imageUploadService.uploadImageToUser(imageCreateRequest, principal);
    }

    @Operation(summary = "Обновление изображения к посту",
            description = "Обновляет изображение поста")
    @PostMapping("/{postId}/upload")
    public ResponseEntity<Void> uploadImageToPost(@PathVariable("postId") Long postId,
                                                             @RequestBody ImageCreateRequest imageCreateRequest,
                                                             Principal principal) {
        imageUploadService.uploadImageToPost(imageCreateRequest, principal, postId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получение изображения профиля текущего пользователя",
            description = "Возвращает изображение профиля текущего пользователя")
    @GetMapping("/profileImage")
    public ImageResponse getImageForUser(Principal principal) {
        return imageUploadService.getImageToUser(principal);
    }

    @Operation(summary = "Получение изображение для поста",
            description = "Возвращает изображение для поста")
    @GetMapping("/{postId}/image")
    public ImageResponse getImageForUser(@PathVariable("postId") Long postId) {
        return imageUploadService.getImageToPost(postId);
    }
}
