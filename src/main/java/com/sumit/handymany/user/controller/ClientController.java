package com.sumit.handymany.user.controller;

import com.sumit.handymany.auth.response.ApiResponse;
import com.sumit.handymany.auth.service.CustomUserDetails;
import com.sumit.handymany.user.dtos.CompleteClientProfileRequest;
import com.sumit.handymany.user.dtos.UserDto;
import com.sumit.handymany.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {
    private final UserService userService;

    @PutMapping("/complete-client-profile")
    public ResponseEntity<ApiResponse> completeProfile(@RequestBody CompleteClientProfileRequest request,
                                        @AuthenticationPrincipal  Long userId) {
        try {

            UserDto userDto = userService.completeClientProfile(userId, request);
            return ResponseEntity.ok(new ApiResponse("Profile Update successfully", userDto));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Failed to complete profile: " + e.getMessage(), null));
        }
    }

}
