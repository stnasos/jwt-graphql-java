package me.nos.jwtgraphqljava.utils;

import lombok.extern.slf4j.Slf4j;
import me.nos.jwtgraphqljava.model.AppRole;
import me.nos.jwtgraphqljava.model.AppUser;
import me.nos.jwtgraphqljava.repositories.AppRoleRepository;
import me.nos.jwtgraphqljava.repositories.AppUserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;

@Component
@Slf4j
public class DataUtil {

    private final AppUserRepository userRepo;
    private final AppRoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public DataUtil(AppUserRepository userRepo, AppRoleRepository roleRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
    }

    @EventListener
    @Transactional
    public void appReady(ApplicationReadyEvent event) {
        if (userRepo.findAll().size() == 0) {

            AppRole adminRole = AppRole.builder().name("ADMIN").build();
            AppRole managerRole = AppRole.builder().name("MANAGER").build();
            AppRole userRole = AppRole.builder().name("USER").build();
            roleRepo.save(adminRole);
            roleRepo.save(managerRole);
            roleRepo.save(userRole);

            String pass = encoder.encode("admin");
            AppUser user = AppUser.builder()
                    .username("admin")
                    .password(pass)
                    .enabled(true)
                    .roles(new LinkedHashSet<>())
                    .build();

            user.addRole(adminRole);
            user.addRole(managerRole);
            user.addRole(userRole);
            userRepo.save(user);
        }
    }
}
