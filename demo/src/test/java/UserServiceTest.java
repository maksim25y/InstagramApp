import com.example.demo.entity.User;
import com.example.demo.entity.enums.ERole;
import com.example.demo.mapper.UserMapper;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.UserDTO;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.security.Principal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private SignupRequest signupRequest;
    private Principal principal;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.getRoles().add(ERole.ROLE_USER);

        signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser");
        signupRequest.setEmail("test@example.com");
        signupRequest.setPassword("password");
        signupRequest.setFirstname("Test");
        signupRequest.setLastname("User");

        principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testuser");
    }

    @Test
    void updateUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstname("Updated");
        userDTO.setLastname("User");
        userDTO.setBio("New bio");

        when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.userToUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO updatedUser = userService.updateUser(userDTO, principal);

        assertNotNull(updatedUser);
        assertEquals("Updated", updatedUser.getFirstname());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getCurrentUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstname("Test");

        when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        when(userMapper.userToUserDTO(user)).thenReturn(userDTO);

        UserDTO currentUser = userService.getCurrentUser(principal);

        assertNotNull(currentUser);
        assertEquals("Test", currentUser.getFirstname());
        verify(userRepository, times(1)).findUserByUsername("testuser");
    }
}
