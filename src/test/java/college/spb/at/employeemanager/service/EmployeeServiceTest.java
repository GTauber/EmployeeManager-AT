package college.spb.at.employeemanager.service;

import static college.spb.at.employeemanager.FixtureFactory.validEmployee;
import static college.spb.at.employeemanager.FixtureFactory.validEmployeeDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import college.spb.at.employeemanager.mapper.EmployeeMapper;
import college.spb.at.employeemanager.model.dto.EmployeeDto;
import college.spb.at.employeemanager.model.entity.Employee;
import college.spb.at.employeemanager.repository.EmployeeRepository;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    @DisplayName("Should create employee successfully")
    void shouldCreateEmployee() {
        var employeeDto = validEmployeeDto();
        var employee = validEmployee();
        when(employeeMapper.toEntity(employeeDto)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);

        employeeService.createEmployee(employeeDto);

        verify(employeeMapper).toEntity(employeeDto);
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("Should fetch all employees successfully")
    void shouldGetAllEmployees() {
        var employee = validEmployee();
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

        employeeService.getAllEmployees();

        verify(employeeRepository).findAll();
    }

    @Test
    @DisplayName("Should fetch employee by id when employee exists")
    void shouldGetEmployeeById() {
        var id = 1L;
        var employee = validEmployee();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        employeeService.getEmployeeById(id);

        verify(employeeRepository).findById(id);
    }

    @Test
    @DisplayName("Should throw exception when employee by id does not exist")
    void shouldThrowExceptionWhenEmployeeByIdDoesNotExist() {
        var id = 1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> employeeService.getEmployeeById(id));
    }


    @Test
    @DisplayName("Should throw exception when updating employee that does not exist")
    void shouldThrowExceptionWhenUpdatingEmployeeThatDoesNotExist() {
        var id = 1L;
        var employeeDto = validEmployeeDto();
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> employeeService.updateEmployee(id, employeeDto));
    }

    @Test
    void shouldUpdateEmployeeWhenEmployeeExists() {
        var validEmployee = validEmployee();
        var validEmployeeDto = validEmployeeDto();
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(validEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(validEmployee);
        when(employeeMapper.partialUpdate(any(EmployeeDto.class), any(Employee.class))).thenAnswer(invocation -> {
            EmployeeDto dto = invocation.getArgument(0);
            Employee emp = invocation.getArgument(1);
            emp.setName(dto.name());
            return emp;
        });
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(validEmployeeDto);

        employeeService.updateEmployee(1L, validEmployeeDto);

        verify(employeeRepository).findById(1L);
        verify(employeeMapper).partialUpdate(validEmployeeDto, validEmployee);
        verify(employeeRepository).save(validEmployee);
        verify(employeeMapper).toDto(validEmployee);
    }

    @Test
    @DisplayName("Should delete employee successfully")
    void shouldDeleteEmployee() {
        var id = 1L;
        when(employeeRepository.existsById(id)).thenReturn(true);

        employeeService.deleteEmployee(id);

        verify(employeeRepository).existsById(id);
        verify(employeeRepository).deleteById(id);
    }

    @Test
    @DisplayName("Should throw exception when deleting employee that does not exist")
    void shouldThrowExceptionWhenDeletingEmployeeThatDoesNotExist() {
        var id = 1L;
        when(employeeRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> employeeService.deleteEmployee(id));
    }

}