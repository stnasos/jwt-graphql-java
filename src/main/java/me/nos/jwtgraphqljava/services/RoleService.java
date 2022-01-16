package me.nos.jwtgraphqljava.services;

import lombok.AllArgsConstructor;
import me.nos.jwtgraphqljava.repositories.AppRoleRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService implements IRoleService {
    private final AppRoleRepository roleRepository;
}
