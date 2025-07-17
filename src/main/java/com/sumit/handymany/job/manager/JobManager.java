package com.sumit.handymany.job.manager;

import com.sumit.handymany.job.model.dtos.ClientJobResponseDTO;
import com.sumit.handymany.job.model.dtos.JobRequestDTO;
import com.sumit.handymany.job.model.dtos.WorkerJobResponseDTO;
import com.sumit.handymany.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JobManager {
    private final JobService jobService;
    public UUID bookJob(JobRequestDTO request){

        return jobService.matchWorker(request);
    }
    public ClientJobResponseDTO crateJob(UUID tempRequestId){
           return jobService.createJobFromMatchRequest(tempRequestId);
    }

    public WorkerJobResponseDTO getJobDetailsForWorker(Long jobId, Long workerId) {
          return jobService.getJobForWorker(jobId, workerId);
    }


//    public JobResponseDTO bookJob(JobRequestDTO request) {
//
//
//      Worker matchedWorker =  jobService.matchWorker(request);
//      return null;
//
//    }
}
