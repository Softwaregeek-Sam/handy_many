package com.sumit.handymany.job.service;

import com.sumit.handymany.job.manager.JobManager;
import com.sumit.handymany.job.model.JobMatchRequest;
import com.sumit.handymany.job.model.dtos.ClientJobResponseDTO;
import com.sumit.handymany.job.model.enums.JobMatchStatus;
import com.sumit.handymany.job.repository.JobMatchRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkerJobClaimService {
    private  final JobManager jobManager;
    private final JobMatchRequestRepository matchRequestRepository;
    @Transactional
    public synchronized boolean acceptJob(UUID tempRequestId, Long workerId){
         JobMatchRequest matchRequest =  matchRequestRepository.findByTempRequestId(tempRequestId)
                 .orElseThrow(() -> new RuntimeException("Invalid request ID"));

         if(matchRequest.getStatus() != JobMatchStatus.PENDING){
             return false;
         }
        if (matchRequest.getClientId().equals(workerId)) {
            throw new AccessDeniedException("You cannot accept your own job");
        }

         matchRequest.setStatus(JobMatchStatus.ACCEPTED);
         matchRequest.setAcceptedWorkerId(workerId);
         matchRequestRepository.save(matchRequest);
       return true;

    }
}
