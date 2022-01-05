package me.nos.jwtgraphqljava.controllers;

import me.nos.jwtgraphqljava.dtos.LoginInput;
import me.nos.jwtgraphqljava.dtos.LoginOutput;
import me.nos.jwtgraphqljava.model.AppUser;
import me.nos.jwtgraphqljava.security.AppUserDetailsService;
import me.nos.jwtgraphqljava.services.IUserService;
import me.nos.jwtgraphqljava.utils.JwtUtil;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphController {

    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final IUserService userService;

    public GraphController(AuthenticationManager authenticationManager,
                           AppUserDetailsService userDetailsService, JwtUtil jwtUtil,
                           IUserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('ADMIN')") //"hasRole('ROLE_ADMIN')"
    public String greeting() {
        return "Hello You!!!";
    }

    @QueryMapping
    public LoginOutput login(@Argument("loginInput") LoginInput loginInput) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInput.getUsername(), loginInput.getPassword())
        );

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginInput.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return new LoginOutput(jwt);
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @SchemaMapping
    @PreAuthorize("hasAuthority('TEST')")
    public String password(AppUser appUser) {
        return appUser.getPassword();
    }
}
