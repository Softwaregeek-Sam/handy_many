package com.sumit.handymany.auth.service;

import com.sumit.handymany.auth.response.AuthResponse;
import com.sumit.handymany.otp.service.OtpService;
import com.sumit.handymany.user.dtos.UserDto;
import com.sumit.handymany.user.model.User;
import com.sumit.handymany.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final OtpService otpService;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;


    public void sendOtp(String phone) {
        otpService.createAndSendOtp(phone);

    }

    public AuthResponse verifyOtp(String phone, String otp) {
        boolean isValid = otpService.verifyOtp(phone, otp);

        if (!isValid) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        User user = userRepo.findByPhone(phone)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setPhone(phone);
                    return userRepo.save(newUser);
                });


        String token = jwtService.generateToken(user.getPhone());
        UserDto userDto = modelMapper.map(user, UserDto.class);


        return new AuthResponse(token, userDto);
    }


}
