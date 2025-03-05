import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.dto.PostDTO;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.exceptions.PostNotFoundException;
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

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private Principal principal;

    @InjectMocks
    private PostService postService;

    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");

        post = new Post();
        post.setId(1L);
        post.setUser(user);
        post.setTitle("Test Post");
        post.setCaption("Test Caption");
        post.setLocation("Test Location");
        post.setLikes(0);
    }

    @Test
    void testCreatePost() {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Test Post");
        postDTO.setCaption("Test Caption");
        postDTO.setLocation("Test Location");
        when(principal.getName()).thenReturn("testuser");
        when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post createdPost = postService.createPost(postDTO, principal);

        assertNotNull(createdPost);
        assertEquals("Test Post", createdPost.getTitle());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testGetAllPosts() {
        when(postRepository.findAllByOrderByCreatedDateDesc()).thenReturn(List.of(post));
        List<Post> posts = postService.getAllPosts();
        assertFalse(posts.isEmpty());
    }

    @Test
    void testGetPostById_PostExists() {
        when(principal.getName()).thenReturn("testuser");
        when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        when(postRepository.findPostByIdAndUser(1L, user)).thenReturn(Optional.of(post));

        Post foundPost = postService.getPostById(1L, principal);
        assertEquals(1L, foundPost.getId());
    }

    @Test
    void testGetPostById_PostNotFound() {
        when(principal.getName()).thenReturn("testuser");
        when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        when(postRepository.findPostByIdAndUser(1L, user)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.getPostById(1L, principal));
    }

    @Test
    void testLikePost() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post likedPost = postService.likePost(1L, "testuser");
        assertEquals(1, likedPost.getLikes());
    }

    @Test
    void testDeletePost() {
        when(principal.getName()).thenReturn("testuser");
        when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        when(postRepository.findPostByIdAndUser(1L, user)).thenReturn(Optional.of(post));
        when(imageRepository.findByPostId(1L)).thenReturn(Optional.empty());

        postService.deletePost(1L, principal);

        verify(postRepository, times(1)).delete(post);
    }
}
