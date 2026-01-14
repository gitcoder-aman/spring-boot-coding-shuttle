package com.tech.module5.SecurityApplication.dto;

import com.tech.module5.SecurityApplication.entities.enums.Permission;
import com.tech.module5.SecurityApplication.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Set<Role> roles;
    private Set<Permission>permissions;
}
