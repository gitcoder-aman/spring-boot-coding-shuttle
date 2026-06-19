package com.tech.spring.ai.service;

import com.tech.spring.ai.entity.Tut;

import java.util.List;

public interface ChatService {
    String chat(String query);
    String chatTemplate();
}
