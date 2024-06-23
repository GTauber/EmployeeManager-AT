package college.spb.at.employeemanager.mapper;

import college.spb.at.employeemanager.enums.Role;
import college.spb.at.employeemanager.model.dto.UserDto;
import college.spb.at.employeemanager.model.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-22T22:12:28-0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDto.id() );
        user.name( userDto.name() );
        user.email( userDto.email() );
        user.password( userDto.password() );
        Set<Role> set = userDto.roles();
        if ( set != null ) {
            user.roles( new LinkedHashSet<Role>( set ) );
        }

        return user.build();
    }

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        String id = null;
        String name = null;
        String email = null;
        String password = null;
        Set<Role> roles = null;

        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();
        Set<Role> set = user.getRoles();
        if ( set != null ) {
            roles = new LinkedHashSet<Role>( set );
        }

        UserDto userDto = new UserDto( id, name, email, password, roles );

        return userDto;
    }

    @Override
    public void partialUpdate(UserDto userDto, User user) {
        if ( userDto == null ) {
            return;
        }

        if ( userDto.id() != null ) {
            user.setId( userDto.id() );
        }
        if ( userDto.name() != null ) {
            user.setName( userDto.name() );
        }
        if ( userDto.email() != null ) {
            user.setEmail( userDto.email() );
        }
        if ( userDto.password() != null ) {
            user.setPassword( userDto.password() );
        }
        if ( user.getRoles() != null ) {
            Set<Role> set = userDto.roles();
            if ( set != null ) {
                user.getRoles().clear();
                user.getRoles().addAll( set );
            }
        }
        else {
            Set<Role> set = userDto.roles();
            if ( set != null ) {
                user.setRoles( new LinkedHashSet<Role>( set ) );
            }
        }
    }
}
