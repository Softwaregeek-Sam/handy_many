package com.sumit.handymany.user.dtos;

import com.sumit.handymany.user.enums.AvailabilityStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CompleteWorkerProfile {
    private String profession;
    private int experienceYears;
    private BigDecimal hourlyRate;
    private List<String> skills;
}
