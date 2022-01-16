package me.nos.jwtgraphqljava.dtos;

import lombok.Data;
import me.nos.jwtgraphqljava.validation.ValidUsername;

import javax.validation.constraints.NotBlank;

@Data
public class ChangeUsernameDto {
    @NotBlank
    private String oldUsername;
    @ValidUsername
    private String newUsername;
}
