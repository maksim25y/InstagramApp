package com.example.demo.web;

import com.example.demo.entity.ImageModel;
import com.example.demo.payload.request.ImageCreateRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.services.ImageUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
@Tag(name = "Изображения", description = "API управления изображениями")
public class ImageController {
    @Autowired
    private ImageUploadService imageUploadService;

    @Operation(summary = "Обновление изображения профиля пользователя",
            description = "Обновляет изображение профиля пользователю")
    @PostMapping("/upload")
    public ImageModel uploadImageToUser(@RequestBody ImageCreateRequest imageCreateRequest, Principal principal) throws IOException {
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
    public ImageModel getImageForUser(Principal principal) {
        return imageUploadService.getImageToUser(principal);
    }

    @Operation(summary = "Получение изображение для поста",
            description = "Возвращает изображение для поста")
    @GetMapping("/{postId}/image")
    public ImageModel getImageForUser(@PathVariable("postId") String postId) {
        return imageUploadService.getImageToPost(Long.parseLong(postId));
    }
}
