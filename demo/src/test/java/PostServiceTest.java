import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.exceptions.PostNotFoundException;
import com.example.demo.mapper.PostMapper;
import com.example.demo.payload.request.PostCreateRequest;
import com.example.demo.payload.response.PostDTO;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;

    private User user;
    private Post post;
    private Principal principal;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        post = new Post();
        post.setId(1L);
        post.setUser(user);
        post.setTitle("Test Title");
        post.setCaption("Test Caption");
        post.setLocation("Test Location");
        post.setLikes(0);

        principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testuser");
    }

    @Test
    void createPost_Success() {
        PostCreateRequest request = new PostCreateRequest();
        request.setTitle("New Post");
        request.setCaption("Caption");
        request.setLocation("Location");

        when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postMapper.postToPostDTO(any(Post.class))).thenReturn(new PostDTO());

        PostDTO createdPost = postService.createPost(request, principal);

        assertNotNull(createdPost);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void getPostById_Success() {
        when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        when(postRepository.findPostByIdAndUser(1L, user)).thenReturn(Optional.of(post));

        Post foundPost = postService.getPostById(1L, principal);

        assertNotNull(foundPost);
        assertEquals("Test Title", foundPost.getTitle());
    }

    @Test
    void getPostById_NotFound() {
        when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        when(postRepository.findPostByIdAndUser(1L, user)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.getPostById(1L, principal));
    }

    @Test
    void deletePost_Success() {
        when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        when(postRepository.findPostByIdAndUser(1L, user)).thenReturn(Optional.of(post));
        when(imageRepository.findByPostId(1L)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> postService.deletePost(1L, principal));
        verify(postRepository, times(1)).delete(any(Post.class));
    }
}
