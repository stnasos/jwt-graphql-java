package me.nos.jwtgraphqljava.utils;

import lombok.extern.slf4j.Slf4j;
import me.nos.jwtgraphqljava.model.AppRole;
import me.nos.jwtgraphqljava.model.AppUser;
import me.nos.jwtgraphqljava.model.Employee;
import me.nos.jwtgraphqljava.repositories.AppRoleRepository;
import me.nos.jwtgraphqljava.repositories.AppUserRepository;
import me.nos.jwtgraphqljava.repositories.EmployeeRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.LinkedHashSet;

@Component
@Slf4j
public class DataUtil {

    private final AppUserRepository userRepo;
    private final AppRoleRepository roleRepo;
    private final EmployeeRepository empRepo;
    private final PasswordEncoder encoder;

    public DataUtil(AppUserRepository userRepo, AppRoleRepository roleRepo, EmployeeRepository empRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.empRepo = empRepo;
        this.encoder = encoder;
    }

    @EventListener
    @Transactional
    public void appReady(ApplicationReadyEvent event) throws ParseException {
        if (userRepo.findAll().size() == 0) {

            AppRole adminRole = AppRole.builder().name("ADMIN").build();
            AppRole managerRole = AppRole.builder().name("MANAGER").build();
            AppRole userRole = AppRole.builder().name("USER").build();
            roleRepo.save(adminRole);
            roleRepo.save(managerRole);
            roleRepo.save(userRole);

            String pass = encoder.encode("admin");
            AppUser user = AppUser.builder()
                    .username("admin").password(pass)
                    .enabled(true).accountNonExpired(true)
                    .accountNonLocked(true).credentialsNonExpired(true)
                    .roles(new LinkedHashSet<>()).build();

            user.addRole(adminRole);
            user.addRole(managerRole);
            user.addRole(userRole);
            userRepo.save(user);

            Employee employee = Employee.builder()
                    .firstname("nasos").lastname("stou")
                    .dateOfBirth(LocalDate.parse("1981-12-15"))
                    .address("Kallithea").email("nos@nos.gr")
                    .mobile("6977226000")
                    .hiredOn(LocalDate.parse("2005-11-17"))
                    .salary(BigDecimal.valueOf(1700))
                    .user(user).build();
            empRepo.save(employee);
        }
    }
}
