package com.tech.module5.SecurityApplication.services;

import com.tech.module5.SecurityApplication.dto.LoginDto;
import com.tech.module5.SecurityApplication.dto.SignUpDto;
import com.tech.module5.SecurityApplication.dto.UserDto;
import com.tech.module5.SecurityApplication.entities.UserApp;
import com.tech.module5.SecurityApplication.exceptions.ResourceNotFoundException;
import com.tech.module5.SecurityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User with email " + username + " not found"));
    }

    public UserApp getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
    }

    public UserDto signup(SignUpDto signUpDto) {
        Optional<UserApp> user = userRepository.findByEmail(signUpDto.getEmail());
        if (user.isPresent()) {
            throw new BadCredentialsException("User with email already exists: " + signUpDto.getEmail());
        }
        UserApp toBeCreatedUserApp = modelMapper.map(signUpDto, UserApp.class);
        toBeCreatedUserApp.setPassword(passwordEncoder.encode(toBeCreatedUserApp.getPassword()));
        UserApp savedUserApp = userRepository.save(toBeCreatedUserApp);
        return modelMapper.map(savedUserApp, UserDto.class);
    }
}
