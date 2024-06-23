package college.spb.at.employeemanager.service;

import college.spb.at.employeemanager.mapper.DepartmentMapper;
import college.spb.at.employeemanager.model.dto.DepartmentDto;
import college.spb.at.employeemanager.model.entity.Department;
import college.spb.at.employeemanager.repository.DepartmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        log.info("Creating a new department");
        var department = departmentMapper.toEntity(departmentDto);
        var savedDepartment = departmentRepository.save(department);
        log.info("Department created with ID: {}", savedDepartment.getId());
        return departmentMapper.toDto(savedDepartment);
    }

    public List<DepartmentDto> getAllDepartments() {
        log.info("Fetching all departments");
        return departmentRepository.findAll().stream()
            .map(departmentMapper::toDto)
            .toList();
    }

    public DepartmentDto getDepartmentById(Long id) {
        log.info("Fetching department with ID: {}", id);
        return departmentRepository.findById(id)
            .map(department -> {
                log.info("Department found with ID: {}", id);
                return departmentMapper.toDto(department);
            })
            .orElseThrow(() -> {
                logDepartmentNotFound(id);
                return new RuntimeException("Department not found");
            });
    }

    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        log.info("Updating department with ID: {}", id);
        return departmentRepository.findById(id)
            .map(existingDepartment -> {
                departmentMapper.partialUpdate(departmentDto, existingDepartment);
                var updatedDepartment = departmentRepository.save(existingDepartment);
                log.info("Department updated with ID: {}", updatedDepartment.getId());
                return departmentMapper.toDto(updatedDepartment);
            })
            .orElseThrow(() -> {
                logDepartmentNotFound(id);
                return new RuntimeException("Department not found");
            });
    }

    public void deleteDepartment(Long id) {
        log.info("Deleting department with ID: {}", id);
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            log.info("Department deleted with ID: {}", id);
        } else {
            logDepartmentNotFound(id);
            throw new RuntimeException("Department not found");
        }
    }

    private void logDepartmentNotFound(Long id) {
        log.error("Department not found with ID: {}", id);
    }

}
