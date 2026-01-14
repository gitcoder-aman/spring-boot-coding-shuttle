package com.tech.module5.SecurityApplication.util;

import com.tech.module5.SecurityApplication.entities.enums.Permission;
import com.tech.module5.SecurityApplication.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tech.module5.SecurityApplication.entities.enums.Permission.*;

public class  PermissionMapping {
    private static Map<Role, Set<Permission>>map = Map.of(
            Role.USER,Set.of(USER_VIEW,POST_VIEW),
            Role.CREATOR,Set.of(USER_VIEW, POST_VIEW,USER_UPDATE,POST_UPDATE),
            Role.ADMIN,Set.of(USER_DELETE,USER_CREATE,POST_DELETE)
    );
    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role){
        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
