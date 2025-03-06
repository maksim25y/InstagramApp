package com.example.demo.controllers;

import com.example.demo.payload.request.CommentCreateRequest;
import com.example.demo.payload.response.CommentDTO;
import com.example.demo.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Комментарии", description = "API управления комментариями")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Добавление нового комментария",
            description = "Создаёт комментарий к записи")
    @PostMapping("/{postId}")
    public CommentDTO createComment(@Valid @RequestBody CommentCreateRequest commentCreateRequest,
                                    @PathVariable("postId") Long postId,
                                    Principal principal) {
        return commentService.saveComment(postId, commentCreateRequest, principal);
    }

    @Operation(summary = "Получение всех комментариев к записи",
            description = "Возвращает все комментарии к записи")
    @GetMapping("/{postId}/all")
    public List<CommentDTO> getAllCommentsToPost(@PathVariable("postId") Long postId) {
        return commentService.getAllCommentsForPost(postId);
    }

    @Operation(summary = "Удаление комментария",
            description = "Удаляет комментарий")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
