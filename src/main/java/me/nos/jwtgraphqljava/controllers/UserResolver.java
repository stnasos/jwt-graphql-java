package me.nos.jwtgraphqljava.controllers;

import lombok.AllArgsConstructor;
import me.nos.jwtgraphqljava.dtos.LoginInput;
import me.nos.jwtgraphqljava.dtos.LoginOutput;
import me.nos.jwtgraphqljava.dtos.UserInput;
import me.nos.jwtgraphqljava.dtos.AddUserDto;
import me.nos.jwtgraphqljava.model.AppUser;
import me.nos.jwtgraphqljava.model.Employee;
import me.nos.jwtgraphqljava.services.IAuthService;
import me.nos.jwtgraphqljava.services.IUserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class UserResolver {

    private final IUserService userService;
    private final IAuthService authService;

    @QueryMapping
    public LoginOutput login(@Argument("loginInput") @Valid LoginInput loginInput) {
        return new LoginOutput(authService.login(loginInput.getUsername(), loginInput.getPassword()));
    }

    @QueryMapping
    public List<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @SchemaMapping
    public BigDecimal salary(Employee employee) {
        return userService.getSalaryForEmp(employee);
    }

    @MutationMapping
    public AppUser addUser(@Argument @Valid AddUserDto user) {
        return userService.addUser(user);
    }

    @MutationMapping
    public AppUser editUser(@Argument UUID id, @Argument @Valid UserInput user) {
        return userService.editUser(id, user);
    }
}
