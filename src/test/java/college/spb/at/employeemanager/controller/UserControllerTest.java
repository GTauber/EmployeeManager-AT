package college.spb.at.employeemanager.controller;

import static college.spb.at.employeemanager.FixtureFactory.validUserDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import college.spb.at.employeemanager.model.dto.UserDto;
import college.spb.at.employeemanager.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUser() throws Exception {
        var userDto = validUserDto();
        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
            .andExpect(status().isCreated());

        verify(userService).createUser(any(UserDto.class));
    }

    @Test
    @DisplayName("Should fetch all users successfully")
    void shouldGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(validUserDto()));

        mockMvc.perform(get("/v1/user")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(userService).getAllUsers();
    }

    @Test
    @DisplayName("Should fetch user by id when user exists")
    void shouldGetUserById() throws Exception {
        var userDto = validUserDto();
        when(userService.getUserById(anyString())).thenReturn(userDto);

        mockMvc.perform(get("/v1/user/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(userService).getUserById(anyString());
    }

    @Test
    @DisplayName("Should update user successfully when user exists")
    void shouldUpdateUserWhenUserExists() throws Exception {
        var userDto = validUserDto();
        when(userService.updateUser(anyString(), any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(put("/v1/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
            .andExpect(status().isOk());

        verify(userService).updateUser(anyString(), any(UserDto.class));
    }

    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyString());

        mockMvc.perform(delete("/v1/user/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(userService).deleteUser(anyString());
    }

}