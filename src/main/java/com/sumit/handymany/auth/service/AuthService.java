package com.sumit.handymany.auth.service;

import com.sumit.handymany.auth.response.AuthResponse;
import com.sumit.handymany.otp.service.OtpService;
import com.sumit.handymany.user.dtos.UserDto;
import com.sumit.handymany.user.model.Role;
import com.sumit.handymany.user.model.User;
import com.sumit.handymany.user.repository.UserRepo;
import com.sumit.handymany.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final OtpService otpService;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final RoleService roleService;


    public void sendOtp(String phone) {
        otpService.createAndSendOtp(phone);

    }

    public AuthResponse verifyOtp(String phone, String otp) {
        boolean isValid = otpService.verifyOtp(phone, otp);

        if (!isValid) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        User user = userRepo.findByPhone(phone)
                .orElseGet(()-> registerNewUserWithDefaultRole(phone));



        String token = jwtService.generateToken(user);
        UserDto userDto = modelMapper.map(user, UserDto.class);



        return new AuthResponse(token, userDto);
    }

    private User registerNewUserWithDefaultRole(String phone) {
        User newUser = new User();
            newUser.setPhone(phone);

        Role clientRole = roleService.getOrCreateRole("CLIENT");
        newUser.setRoles(Set.of(clientRole));
        return userRepo.save(newUser);
    }


}
