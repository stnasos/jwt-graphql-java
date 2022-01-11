package me.nos.jwtgraphqljava.services;

import org.springframework.security.access.prepost.PreAuthorize;

public interface IAuthService {

    @PreAuthorize("isAnonymous()")
    String login(String username, String password);
}
