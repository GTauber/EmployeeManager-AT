package college.spb.at.employeemanager.service;

import college.spb.at.employeemanager.mapper.EmployeeMapper;
import college.spb.at.employeemanager.model.dto.EmployeeDto;
import college.spb.at.employeemanager.model.entity.Employee;
import college.spb.at.employeemanager.repository.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        log.info("Creating a new employee");
        var employee = employeeMapper.toEntity(employeeDto);
        var savedEmployee = employeeRepository.save(employee);
        log.info("Employee created with ID: {}", savedEmployee.getId());
        return employeeMapper.toDto(savedEmployee);
    }

    public List<EmployeeDto> getAllEmployees() {
        log.info("Fetching all employees");
        return employeeRepository.findAll().stream()
            .map(employeeMapper::toDto)
            .toList();
    }

    public EmployeeDto getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        var optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            log.info("Employee found with ID: {}", id);
            return employeeMapper.toDto(employee);
        } else {
            log.error("Employee not found with ID: {}", id);
            throw new RuntimeException("Employee not found");
        }
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        log.info("Updating employee with ID: {}", id);
        return employeeRepository.findById(id)
            .map(existingEmployee -> {
                employeeMapper.partialUpdate(employeeDto, existingEmployee);
                var updatedEmployee = employeeRepository.save(existingEmployee);
                log.info("Employee updated with ID: {}", updatedEmployee.getId());
                return employeeMapper.toDto(updatedEmployee);
            })
            .orElseThrow(() -> {
                employerNotFoundLogger(id);
                return new RuntimeException("Employee not found");
            });
    }

    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            log.info("Employee deleted with ID: {}", id);
        } else {
            employerNotFoundLogger(id);
            throw new RuntimeException("Employee not found");
        }
    }

    private void employerNotFoundLogger(Long id) {
        log.error("Employee not found with ID: {}", id);
    }

}
