package college.spb.at.employeemanager.controller;

import static college.spb.at.employeemanager.FixtureFactory.validDepartmentDto;
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

import college.spb.at.employeemanager.model.dto.DepartmentDto;
import college.spb.at.employeemanager.service.DepartmentService;
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
class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should create department successfully")
    void shouldCreateDepartment() throws Exception {
        var departmentDto = validDepartmentDto();
        when(departmentService.createDepartment(any(DepartmentDto.class))).thenReturn(departmentDto);

        mockMvc.perform(post("/v1/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentDto)))
            .andExpect(status().isCreated());

        verify(departmentService).createDepartment(any(DepartmentDto.class));
    }

    @Test
    @DisplayName("Should fetch all departments successfully")
    void shouldGetAllDepartments() throws Exception {
        when(departmentService.getAllDepartments()).thenReturn(Collections.singletonList(validDepartmentDto()));

        mockMvc.perform(get("/v1/department")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(departmentService).getAllDepartments();
    }

    @Test
    @DisplayName("Should fetch department by id when department exists")
    void shouldGetDepartmentById() throws Exception {
        var departmentDto = validDepartmentDto();
        when(departmentService.getDepartmentById(anyLong())).thenReturn(departmentDto);

        mockMvc.perform(get("/v1/department/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(departmentService).getDepartmentById(anyLong());
    }

    @Test
    @DisplayName("Should update department successfully when department exists")
    void shouldUpdateDepartmentWhenDepartmentExists() throws Exception {
        var departmentDto = validDepartmentDto();
        when(departmentService.updateDepartment(anyLong(), any(DepartmentDto.class))).thenReturn(departmentDto);

        mockMvc.perform(put("/v1/department/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentDto)))
            .andExpect(status().isOk());

        verify(departmentService).updateDepartment(anyLong(), any(DepartmentDto.class));
    }

    @Test
    @DisplayName("Should delete department successfully")
    void shouldDeleteDepartment() throws Exception {
        doNothing().when(departmentService).deleteDepartment(anyLong());

        mockMvc.perform(delete("/v1/department/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(departmentService).deleteDepartment(anyLong());
    }

}