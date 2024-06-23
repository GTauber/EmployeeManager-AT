package college.spb.at.employeemanager.model.dto;

import college.spb.at.employeemanager.enums.Role;
import jakarta.validation.constraints.Email;
import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link college.spb.at.employeemanager.model.entity.User}
 */
public record UserDto(String id, String name, @Email String email, String password, Set<Role> roles) implements
    Serializable {

}