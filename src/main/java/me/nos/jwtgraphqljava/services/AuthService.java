package me.nos.jwtgraphqljava.services;

import lombok.AllArgsConstructor;
import me.nos.jwtgraphqljava.security.AppUserDetailsService;
import me.nos.jwtgraphqljava.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public String login(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        return jwtUtil.generateToken(userDetails);
    }
}
