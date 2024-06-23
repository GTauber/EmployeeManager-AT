package college.spb.at.employeemanager.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link college.spb.at.employeemanager.model.entity.Employee}
 */
public record EmployeeDto(Long id, @NotNull(message = "Name must not be Null") @Size(message = "Name must be between 2 and 20 characters", min = 2, max = 20) @NotEmpty(message = "name must not be Empty") String name, String address, String phone, @Email(message = "must be an email format") String email, LocalDate birthDate, Long departmentId) implements
    Serializable {
  }