import com.example.demo.dto.CommentDTO;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.exceptions.PostNotFoundException;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Principal principal;

    @InjectMocks
    private CommentService commentService;

    private User user;
    private Post post;
    private CommentDTO commentDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создаем пользователя
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");

        // Создаем пост
        post = new Post();
        post.setId(1L);
        post.setTitle("Test Post");

        // Создаем объект CommentDTO
        commentDTO = new CommentDTO();
        commentDTO.setMessage("Test comment message");

        // Мокаем Principal
        when(principal.getName()).thenReturn(user.getUsername());
    }

    @Test
    public void testSaveCommentSuccess() {
        // Мокаем репозиторий Post
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        // Мокаем репозиторий User
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        // Мокаем репозиторий Comment
        when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(new Comment());

        Comment savedComment = commentService.saveComment(post.getId(), commentDTO, principal);

        assertNotNull(savedComment);
        verify(commentRepository, times(1)).save(Mockito.any(Comment.class));
    }

    @Test
    public void testSaveCommentUserNotFound() {
        // Мокаем репозиторий Post
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        // Мокаем репозиторий User, чтобы он не нашел пользователя
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.empty());

        // Проверяем исключение UsernameNotFoundException
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            commentService.saveComment(post.getId(), commentDTO, principal);
        });

        assertEquals("Username not found with username testuser", exception.getMessage());
    }

    @Test
    public void testGetAllCommentsForPost() {
        // Мокаем репозиторий Post
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        // Мокаем репозиторий Comment
        when(commentRepository.findAllByPost(post)).thenReturn(Collections.singletonList(new Comment()));

        List<Comment> comments = commentService.getAllCommentsForPost(post.getId());

        assertNotNull(comments);
        assertFalse(comments.isEmpty());
        verify(commentRepository, times(1)).findAllByPost(post);
    }

    @Test
    public void testGetAllCommentsForPostPostNotFound() {
        // Мокаем репозиторий Post, чтобы он не нашел пост
        when(postRepository.findById(post.getId())).thenReturn(Optional.empty());

        // Проверяем исключение PostNotFoundException
        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> {
            commentService.getAllCommentsForPost(post.getId());
        });

        assertEquals("Post cannot be found", exception.getMessage());
    }

    @Test
    public void testDeleteComment() {
        // Создаем комментарий
        Comment comment = new Comment();
        comment.setId(1L);

        // Мокаем репозиторий Comment
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        // Вызываем метод deleteComment
        commentService.deleteComment(1L);

        // Проверяем, что метод delete был вызван один раз
        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    public void testDeleteCommentNotFound() {
        // Мокаем репозиторий Comment, чтобы комментарий не был найден
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        // Вызываем метод deleteComment и проверяем, что ничего не происходит
        commentService.deleteComment(1L);

        // Проверяем, что метод delete не был вызван
        verify(commentRepository, times(0)).delete(Mockito.any(Comment.class));
    }
}
