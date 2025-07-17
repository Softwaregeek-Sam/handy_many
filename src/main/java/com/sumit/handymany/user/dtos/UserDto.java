package com.sumit.handymany.user.dtos;

import com.sumit.handymany.user.enums.Gender;
import com.sumit.handymany.user.enums.Role;
import com.sumit.handymany.user.model.Address;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String phone;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Address address;
    private  Role role;
    private boolean isProfileCompleted;
}
