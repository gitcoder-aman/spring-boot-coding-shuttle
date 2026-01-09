package com.tech.module5.SecurityApplication.repositories;

import com.tech.module5.SecurityApplication.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp,Long> {

    Optional<UserApp>findByEmail(String email);
}
