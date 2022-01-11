package me.nos.jwtgraphqljava.dtos;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;

@Data
public class AddUserDto {
    @NotNull
    @NotBlank
    public String username;
    @NotNull
    @NotBlank
    public String password;
    @NotNull
    @NotBlank
    public String confirmPassword;
}
