package me.nos.jwtgraphqljava.controllers;

import lombok.extern.log4j.Log4j2;
import me.nos.jwtgraphqljava.dtos.LoginInput;
import me.nos.jwtgraphqljava.dtos.LoginOutput;
import me.nos.jwtgraphqljava.security.AppUserDetailsService;
import me.nos.jwtgraphqljava.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, AppUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("test")
    public ResponseEntity<?> getText() {
        return ResponseEntity.ok("Test Passed!!!");
    }

    @PostMapping("auth")
    public ResponseEntity<?> authenticate(@RequestBody LoginInput loginInput) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInput.getUsername(), loginInput.getPassword())
        );

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginInput.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginOutput(jwt));
    }
}