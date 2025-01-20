package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.LoginDto;
import com.cosmetic_manager.cosmetic_manager.dto.UserDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.InvalidCredentialsException;
import com.cosmetic_manager.cosmetic_manager.exceptions.UserAlreadyExistsException;
import com.cosmetic_manager.cosmetic_manager.exceptions.UserNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.repository.UserRepository;
import com.cosmetic_manager.cosmetic_manager.utils.SkinType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static com.cosmetic_manager.cosmetic_manager.utils.SkinType.OILY;
import static com.cosmetic_manager.cosmetic_manager.utils.UserUtil.fromUserDtoToUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Create new user with valid data")
    void createUser() throws ParseException {
        // Arrange
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1999-12-12");

        UserDto userDto = new UserDto("stefana", "stefana@gmail.com", "Stefana123!", SkinType.COMBINATION, birthDate);
        User userFromDto = fromUserDtoToUser(userDto);

        User savedUser = new User(1, "stefana", "stefana@gmail.com", "Stefana123!", SkinType.COMBINATION, birthDate, new Date());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = userService.createNewUser(userDto);

        // Assert
        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
        assertEquals(savedUser.getUsername(), result.getUsername());
        assertEquals(savedUser.getEmail(), result.getEmail());
        assertEquals(savedUser.getPassword(), result.getPassword());
        assertEquals(savedUser.getSkinType(), result.getSkinType());
        assertEquals(savedUser.getBirthDate(), result.getBirthDate());
        assertNotNull(result.getRegistrationDate());
    }

    @Test
    @DisplayName("Create new user with invalid data")
    void createUserInvalidData() throws ParseException {
        // Arrange
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1999-12-12");

        User user = new User(1, "stefana", "stefana@gmail.com", "Stefana123!", SkinType.COMBINATION, birthDate, new Date());
        UserDto userDto = new UserDto("stefana", "stefana@gmail.com", "Stefana123!", SkinType.OILY, birthDate);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Act
        Exception exception = assertThrows(UserAlreadyExistsException.class, () -> userService.createNewUser(userDto));

        // Assert
        assertEquals("User with email " + user.getEmail() + " already exists", exception.getMessage());

        verify(userRepository, times(0)).save(user);
    }

    @Test
    @DisplayName("Get user by id")
    void getUserById() throws Exception {
        // Arrange
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1999-12-12");

        User user = new User(1, "stefana", "stefana@gmail.com", "Stefana123!", SkinType.COMBINATION, birthDate, new Date());

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(1);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("Get user by invalid id")
    void getUserByIdInvalidId() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(UserNotFoundException.class, () -> userService.getUserById(1));

        // Assert
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    @DisplayName("Login with valid credentials")
    void login() throws Exception {
        // Arrange
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1999-12-12");

        User user = new User(1, "stefana", "stefa@gmail.com", "Stefana123!", SkinType.COMBINATION, birthDate, new Date());

        LoginDto loginDto = new LoginDto("stefana@gmail.com", "Stefana123!");

        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(user));

        // Act
        User result = userService.login(loginDto);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository).findByEmail(loginDto.getEmail());
    }

    @Test
    @DisplayName("Login with invalid credentials")
    void loginInvalidCredentials() {
        // Arrange
        LoginDto loginDto = new LoginDto("daria@gmail.com", "Daria123!");

        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(InvalidCredentialsException.class, () -> userService.login(loginDto));

        // Assert
        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    @DisplayName("Get user by email")
    void getUserByEmail() throws Exception {
        // Arrange
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1999-12-12");

        User user = new User(1, "stefana", "stefana@gmail.com", "Stefana123!", SkinType.COMBINATION, birthDate, new Date());

        when(userRepository.findByEmail("stefana@gmail.com")).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserByEmail("stefana@gmail.com");

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getSkinType(), result.getSkinType());
        assertEquals(user.getBirthDate(), result.getBirthDate());

        verify(userRepository).findByEmail("stefana@gmail.com");
    }
}
