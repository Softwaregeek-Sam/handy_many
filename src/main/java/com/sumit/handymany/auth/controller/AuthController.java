package com.sumit.handymany.auth.controller;

import com.sumit.handymany.auth.request.OtpRequest;
import com.sumit.handymany.auth.request.OtpVerifyRequest;
import com.sumit.handymany.auth.response.ApiResponse;
import com.sumit.handymany.auth.response.AuthResponse;
import com.sumit.handymany.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("request-otp")
  public ResponseEntity<ApiResponse> requestOtp(@RequestBody OtpRequest request){
      try{
          authService.sendOtp(request.getPhone());
          return ResponseEntity.ok(new ApiResponse("OTP sent successfully", null));
      }catch (Exception e){
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body(new ApiResponse("Failed to send otp: " + e.getMessage(), null ));
      }
  }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestBody OtpVerifyRequest request) {
        try {
            AuthResponse authResponse = authService.verifyOtp(request.getPhone(), request.getOtp());
            return ResponseEntity.ok(new ApiResponse("OTP verified successfully", authResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("OTP verification failed: " + e.getMessage(), null));
        }
    }


}
