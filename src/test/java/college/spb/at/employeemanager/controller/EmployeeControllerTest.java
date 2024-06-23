package college.spb.at.employeemanager.controller;

import static college.spb.at.employeemanager.FixtureFactory.validEmployeeDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import college.spb.at.employeemanager.model.dto.EmployeeDto;
import college.spb.at.employeemanager.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Should create employee successfully")
    void shouldCreateEmployee() throws Exception {
        EmployeeDto employeeDto = validEmployeeDto();
        when(employeeService.createEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);

        mockMvc.perform(post("/v1/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)))
            .andExpect(status().isCreated());

        verify(employeeService).createEmployee(any(EmployeeDto.class));
    }

    @Test
    @DisplayName("Should fetch all employees successfully")
    void shouldGetAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(validEmployeeDto()));

        mockMvc.perform(get("/v1/employee")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(employeeService).getAllEmployees();
    }

    @Test
    @DisplayName("Should fetch employee by id when employee exists")
    void shouldGetEmployeeById() throws Exception {
        var employeeDto = validEmployeeDto();
        when(employeeService.getEmployeeById(anyLong())).thenReturn(employeeDto);

        mockMvc.perform(get("/v1/employee/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(employeeService).getEmployeeById(anyLong());
    }

    @Test
    @DisplayName("Should update employee successfully when employee exists")
    void shouldUpdateEmployeeWhenEmployeeExists() throws Exception {
        var employeeDto = validEmployeeDto();
        when(employeeService.updateEmployee(anyLong(), any(EmployeeDto.class))).thenReturn(employeeDto);

        mockMvc.perform(put("/v1/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)))
            .andExpect(status().isOk());

        verify(employeeService).updateEmployee(anyLong(), any(EmployeeDto.class));
    }

    @Test
    @DisplayName("Should delete employee successfully")
    void shouldDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(anyLong());

        mockMvc.perform(delete("/v1/employee/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(employeeService).deleteEmployee(anyLong());
    }

}