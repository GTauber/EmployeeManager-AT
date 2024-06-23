package college.spb.at.employeemanager.config;

import college.spb.at.employeemanager.enums.Role;
import college.spb.at.employeemanager.model.entity.User;
import college.spb.at.employeemanager.repository.UserRepository;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(-100)
@Slf4j
public class UserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        var user = User.builder()
            .name("user0")
            .email("user0")
            .password(passwordEncoder.encode("0"))
            .roles(Set.of(Role.ROLE_USER))
            .build();
        userRepository.save(user);
        log.info("Inserted initial user0 data into MongoDB.");

    }
}
