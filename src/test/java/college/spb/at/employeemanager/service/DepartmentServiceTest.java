package college.spb.at.employeemanager.service;

import static college.spb.at.employeemanager.FixtureFactory.validDepartment;
import static college.spb.at.employeemanager.FixtureFactory.validDepartmentDto;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import college.spb.at.employeemanager.mapper.DepartmentMapper;
import college.spb.at.employeemanager.repository.DepartmentRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    @DisplayName("Should create department successfully")
    void shouldCreateDepartment() {
        var departmentDto = validDepartmentDto();
        var department = validDepartment();
        when(departmentMapper.toEntity(departmentDto)).thenReturn(department);
        when(departmentRepository.save(department)).thenReturn(department);

        departmentService.createDepartment(departmentDto);

        verify(departmentMapper).toEntity(departmentDto);
        verify(departmentRepository).save(department);
    }

    @Test
    @DisplayName("Should fetch all departments successfully")
    void shouldGetAllDepartments() {
        var department = validDepartment();
        when(departmentRepository.findAll()).thenReturn(Collections.singletonList(department));

        departmentService.getAllDepartments();

        verify(departmentRepository).findAll();
    }

    @Test
    @DisplayName("Should fetch department by id when department exists")
    void shouldGetDepartmentById() {
        var id = 1L;
        var department = validDepartment();
        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));
        when(departmentMapper.toDto(department)).thenReturn(validDepartmentDto());

        departmentService.getDepartmentById(id);

        verify(departmentRepository).findById(id);
    }

    @Test
    @DisplayName("Should throw exception when department by id does not exist")
    void shouldThrowExceptionWhenDepartmentByIdDoesNotExist() {
        var id = 1L;
        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> departmentService.getDepartmentById(id));
    }

    @Test
    @DisplayName("Should update department successfully when department exists")
    void shouldUpdateDepartmentWhenDepartmentExists() {
        var id = 1L;
        var departmentDto = validDepartmentDto();
        var existingDepartment = validDepartment();
        var updatedDepartment = validDepartment();
        when(departmentRepository.findById(id)).thenReturn(Optional.of(existingDepartment));
        when(departmentRepository.save(existingDepartment)).thenReturn(updatedDepartment);
        when(departmentMapper.toDto(updatedDepartment)).thenReturn(departmentDto);

        departmentService.updateDepartment(id, departmentDto);

        verify(departmentRepository).findById(id);
        verify(departmentMapper).partialUpdate(departmentDto, existingDepartment);
        verify(departmentRepository).save(existingDepartment);
    }

    @Test
    @DisplayName("Should throw exception when updating department that does not exist")
    void shouldThrowExceptionWhenUpdatingDepartmentThatDoesNotExist() {
        var id = 1L;
        var departmentDto = validDepartmentDto();
        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> departmentService.updateDepartment(id, departmentDto));
    }

    @Test
    @DisplayName("Should delete department successfully")
    void shouldDeleteDepartment() {
        var id = 1L;
        when(departmentRepository.existsById(id)).thenReturn(true);

        departmentService.deleteDepartment(id);

        verify(departmentRepository).existsById(id);
        verify(departmentRepository).deleteById(id);
    }

    @Test
    @DisplayName("Should throw exception when deleting department that does not exist")
    void shouldThrowExceptionWhenDeletingDepartmentThatDoesNotExist() {
        var id = 1L;
        when(departmentRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> departmentService.deleteDepartment(id));
    }

}