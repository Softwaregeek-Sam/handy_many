package com.sumit.handymany.job.model.dtos;

import com.sumit.handymany.job.model.enums.JobStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkerJobResponseDTO {
    private Long id;
    private String profession;
    private String locationText;
    private Double latitude;
    private Double longitude;

    private JobStatus status;
    private LocalDateTime confirmedAt;


    private Long clientId;
    private String clientName;
    private String clientPhone;


}
