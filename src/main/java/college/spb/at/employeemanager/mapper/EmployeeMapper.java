package college.spb.at.employeemanager.mapper;

import college.spb.at.employeemanager.model.dto.EmployeeDto;
import college.spb.at.employeemanager.model.entity.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface EmployeeMapper {

    @Mapping(source = "departmentId", target = "department.id")
    Employee toEntity(EmployeeDto employeeDto);

    @Mapping(source = "department.id", target = "departmentId")
    EmployeeDto toDto(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "departmentId", target = "department.id")
    Employee partialUpdate(EmployeeDto employeeDto, @MappingTarget Employee employee);
}