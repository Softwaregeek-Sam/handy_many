package com.sumit.handymany.otp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OTPResponse {


    @JsonProperty("Status")
    private String status;

    @JsonProperty("Details")
    private String details;

}
