package me.nos.jwtgraphqljava.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean enabled;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean accountNonExpired;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean accountNonLocked;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean credentialsNonExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AppRole> roles = new java.util.LinkedHashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Employee employee;

    public void addRole(AppRole role) {
        this.roles.add(role);
    }

    public void removeRole(AppRole role) {
        this.roles.remove(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles().stream()
                .map(appRole -> new SimpleGrantedAuthority(appRole.getName()))
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
