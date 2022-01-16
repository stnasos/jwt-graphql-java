package me.nos.jwtgraphqljava.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nos.jwtgraphqljava.dtos.*;
import me.nos.jwtgraphqljava.errors.UserNotFoundException;
import me.nos.jwtgraphqljava.model.AppRole;
import me.nos.jwtgraphqljava.model.AppUser;
import me.nos.jwtgraphqljava.model.AppUserDetails;
import me.nos.jwtgraphqljava.repositories.AppRoleRepository;
import me.nos.jwtgraphqljava.repositories.AppUserRepository;
import me.nos.jwtgraphqljava.repositories.AppUserDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final AppUserRepository userRepo;
    private final AppRoleRepository roleRepo;
    private final AppUserDetailsRepository appUserDetailsRepo;
    private final PasswordEncoder encoder;

    @Override
    public AppUserDto findByUsername(String username) {
        return userRepo.findByUsername(username.toLowerCase())
                .orElseThrow(UserNotFoundException::new).mapToAppUserDto();
    }

    @Override
    public List<AppUserDto> getAllUsers() {
        return userRepo.findAll().stream().map(AppUser::mapToAppUserDto).collect(Collectors.toList());
    }

    @Override
    public Map<AppUserDto, Set<AppRoleDto>> getRolesForUsers(List<UUID> userIds) {
        List<AppUser> t = userRepo.getRolesForUsers(userIds);
        return t.stream().collect(Collectors.toMap(AppUser::mapToAppUserDto,
                u -> u.getRoles().stream().map(AppRole::mapToAppRoleDto).collect(Collectors.toSet())));
    }

    @Override
    public Map<AppUserDto, AppUserDetailsDto> getAppUserDetails(List<AppUserDto> users) {
        List<UUID> detailIds = users.stream().map(AppUserDto::getId).collect(Collectors.toList());
        List<AppUserDetails> appUserDetails = appUserDetailsRepo.findAllById(detailIds);

        return appUserDetails.stream()
                .collect(Collectors.toMap(ud -> ud.getUser().mapToAppUserDto(), AppUserDetails::mapToUserDetailsDto));
    }

    @Override
    public AppUserDto addUser(AddUserDto userDto) {
        if (userRepo.existsByUsername(userDto.getUsername()))
            throw new EntityExistsException("Username " + userDto.getUsername() + " already exists!");

        AppUser user = new AppUser(userDto.getUsername(), encoder.encode(userDto.getPassword()));

        AppRole role = roleRepo.findByName("User").orElseThrow(() -> new EntityNotFoundException("Role 'User' not found"));

        user.getRoles().add(role);

        userRepo.save(user);

        return user.mapToAppUserDto();
    }

    @Override
    public AppUser changeUsername(ChangeUsernameDto data) {
        if (userRepo.existsByUsername(data.getNewUsername()))
            throw new EntityExistsException("Username " + data.getNewUsername() + " already exists!");

        AppUser user = userRepo.findByUsername(data.getOldUsername()).orElseThrow(() -> new EntityNotFoundException("User '" + data.getOldUsername() + "' not found"));

        user.setUsername(data.getNewUsername());

        userRepo.save(user);

        return user;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username.toLowerCase());
    }
}
