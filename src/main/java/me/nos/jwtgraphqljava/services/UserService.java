package me.nos.jwtgraphqljava.services;

import me.nos.jwtgraphqljava.model.AppUser;
import me.nos.jwtgraphqljava.repositories.AppUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final AppUserRepository userRepository;

    public UserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
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
}
