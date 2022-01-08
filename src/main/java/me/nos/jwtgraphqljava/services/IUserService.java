package me.nos.jwtgraphqljava.services;

import me.nos.jwtgraphqljava.dtos.NewUserDto;
import me.nos.jwtgraphqljava.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<AppUser> findByUsername(String username);

    List<AppUser> getAllUsers();

    Page<AppUser> getPagedUsers(Pageable pageable);

    AppUser addNewUser(NewUserDto newUserDto);

    boolean existsByUsername(String username);
}
