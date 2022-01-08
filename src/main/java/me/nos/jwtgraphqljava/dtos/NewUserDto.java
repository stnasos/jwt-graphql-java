package me.nos.jwtgraphqljava.dtos;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class NewUserDto {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private String firstname;
    private String lastname;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    private String address;
    @Email(message = "Enter a valid email address")
    private String email;
    private String mobile;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate hiredOn;
    @Min(value = 0, message = "salary cannot be negative")
    private BigDecimal salary;
}
