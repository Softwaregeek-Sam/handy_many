package com.sumit.handymany.otp.service;

import com.sumit.handymany.otp.dtos.OTPResponse;
import com.sumit.handymany.otp.model.Otp;
import com.sumit.handymany.otp.repository.OtpRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepo otpRepo;
    private final RestTemplate restTemplate;

    @Value("${otp.2factor.apiKey}")
    private String apiKey;

    public void createAndSendOtp(String phone) {
        String url = "https://2factor.in/API/V1/" + apiKey + "/SMS/" + phone + "/AUTOGEN";

        // Remove existing OTPs for this phone if present
        otpRepo.findByPhone(phone).ifPresent(otpRepo::delete);

        // Now we parse it as structured response
        OTPResponse response = restTemplate.getForObject(url, OTPResponse.class);

        if (response != null && "Success".equalsIgnoreCase(response.getStatus())) {
            Otp otp = new Otp();
            otp.setPhone(phone);
            otp.setSessionId(response.getDetails());
            otp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
            otpRepo.save(otp);
        } else {
            throw new RuntimeException("Failed to send OTP");
        }
    }

    // File: com.sumit.handymany.otp.service.OtpService.java
    public boolean verifyOtp(String phone, String otp) {
        Otp otpRecord = otpRepo.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("OTP session not found"));

        // Build URL for 2Factor verification
        String url = "https://2factor.in/API/V1/" + apiKey + "/SMS/VERIFY/" +
                otpRecord.getSessionId() + "/" + otp;

        OTPResponse response = restTemplate.getForObject(url, OTPResponse.class);
        System.out.println("OTP Verification Response: " + response);


        if (response != null && "Success".equalsIgnoreCase(response.getStatus())
                && "OTP Matched".equalsIgnoreCase(response.getDetails())) {
            otpRepo.delete(otpRecord); // Clear OTP after success
            return true;
        } else {
            return false;
        }
    }

}
