package me.nos.jwtgraphqljava.repositories;

import me.nos.jwtgraphqljava.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByUsername(String username);

    @Query("select u from AppUser u left join fetch u.roles r where u.username = ?1")
    Optional<AppUser> findByUsernameWithRoles(String username);

    @Query("select distinct u from AppUser u left join fetch u.roles r where u.id in ?1")
    List<AppUser> getRolesForUsers(List<UUID> userIds);

    boolean existsByUsername(String username);
}