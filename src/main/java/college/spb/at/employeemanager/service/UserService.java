package college.spb.at.employeemanager.service;

import college.spb.at.employeemanager.mapper.UserMapper;
import college.spb.at.employeemanager.model.dto.UserDto;
import college.spb.at.employeemanager.model.entity.User;
import college.spb.at.employeemanager.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Loading user by email: {}", email);
        var user = userRepository.findByEmail(email);
        log.info("User found by email: {}", user);
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDto createUser(UserDto userDto) {
        log.info("Creating a new user");
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("User created with ID: {}", savedUser.getId());
        return userMapper.toDto(savedUser);
    }

    public List<UserDto> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
            .map(userMapper::toDto)
            .toList();
    }

    public UserDto getUserById(String id) {
        log.info("Fetching user with ID: {}", id);
        var user = userRepository.findById(id);
        if (user.isPresent()) {
            log.info("User found with ID: {}", id);
            return userMapper.toDto(user.get());
        } else {
            logUserNotFound(id);
            throw new RuntimeException("User not found");
        }
    }

    public UserDto updateUser(String id, UserDto userDto) {
        log.info("Updating user with ID: {}", id);
        var existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            userMapper.partialUpdate(userDto, existingUser.get());
            User updatedUser = userRepository.save(existingUser.get());
            log.info("User updated with ID: {}", updatedUser.getId());
            return userMapper.toDto(updatedUser);
        } else {
            logUserNotFound(id);
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUser(String id) {
        log.info("Deleting user with ID: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("User deleted with ID: {}", id);
        } else {
            logUserNotFound(id);
            throw new RuntimeException("User not found");
        }
    }

    private static void logUserNotFound(String id) {
        log.error("User not found with ID: {}", id);
        throw new RuntimeException("User not found");
    }

}
