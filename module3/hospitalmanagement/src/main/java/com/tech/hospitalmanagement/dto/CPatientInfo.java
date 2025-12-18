package com.tech.hospitalmanagement.dto;

import lombok.Data;

//Concrete projection
@Data
public class CPatientInfo {
    private final Long id;
    private final String name;
}
