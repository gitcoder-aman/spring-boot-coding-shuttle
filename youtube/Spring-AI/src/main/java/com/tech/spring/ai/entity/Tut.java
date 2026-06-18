package com.tech.spring.ai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tut {
    private String title;
    private String content;
    private String createdYear;
}
