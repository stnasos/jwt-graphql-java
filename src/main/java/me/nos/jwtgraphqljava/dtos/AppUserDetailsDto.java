package me.nos.jwtgraphqljava.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AppUserDetailsDto {
    private UUID id;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String address;
    private String email;
    private String mobile;
    private LocalDate hiredOn;
    private BigDecimal salary;
}
