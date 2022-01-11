package me.nos.jwtgraphqljava.services;

import lombok.AllArgsConstructor;
import me.nos.jwtgraphqljava.dtos.AddUserDto;
import me.nos.jwtgraphqljava.dtos.UserInput;
import me.nos.jwtgraphqljava.model.AppRole;
import me.nos.jwtgraphqljava.model.AppUser;
import me.nos.jwtgraphqljava.model.Employee;
import me.nos.jwtgraphqljava.repositories.AppRoleRepository;
import me.nos.jwtgraphqljava.repositories.AppUserRepository;
import me.nos.jwtgraphqljava.repositories.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final AppUserRepository userRepository;
    private final AppRoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder encoder;

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
    public AppUser addUser(AddUserDto userInput) {
        if (userRepository.existsByUsername(userInput.getUsername()))
            throw new EntityExistsException("Username " + userInput.getUsername() + " already exists!");


        AppUser user = AppUser.builder()
                .username(userInput.getUsername())
                .password(encoder.encode(userInput.getPassword()))
                .roles(new LinkedHashSet<>())
                .build();

        AppRole role = roleRepository.findByName("USER").orElseThrow(()
                -> new EntityNotFoundException("Role 'USER' not found"));

        user.addRole(role);

        userRepository.save(user);

//        Employee emp = Employee.builder()
//                .firstname(userInput.getFirstname())
//                .lastname(userInput.getLastname())
//                .dateOfBirth(userInput.getDateOfBirth())
//                .address(userInput.getAddress())
//                .email(userInput.getEmail())
//                .mobile(userInput.getMobile())
//                .hiredOn(userInput.getHiredOn())
//                .salary(userInput.getSalary())
//                .user(user).build();
//        employeeRepository.save(emp);
//
//        user.setEmployee(emp);
        return user;
    }

    @Override
    public AppUser editUser(UUID id, UserInput user) {
        AppUser appUser = userRepository.getById(id);

        if (!Objects.equals(appUser.getUsername(), user.getUsername())) {
            if (userRepository.existsByUsername(user.getUsername()))
                throw new EntityExistsException("Username " + user.getUsername() + " already exists!");
        }

        appUser.setEnabled(user.isEnabled());
        appUser.setAccountNonExpired(user.isAccountNonExpired());
        appUser.setAccountNonLocked(user.isAccountNonLocked());
        appUser.setCredentialsNonExpired(user.isCredentialsNonExpired());
        appUser.getEmployee().setAddress(user.getAddress());
        appUser.getEmployee().setEmail(user.getEmail());
        appUser.getEmployee().setFirstname(user.getFirstname());
        appUser.getEmployee().setDateOfBirth(user.getDateOfBirth());
        appUser.getEmployee().setHiredOn(user.getHiredOn());
        appUser.getEmployee().setLastname(user.getLastname());
        appUser.getEmployee().setMobile(user.getMobile());
        appUser.getEmployee().setSalary(user.getSalary());

        userRepository.save(appUser);

        return appUser;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public BigDecimal getSalaryForEmp(Employee employee) {
        return employee.getSalary();
    }
}
