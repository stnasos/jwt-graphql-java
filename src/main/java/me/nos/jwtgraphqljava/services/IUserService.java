package me.nos.jwtgraphqljava.services;

import me.nos.jwtgraphqljava.dtos.AddUserDto;
import me.nos.jwtgraphqljava.dtos.UserInput;
import me.nos.jwtgraphqljava.model.AppUser;
import me.nos.jwtgraphqljava.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    @PreAuthorize("hasAuthority('ADMIN')")
    Optional<AppUser> findByUsername(String username);

    @PreAuthorize("hasAuthority('ADMIN')")
    List<AppUser> getAllUsers();

    @PreAuthorize("hasAuthority('ADMIN')")
    Page<AppUser> getPagedUsers(Pageable pageable);

    @PreAuthorize("hasAuthority('ADMIN')")
    AppUser addUser(AddUserDto userInput);

    @PreAuthorize("hasAuthority('ADMIN')")
    AppUser editUser(UUID id, UserInput user);

    @PreAuthorize("hasAuthority('ADMIN')")
    boolean existsByUsername(String username);

    @PreAuthorize("hasAuthority('HR')")
    BigDecimal getSalaryForEmp(Employee employee);
}