package me.nos.jwtgraphqljava.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nos.jwtgraphqljava.model.AppRole;
import me.nos.jwtgraphqljava.model.AppUser;
import me.nos.jwtgraphqljava.model.AppUserDetails;
import me.nos.jwtgraphqljava.repositories.AppRoleRepository;
import me.nos.jwtgraphqljava.repositories.AppUserRepository;
import me.nos.jwtgraphqljava.repositories.AppUserDetailsRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Slf4j
@AllArgsConstructor
public class DataUtil {

    private final AppUserRepository userRepo;
    private final AppRoleRepository roleRepo;
    private final AppUserDetailsRepository userDetailsRepo;
    private final PasswordEncoder encoder;

    @EventListener
    @Transactional
    public void appReady(ApplicationReadyEvent event) {
        if (userRepo.count() == 0) {
            AppUser user = new AppUser("admin", encoder.encode("admin"));

            user.getRoles().add(new AppRole("Admin"));
            user.getRoles().add(new AppRole("Manager"));
            user.getRoles().add(new AppRole("User"));


            roleRepo.saveAll(user.getRoles());
            userRepo.save(user);

            userDetailsRepo.save(new AppUserDetails(null, "Nasos", "Stou", LocalDate.parse("1981-12-15"),
                    "Kallithea", "nos@nos.gr", "6977226000", LocalDate.parse("2005-11-17"),
                    BigDecimal.valueOf(1700), user));

        }
    }
}
