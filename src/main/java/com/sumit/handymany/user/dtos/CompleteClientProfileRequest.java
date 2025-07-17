package com.sumit.handymany.user.dtos;

import com.sumit.handymany.user.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CompleteClientProfileRequest{
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
}
