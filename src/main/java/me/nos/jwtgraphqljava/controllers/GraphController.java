package me.nos.jwtgraphqljava.controllers;

import me.nos.jwtgraphqljava.dtos.LoginInput;
import me.nos.jwtgraphqljava.dtos.LoginOutput;
import me.nos.jwtgraphqljava.dtos.NewUserDto;
import me.nos.jwtgraphqljava.model.AppUser;
import me.nos.jwtgraphqljava.security.AppUserDetailsService;
import me.nos.jwtgraphqljava.services.IUserService;
import me.nos.jwtgraphqljava.utils.JwtUtil;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public String greeting() {
        return "Hello You!!!";
    }

    @QueryMapping
    @PreAuthorize("isAnonymous()")
    public LoginOutput login(@Argument("loginInput") @Valid LoginInput loginInput) {
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

    @MutationMapping
    public AppUser addUser(@Argument @Valid NewUserDto user) {
        if (userService.existsByUsername(user.getUsername()))
            throw new EntityExistsException("Username " + user.getUsername() + " already exists!");
        return userService.addNewUser(user);
    }
}
