package com.sumit.handymany.user.service;

import com.sumit.handymany.user.dtos.CompleteClientProfileRequest;
import com.sumit.handymany.user.dtos.UserDto;
import com.sumit.handymany.user.model.User;
import com.sumit.handymany.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public UserDto completeClientProfile(Long userId, CompleteClientProfileRequest request) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with phone: " + userId));
        user.setName(request.getName());
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setIsProfileCompleted(true);
         User updated = userRepo.save(user);

         return modelMapper.map(updated, UserDto.class);
    }
}
