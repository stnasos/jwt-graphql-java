package me.nos.jwtgraphqljava.model;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private Set<AppUser> users = new LinkedHashSet<>();

    public void addUser(AppUser user) {
        this.users.add(user);
    }

    public void removeUser(AppUser user) {
        this.users.remove(user);
    }
}
