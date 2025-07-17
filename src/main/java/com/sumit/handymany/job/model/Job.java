package com.sumit.handymany.job.model;

import com.sumit.handymany.job.model.enums.JobStatus;
import com.sumit.handymany.user.model.User;
import com.sumit.handymany.user.model.Worker;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private Long id;
    private String profession;

    private String locationText;
    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private JobStatus status = JobStatus.HIRED;

    @ManyToOne(optional = false)
    @JoinColumn(name="client_id")
    private User client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "worker_id")
    private Worker assignedWorker;

    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

}
