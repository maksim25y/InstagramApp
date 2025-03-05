import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.ERole;
import com.example.demo.exceptions.UserExistsException;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private Principal principal;

    @InjectMocks
    private UserService userService;

    private User user;
    private SignupRequest signupRequest;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создаем пользователя
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setName("Test");
        user.setLastname("User");

        // Создаем SignupRequest
        signupRequest = new SignupRequest();
        signupRequest.setEmail("testuser@example.com");
        signupRequest.setFirstname("Test");
        signupRequest.setLastname("User");
        signupRequest.setUsername("testuser");
        signupRequest.setPassword("password123");

        // Создаем UserDTO
        userDTO = new UserDTO();
        userDTO.setFirstname("UpdatedName");
        userDTO.setLastname("UpdatedLastname");
        userDTO.setBio("Updated bio");

        // Мокаем Principal
        when(principal.getName()).thenReturn(user.getUsername());
    }

    @Test
    public void testCreateUserUserExists() {
        // Мокаем репозиторий, чтобы бросить исключение
        when(userRepository.save(Mockito.any(User.class))).thenThrow(new RuntimeException("Duplicate entry"));

        // Проверяем исключение
        UserExistsException exception = assertThrows(UserExistsException.class, () -> {
            userService.createUser(signupRequest);
        });

        assertEquals("The user testuser already exist. Please check credentials", exception.getMessage());
    }

    @Test
    public void testUpdateUserSuccess() {
        // Мокаем репозиторий, чтобы вернуть текущего пользователя
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(userDTO, principal);

        assertNotNull(updatedUser);
        assertEquals("UpdatedName", updatedUser.getName());
        assertEquals("UpdatedLastname", updatedUser.getLastname());
        assertEquals("Updated bio", updatedUser.getBio());
        verify(userRepository, times(1)).save(Mockito.any(User.class));
    }

    @Test
    public void testGetCurrentUser() {
        // Мокаем репозиторий, чтобы вернуть текущего пользователя
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User currentUser = userService.getCurrentUser(principal);

        assertNotNull(currentUser);
        assertEquals("testuser", currentUser.getUsername());
        verify(userRepository, times(1)).findUserByUsername(user.getUsername());
    }

    @Test
    public void testGetCurrentUserNotFound() {
        // Мокаем репозиторий, чтобы не найти пользователя
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.empty());

        // Проверяем исключение
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.getCurrentUser(principal);
        });

        assertEquals("User not found with username testuser", exception.getMessage());
    }

    @Test
    public void testGetUserByIdSuccess() {
        // Мокаем репозиторий, чтобы вернуть пользователя по id
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(user.getId());

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        verify(userRepository, times(1)).findUserById(user.getId());
    }

    @Test
    public void testGetUserByIdNotFound() {
        // Мокаем репозиторий, чтобы не найти пользователя по id
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.empty());

        // Проверяем исключение
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.getUserById(user.getId());
        });

        assertEquals("User not found", exception.getMessage());
    }
}
