package com.example.demo.web;

import com.example.demo.payload.request.PostCreateRequest;
import com.example.demo.payload.response.PostDTO;
import com.example.demo.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@CrossOrigin
@Tag(name = "Посты", description = "API управления записями")
public class PostController {
    @Autowired
    private PostService postService;

    @Operation(summary = "Создание поста нового",
            description = "Создаёт новый пост")
    @PostMapping
    public PostDTO createPost(@Valid @RequestBody PostCreateRequest postCreateRequest, Principal principal) {
        return postService.createPost(postCreateRequest, principal);
    }

    @Operation(summary = "Получение всех постов",
            description = "Возвращает все посты")
    @GetMapping("/all")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @Operation(summary = "Получение всех постов пользователя",
            description = "Возвращает все посты пользователя")
    @GetMapping("/user/posts")
    public List<PostDTO> getAllPostsForUser(Principal principal) {
        return postService.getAllPostsForUser(principal);
    }

    @Operation(summary = "Оставление лайка к посту",
            description = "Ставит или убирает лайк")
    @PostMapping("/{postId}/{username}/like")
    public PostDTO likePost(@PathVariable("postId") Long postId,
                                            @PathVariable("username") String username) {
        return postService.likePost(postId, username);
    }

    @Operation(summary = "Удаление поста по его id",
            description = "Удаляет пост по его id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id, Principal principal) {
        postService.deletePost(id, principal);
        return ResponseEntity.noContent().build();
    }

}
