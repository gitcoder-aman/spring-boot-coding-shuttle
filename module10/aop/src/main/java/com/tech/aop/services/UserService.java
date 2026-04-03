package com.tech.aop.services;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void getUser() {
        throw new RuntimeException("User not found");
    }
}