package com.tech.module5.SecurityApplication.services;

import com.tech.module5.SecurityApplication.entities.Session;
import com.tech.module5.SecurityApplication.entities.UserApp;
import com.tech.module5.SecurityApplication.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    private final SessionRepository sessionRepository;

    private final int SESSION_LIMIT = 2;
    public void generateNewSession(UserApp user,String refreshToken){
        List<Session>userSessions = sessionRepository.findByUser(user);
        if(userSessions.size() == SESSION_LIMIT){
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));

            Session leastRecentlyUsedSession = userSessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }
        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        sessionRepository.save(newSession);

    }

    public void validateSession(String refreshToken){
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new SessionAuthenticationException("Session not found for refresh token"+refreshToken));
        log.info("Session : {}",session);
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

    public void logout(String refreshToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new SessionAuthenticationException("Invalid refresh token"));
        sessionRepository.delete(session);
    }
}
