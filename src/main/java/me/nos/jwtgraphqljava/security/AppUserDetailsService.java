package me.nos.jwtgraphqljava.security;

import me.nos.jwtgraphqljava.model.AppUser;
import me.nos.jwtgraphqljava.services.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final IUserService userService;

    public AppUserDetailsService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = userService.findByUsername(username);

        user.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        return user.map(AppUserDetails::new).get();
    }
}
