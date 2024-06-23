package college.spb.at.employeemanager.mapper;

import college.spb.at.employeemanager.model.dto.DepartmentDto;
import college.spb.at.employeemanager.model.entity.Department;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-22T22:12:27-0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class DepartmentMapperImpl implements DepartmentMapper {

    @Override
    public Department toEntity(DepartmentDto departmentDto) {
        if ( departmentDto == null ) {
            return null;
        }

        Department.DepartmentBuilder department = Department.builder();

        department.id( departmentDto.id() );
        department.name( departmentDto.name() );
        department.local( departmentDto.local() );

        return department.build();
    }

    @Override
    public DepartmentDto toDto(Department department) {
        if ( department == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String local = null;

        id = department.getId();
        name = department.getName();
        local = department.getLocal();

        DepartmentDto departmentDto = new DepartmentDto( id, name, local );

        return departmentDto;
    }

    @Override
    public Department partialUpdate(DepartmentDto departmentDto, Department department) {
        if ( departmentDto == null ) {
            return department;
        }

        if ( departmentDto.id() != null ) {
            department.setId( departmentDto.id() );
        }
        if ( departmentDto.name() != null ) {
            department.setName( departmentDto.name() );
        }
        if ( departmentDto.local() != null ) {
            department.setLocal( departmentDto.local() );
        }

        return department;
    }
}
