package me.nos.jwtgraphqljava.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nos.jwtgraphqljava.dtos.*;
import me.nos.jwtgraphqljava.model.AppUser;
import me.nos.jwtgraphqljava.services.IAuthService;
import me.nos.jwtgraphqljava.services.IUserService;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserResolver {

    private final IUserService userService;
    private final IAuthService authService;

    @QueryMapping
    public LoginOutput login(@Argument("loginInput") @Valid LoginInput loginInput) {
        return new LoginOutput(authService.login(loginInput.getUsername(), loginInput.getPassword()));
    }

    @QueryMapping
    public AppUserDto user(@Argument String username) {
        return userService.findByUsername(username);
    }

    @QueryMapping
    public List<AppUserDto> users() {
        return userService.getAllUsers();
    }

    @BatchMapping(typeName = "User", field = "roles")
    public Map<AppUserDto, Set<AppRoleDto>> roles(List<AppUserDto> users) {
        var userIds = users.stream().map(AppUserDto::getId).collect(Collectors.toList());
        return userService.getRolesForUsers(userIds);
    }

    @BatchMapping(typeName = "User", field = "appUserDetails")
    public Map<AppUserDto, AppUserDetailsDto> appUserDetails(List<AppUserDto> users) {
        log.info("Getting employee for users: {}", users);
        return userService.getAppUserDetails(users);
    }

    @MutationMapping
    public AppUserDto addUser(@Argument @Valid AddUserDto user) {
        return userService.addUser(user);
    }

    @MutationMapping
    public AppUser changeUsername(@Argument @Valid ChangeUsernameDto data) {
        return userService.changeUsername(data);
    }
}
