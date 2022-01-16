package me.nos.jwtgraphqljava.model;

import lombok.*;
import me.nos.jwtgraphqljava.dtos.AppUserDetailsDto;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDetails {
    @Id
    private UUID id;

    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String address;
    private String email;
    private String mobile;
    private LocalDate hiredOn;
    private BigDecimal salary;

    @OneToOne(fetch = LAZY)
    @MapsId
    @ToString.Exclude
    private AppUser user;

    public AppUserDetailsDto mapToUserDetailsDto() {
        return new AppUserDetailsDto(this.getId(), this.getFirstname(), this.getLastname(),
                this.getDateOfBirth(), this.getAddress(), this.getEmail(), this.getMobile(),
                this.getHiredOn(), this.getSalary());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUserDetails appUserDetails = (AppUserDetails) o;
        return id != null && Objects.equals(id, appUserDetails.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
