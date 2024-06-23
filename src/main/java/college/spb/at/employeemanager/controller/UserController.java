package college.spb.at.employeemanager.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import college.spb.at.employeemanager.model.dto.UserDto;
import college.spb.at.employeemanager.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        log.info("Received request to create a new user");
        UserDto createdUser = userService.createUser(userDto);
        log.info("New user created with ID: {}", createdUser.id());
        return new ResponseEntity<>(createdUser, CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("Received request to fetch all users");
        List<UserDto> users = userService.getAllUsers();
        log.info("Fetched {} users", users.size());
        return new ResponseEntity<>(users, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        log.info("Received request to fetch user with ID: {}", id);
        UserDto user = userService.getUserById(id);
        log.info("User fetched with ID: {}", user.id());
        return new ResponseEntity<>(user, OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserDto userDto) {
        log.info("Received request to update user with ID: {}", id);
        UserDto updatedUser = userService.updateUser(id, userDto);
        log.info("User updated with ID: {}", updatedUser.id());
        return new ResponseEntity<>(updatedUser, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        log.info("Received request to delete user with ID: {}", id);
        userService.deleteUser(id);
        log.info("User deleted with ID: {}", id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
