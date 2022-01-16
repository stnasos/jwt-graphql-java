package me.nos.jwtgraphqljava.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class AppRoleDto {
    private int id;
    private String name;
}
