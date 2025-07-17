package com.sumit.handymany.job.repository;

import com.sumit.handymany.job.model.JobMatchRequest;
import com.sumit.handymany.job.model.enums.JobMatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface
JobMatchRequestRepository extends JpaRepository<JobMatchRequest, Long> {


    Optional<JobMatchRequest> findByTempRequestId(UUID tempRequestId);
}
