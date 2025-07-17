package com.sumit.handymany.job.model.dtos;

import lombok.Data;

@Data
public class JobRequestDTO {
    private String profession;

    private String locationText;
    private Double latitude;
    private Double longitude;

    private Long clientId;
}
