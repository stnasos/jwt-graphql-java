package me.nos.jwtgraphqljava.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.nos.jwtgraphqljava.validation.PasswordMatch;
import me.nos.jwtgraphqljava.validation.ValidPassword;
import me.nos.jwtgraphqljava.validation.ValidUsername;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;

@Data
@PasswordMatch
@AllArgsConstructor
@EqualsAndHashCode
public class AddUserDto {

    @ValidUsername
    private String username;
    @ValidPassword
    private String password;
    @NotBlank
    private String confirmPassword;
}
