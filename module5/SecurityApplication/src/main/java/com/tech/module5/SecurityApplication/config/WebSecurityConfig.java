package com.tech.module5.SecurityApplication.config;

import com.tech.module5.SecurityApplication.entities.enums.Permission;
import com.tech.module5.SecurityApplication.entities.enums.Role;
import com.tech.module5.SecurityApplication.filters.JwtAuthFilter;
import com.tech.module5.SecurityApplication.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.tech.module5.SecurityApplication.entities.enums.Permission.*;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] publicRoutes = {
            "/error","/auth/**","/home.html"
    };

    @Bean
    SecurityFilterChain apiSecurityChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers(HttpMethod.GET,"/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/posts/**")
                        .hasAnyRole(Role.ADMIN.name(),Role.CREATOR.name())
                        .requestMatchers(HttpMethod.POST,"/posts/**")
                        .hasAnyAuthority(POST_CREATE.name())
                        .requestMatchers(HttpMethod.GET,"/posts/**")
                        .hasAuthority(POST_VIEW.name())
                        .requestMatchers(HttpMethod.PUT,"/posts/**")
                        .hasAuthority(POST_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE,"/posts/**")
                        .hasAuthority(POST_DELETE.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    UserDetailsService myInMemoryUserDetailsService() {
//        UserDetails normalUser = User
//                .withUsername("aman")
//                .password(passwordEncoder().encode("aman123"))
//                .roles("USER")
//                .build();
//
//        UserDetails adminUser = User
//                .withUsername("admin")
//                .password(passwordEncoder().encode("admin123"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        return config.getAuthenticationManager();
    }
}
