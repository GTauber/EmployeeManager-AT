package college.spb.at.employeemanager.service;

import static college.spb.at.employeemanager.FixtureFactory.validUser;
import static college.spb.at.employeemanager.FixtureFactory.validUserDto;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import college.spb.at.employeemanager.mapper.UserMapper;
import college.spb.at.employeemanager.repository.UserRepository;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

//    @Test
//    @DisplayName("Should load user by username when user exists")
//    void shouldLoadUserByUsername() {
//        var email = "test@test.com";
//        var user = validUser();
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
//
//        userService.loadUserByUsername(email);
//
//        verify(userRepository).findByEmail(email);
//    }

    @Test
    @DisplayName("Should throw exception when user does not exist")
    void shouldThrowExceptionWhenUserDoesNotExist() {
        var email = "test@test.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
    }

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUser() {
        var userDto = validUserDto();
        var user = validUser();
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userService.createUser(userDto);

        verify(userMapper).toEntity(userDto);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Should fetch all users successfully")
    void shouldGetAllUsers() {
        var user = validUser();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        userService.getAllUsers();

        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Should fetch user by id when user exists")
    void shouldGetUserById() {
        var id = "testId";
        var user = validUser();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.getUserById(id);

        verify(userRepository).findById(id);
    }

    @Test
    @DisplayName("Should throw exception when user by id does not exist")
    void shouldThrowExceptionWhenUserByIdDoesNotExist() {
        var id = "testId";
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(id));
    }

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUser() {
        var id = "testId";
        var userDto = validUserDto();
        var user = validUser();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.updateUser(id, userDto);

        verify(userRepository).findById(id);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Should throw exception when updating user that does not exist")
    void shouldThrowExceptionWhenUpdatingUserThatDoesNotExist() {
        var id = "testId";
        var userDto = validUserDto();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(id, userDto));
    }

    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUser() {
        String id = "testId";
        when(userRepository.existsById(id)).thenReturn(true);

        userService.deleteUser(id);

        verify(userRepository).existsById(id);
        verify(userRepository).deleteById(id);
    }

    @Test
    @DisplayName("Should throw exception when deleting user that does not exist")
    void shouldThrowExceptionWhenDeletingUserThatDoesNotExist() {
        String id = "testId";
        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(id));
    }

}