package me.nos.jwtgraphqljava.services;

import me.nos.jwtgraphqljava.dtos.NewUserDto;
import me.nos.jwtgraphqljava.model.AppUser;
import me.nos.jwtgraphqljava.model.Employee;
import me.nos.jwtgraphqljava.repositories.AppUserRepository;
import me.nos.jwtgraphqljava.repositories.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final AppUserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder encoder;

    public UserService(AppUserRepository userRepository, EmployeeRepository employeeRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<AppUser> getPagedUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public AppUser addNewUser(NewUserDto newUserDto) {
        AppUser user = AppUser.builder()
                .username(newUserDto.getUsername())
                .password(encoder.encode(newUserDto.getPassword()))
                .enabled(newUserDto.isEnabled())
                .accountNonExpired(newUserDto.isAccountNonExpired())
                .accountNonLocked(newUserDto.isAccountNonLocked())
                .credentialsNonExpired(newUserDto.isCredentialsNonExpired())
                .build();
        userRepository.save(user);

        Employee emp = Employee.builder()
                .firstname(newUserDto.getFirstname())
                .lastname(newUserDto.getLastname())
                .dateOfBirth(newUserDto.getDateOfBirth())
                .address(newUserDto.getAddress())
                .email(newUserDto.getEmail())
                .mobile(newUserDto.getMobile())
                .hiredOn(newUserDto.getHiredOn())
                .salary(newUserDto.getSalary())
                .user(user).build();
        employeeRepository.save(emp);

        user.setEmployee(emp);
        return user;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
