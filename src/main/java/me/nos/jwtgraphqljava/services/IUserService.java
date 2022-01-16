package me.nos.jwtgraphqljava.services;

import me.nos.jwtgraphqljava.dtos.*;
import me.nos.jwtgraphqljava.model.AppUser;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.*;

public interface IUserService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    AppUserDto findByUsername(String username);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<AppUserDto> getAllUsers();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Map<AppUserDto, Set<AppRoleDto>> getRolesForUsers(List<UUID> userIds);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Map<AppUserDto, AppUserDetailsDto> getAppUserDetails(List<AppUserDto> users);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    AppUserDto addUser(AddUserDto userInput);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    AppUser changeUsername(ChangeUsernameDto data);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    boolean existsByUsername(String username);
}