package com.sumit.handymany.auth.request;

import lombok.Data;

@Data
public class OtpVerifyRequest {
    private String phone;
    private String otp;
}
