package me.nos.jwtgraphqljava.repositories;

import me.nos.jwtgraphqljava.model.AppUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppUserDetailsRepository extends JpaRepository<AppUserDetails, UUID> {
}