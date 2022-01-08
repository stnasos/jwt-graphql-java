package me.nos.jwtgraphqljava.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @Column(name = "user_id")
    private UUID id;

    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String address;
    private String email;
    private String mobile;
    private LocalDate hiredOn;
    private BigDecimal salary;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Employee employee = (Employee) o;
        return id != null && Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
