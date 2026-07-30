package com.tech.module5.SecurityApplication.services;

import com.tech.module5.SecurityApplication.dto.LoginDto;
import com.tech.module5.SecurityApplication.dto.LoginResponseDto;
import com.tech.module5.SecurityApplication.entities.Session;
import com.tech.module5.SecurityApplication.entities.UserApp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;

    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        UserApp user = (UserApp) authenticate.getPrincipal();
        assert user != null;
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        sessionService.generateNewSession(user,refreshToken);

        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }

    public LoginResponseDto refreshToken(String oldRefreshToken) {

        Long userId = jwtService.getUserIdFromToken(oldRefreshToken);
        sessionService.validateSession(oldRefreshToken);
        UserApp user = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        sessionService.rotateSession(user,oldRefreshToken,newRefreshToken);
        return new LoginResponseDto(user.getId(),accessToken,newRefreshToken);
    }

    public void logout(String refreshToken) {
        sessionService.logout(refreshToken);
    }

}
