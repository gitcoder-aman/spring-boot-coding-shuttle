package com.tech.module5.SecurityApplication.entities;

import com.tech.module5.SecurityApplication.entities.enums.Permission;
import com.tech.module5.SecurityApplication.entities.enums.Role;
import com.tech.module5.SecurityApplication.util.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tech.module5.SecurityApplication.util.PermissionMapping.getAuthoritiesForRole;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserApp implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @Enumerated(EnumType.STRING)
//    private Set<Permission> permissions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<SimpleGrantedAuthority> authorities = roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//                .collect(Collectors.toSet());
//        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.name())));

        Set<SimpleGrantedAuthority>authorities = new HashSet<>();
        roles.forEach(role->{
            Set<SimpleGrantedAuthority> permissions =  PermissionMapping.getAuthoritiesForRole(role);
            authorities.addAll(permissions);
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
        });

        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
