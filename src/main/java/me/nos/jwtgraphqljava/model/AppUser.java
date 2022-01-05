package me.nos.jwtgraphqljava.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder @ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AppRole> roles = new java.util.LinkedHashSet<>();

    public void addRole(AppRole role) {
        this.roles.add(role);
    }

    public void removeRole(AppRole role) {
        this.roles.remove(role);
    }
}
