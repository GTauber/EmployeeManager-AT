package college.spb.at.employeemanager.mapper;

import college.spb.at.employeemanager.model.dto.EmployeeDto;
import college.spb.at.employeemanager.model.entity.Department;
import college.spb.at.employeemanager.model.entity.Employee;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-22T22:12:28-0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee toEntity(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        Employee.EmployeeBuilder employee = Employee.builder();

        employee.department( employeeDtoToDepartment( employeeDto ) );
        employee.id( employeeDto.id() );
        employee.name( employeeDto.name() );
        employee.address( employeeDto.address() );
        employee.phone( employeeDto.phone() );
        employee.email( employeeDto.email() );
        employee.birthDate( employeeDto.birthDate() );

        return employee.build();
    }

    @Override
    public EmployeeDto toDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        Long departmentId = null;
        Long id = null;
        String name = null;
        String address = null;
        String phone = null;
        String email = null;
        LocalDate birthDate = null;

        departmentId = employeeDepartmentId( employee );
        id = employee.getId();
        name = employee.getName();
        address = employee.getAddress();
        phone = employee.getPhone();
        email = employee.getEmail();
        birthDate = employee.getBirthDate();

        EmployeeDto employeeDto = new EmployeeDto( id, name, address, phone, email, birthDate, departmentId );

        return employeeDto;
    }

    @Override
    public Employee partialUpdate(EmployeeDto employeeDto, Employee employee) {
        if ( employeeDto == null ) {
            return employee;
        }

        if ( employee.getDepartment() == null ) {
            employee.setDepartment( Department.builder().build() );
        }
        employeeDtoToDepartment1( employeeDto, employee.getDepartment() );
        if ( employeeDto.id() != null ) {
            employee.setId( employeeDto.id() );
        }
        if ( employeeDto.name() != null ) {
            employee.setName( employeeDto.name() );
        }
        if ( employeeDto.address() != null ) {
            employee.setAddress( employeeDto.address() );
        }
        if ( employeeDto.phone() != null ) {
            employee.setPhone( employeeDto.phone() );
        }
        if ( employeeDto.email() != null ) {
            employee.setEmail( employeeDto.email() );
        }
        if ( employeeDto.birthDate() != null ) {
            employee.setBirthDate( employeeDto.birthDate() );
        }

        return employee;
    }

    protected Department employeeDtoToDepartment(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        Department.DepartmentBuilder department = Department.builder();

        department.id( employeeDto.departmentId() );

        return department.build();
    }

    private Long employeeDepartmentId(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Department department = employee.getDepartment();
        if ( department == null ) {
            return null;
        }
        Long id = department.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected void employeeDtoToDepartment1(EmployeeDto employeeDto, Department mappingTarget) {
        if ( employeeDto == null ) {
            return;
        }

        if ( employeeDto.departmentId() != null ) {
            mappingTarget.setId( employeeDto.departmentId() );
        }
    }
}
