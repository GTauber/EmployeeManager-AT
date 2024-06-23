package college.spb.at.employeemanager.model.dto;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link college.spb.at.employeemanager.model.entity.Department}
 */
public record DepartmentDto(Long id, @NotBlank String name, @NotBlank String local) implements Serializable {

}