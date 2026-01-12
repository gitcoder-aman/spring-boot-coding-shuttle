package com.tech.module5.SecurityApplication.repositories;

import com.tech.module5.SecurityApplication.entities.Session;
import com.tech.module5.SecurityApplication.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findByUser(UserApp user);

    Optional<Session> findByRefreshToken(String refreshToken);
}
