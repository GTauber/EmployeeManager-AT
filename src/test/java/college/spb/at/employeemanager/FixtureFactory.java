package college.spb.at.employeemanager;

import college.spb.at.employeemanager.enums.Role;
import college.spb.at.employeemanager.model.dto.DepartmentDto;
import college.spb.at.employeemanager.model.dto.EmployeeDto;
import college.spb.at.employeemanager.model.dto.UserDto;
import college.spb.at.employeemanager.model.entity.Department;
import college.spb.at.employeemanager.model.entity.Employee;
import college.spb.at.employeemanager.model.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class FixtureFactory {

    public static UserDto validUserDto() {
        return new UserDto("1", "John Doe", "test@test.com", "password", Set.of(Role.ROLE_USER));
    }

    public static User validUser() {
        return User.builder()
            .id("1")
            .version(1L)
            .name("John Doe")
            .email("test@test.com")
            .password("password")
            .roles(Set.of(Role.ROLE_USER))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public static EmployeeDto validEmployeeDto() {
        return new EmployeeDto(1L, "John Doe", "01 de oliveira", "123456789",
            "test@test.com", LocalDate.now(), 1L);
    }

    public static Employee validEmployee() {
        return Employee.builder()
            .id(1L)
            .version(1L)
            .name("John Doe")
            .address("01 de oliveira")
            .phone("123456789")
            .email("test@test.com")
            .birthDate(LocalDate.now())
            .department(validDepartment())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public static Department validDepartment() {
        return Department.builder()
            .id(1L)
            .name("Sales")
            .local("Building A")
            .build();

    }

    public static DepartmentDto validDepartmentDto() {
        return new DepartmentDto(1L, "Sales", "Building A");
    }
}