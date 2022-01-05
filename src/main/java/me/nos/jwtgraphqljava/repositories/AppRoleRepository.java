package me.nos.jwtgraphqljava.repositories;

import me.nos.jwtgraphqljava.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppRoleRepository extends JpaRepository<AppRole, UUID> {
    Optional<AppRole> findByName(String name);
}