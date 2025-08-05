package com.sumit.handymany.user.dtos;

import com.sumit.handymany.user.enums.Gender;
import com.sumit.handymany.user.model.Address;
import com.sumit.handymany.user.model.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String phone;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Address address;
    private List<String> roles;
    private boolean isProfileCompleted;
}
