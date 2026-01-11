package com.tech.module5.SecurityApplication.config;

import com.tech.module5.SecurityApplication.filters.JwtAuthFilter;
import com.tech.module5.SecurityApplication.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    @Order(1)
    SecurityFilterChain oauth2SecurityChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/oauth2/**", "/login/**")
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2Login(oauth2Config->oauth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain apiSecurityChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/posts","/error","/auth/**").permitAll()
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
