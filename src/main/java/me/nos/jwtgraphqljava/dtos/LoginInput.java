package me.nos.jwtgraphqljava.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginInput {
    @NotNull(message = "username cannot be null")
    @NotBlank(message = "username cannot be blank")
    private String username;
    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    private String password;
}