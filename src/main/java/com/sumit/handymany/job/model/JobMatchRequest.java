package com.sumit.handymany.job.model;

import com.sumit.handymany.job.model.enums.JobMatchStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "job_match_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobMatchRequest {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @Column(unique = true, nullable = false)
    private UUID tempRequestId;

     @Enumerated(EnumType.STRING)
    private JobMatchStatus status;

    private Long clientId;
    private String profession;
    private Double latitude;
    private Double longitude;
    private Long acceptedWorkerId;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
