package com.tech.hospitalmanagement.dto;

import com.tech.hospitalmanagement.entities.type.BloodGroupType;
import lombok.Data;

@Data
public class BloodGroupStats {
    private final BloodGroupType bloodGroupType;
    private final Long count;
}
