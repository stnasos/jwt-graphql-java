package me.nos.jwtgraphqljava.model;

import lombok.*;
import me.nos.jwtgraphqljava.dtos.AppUserDto;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AppUser implements UserDetails {
    @Id
    @Setter(value = AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean enabled = true;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean accountNonExpired = true;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean accountNonLocked = true;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean credentialsNonExpired = true;

    @ManyToMany(fetch = LAZY)
    @ToString.Exclude
    private Set<AppRole> roles = new LinkedHashSet<>();

    public AppUser(String username, String password) {
        this.username = username.toLowerCase();
        this.password = password;
    }

    public void setUsername(@NotNull String username) {
        this.username = username.toLowerCase();
    }

    public AppUserDto mapToAppUserDto() {
        return new AppUserDto(this.getId(), this.getUsername(), this.getPassword(), this.isEnabled(),
                this.isAccountNonExpired(), this.isAccountNonLocked(), this.isCredentialsNonExpired());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles().stream()
                .map(appRole -> new SimpleGrantedAuthority("ROLE_" + appRole.getName().toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUser appUser = (AppUser) o;
        return id != null && Objects.equals(id, appUser.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
