package college.spb.at.employeemanager.controller;

import college.spb.at.employeemanager.model.dto.DepartmentDto;
import college.spb.at.employeemanager.service.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/v1/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {
        log.info("Received request to create a new department");
        DepartmentDto createdDepartment = departmentService.createDepartment(departmentDto);
        log.info("New department created with ID: {}", createdDepartment.id());
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        log.info("Received request to fetch all departments");
        var departments = departmentService.getAllDepartments();
        log.info("Fetched {} departments", departments.size());
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        log.info("Received request to fetch department with ID: {}", id);
        DepartmentDto department = departmentService.getDepartmentById(id);
        log.info("Department fetched with ID: {}", department.id());
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDto departmentDto) {
        log.info("Received request to update department with ID: {}", id);
        DepartmentDto updatedDepartment = departmentService.updateDepartment(id, departmentDto);
        log.info("Department updated with ID: {}", updatedDepartment.id());
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.info("Received request to delete department with ID: {}", id);
        departmentService.deleteDepartment(id);
        log.info("Department deleted with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
