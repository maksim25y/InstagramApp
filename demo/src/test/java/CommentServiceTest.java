import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.entity.Post;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.payload.request.CommentCreateRequest;
import com.example.demo.payload.response.CommentDTO;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.CommentService;
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
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentService commentService;

    private User user;
    private Comment comment;
    private Post post;
    private Principal principal;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        post = new Post();
        post.setId(1L);

        comment = new Comment();
        comment.setId(1L);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage("Test Comment");
        comment.setPost(post);

        principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testuser");
    }

    @Test
    void saveComment_Success() {
        CommentCreateRequest request = new CommentCreateRequest();
        request.setMessage("New Comment");

        when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.commentToCommentDTO(any(Comment.class))).thenReturn(new CommentDTO());

        CommentDTO result = commentService.saveComment(1L, request, principal);

        assertNotNull(result);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }
}
