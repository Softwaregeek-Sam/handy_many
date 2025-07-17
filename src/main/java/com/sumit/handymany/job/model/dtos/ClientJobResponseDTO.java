package com.sumit.handymany.job.model.dtos;

import com.sumit.handymany.job.model.enums.JobStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientJobResponseDTO {
    private Long id;
    private String profession;
    private String locationText;
    private Double latitude;
    private Double longitude;
    private LocalDateTime confirmedAt;
    private JobStatus status;

    //Worker info
    private Long workerId;
    private String workerName;
    private String phone;
    private int experienceYears;




}
