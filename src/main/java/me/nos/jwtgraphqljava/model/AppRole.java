package me.nos.jwtgraphqljava.model;

import lombok.*;
import me.nos.jwtgraphqljava.dtos.AppRoleDto;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AppRole {
    @Id
    @Setter(value = AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = LAZY)
    @ToString.Exclude
    private Set<AppUser> users = new LinkedHashSet<>();

    public AppRole(String name) {
        this.name = name;
    }

    public AppRoleDto mapToAppRoleDto() {
        return new AppRoleDto(this.getId(), this.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppRole appRole = (AppRole) o;
        return id != 0 && Objects.equals(id, appRole.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
