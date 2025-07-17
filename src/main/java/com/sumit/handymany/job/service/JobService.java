package com.sumit.handymany.job.service;

import com.sumit.handymany.job.model.Job;
import com.sumit.handymany.job.model.JobMatchRequest;
import com.sumit.handymany.job.model.dtos.ClientJobResponseDTO;
import com.sumit.handymany.job.model.dtos.JobRequestDTO;
import com.sumit.handymany.job.model.dtos.WorkerJobResponseDTO;
import com.sumit.handymany.job.model.enums.JobMatchStatus;
import com.sumit.handymany.job.model.enums.JobStatus;
import com.sumit.handymany.job.repository.JobMatchRequestRepository;
import com.sumit.handymany.job.repository.JobRepository;
import com.sumit.handymany.user.model.User;
import com.sumit.handymany.user.model.Worker;
import com.sumit.handymany.user.repository.UserRepo;
import com.sumit.handymany.user.repository.WorkerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobMatching jobMatching;
    private final JobMatchRequestRepository matchRequestRepository;
    private final UserRepo userRepo;
    private final WorkerRepository workerRepository;
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    public UUID matchWorker(JobRequestDTO request) {
        return jobMatching.matchAndNotify(request);
    }

    @Transactional
    public ClientJobResponseDTO createJobFromMatchRequest(UUID tempRequestId) {
        JobMatchRequest matchRequest = matchRequestRepository.findByTempRequestId(tempRequestId)
                .orElseThrow(() -> new RuntimeException("Invalid temp ID"));

        if (matchRequest.getStatus() != JobMatchStatus.ACCEPTED) {
            throw new IllegalArgumentException("Job was not accepted by any worker");
        }


        User client = userRepo.findById(matchRequest.getClientId())
                .orElseThrow(() -> new RuntimeException("client not found"));

        Worker worker = workerRepository.findById(matchRequest.getAcceptedWorkerId())
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        Job job = Job.builder()
                .profession(matchRequest.getProfession())
                .client(client)
                .assignedWorker(worker)
                .locationText("Auto-Generated")
                .latitude(matchRequest.getLatitude())
                .longitude(matchRequest.getLongitude())
                .status(JobStatus.HIRED)
                .confirmedAt(LocalDateTime.now())
                .build();

        jobRepository.save(job);

        ClientJobResponseDTO responseDTO =  modelMapper.map(job, ClientJobResponseDTO.class);
        responseDTO.setWorkerId(worker.getId());
        responseDTO.setWorkerName(worker.getUser().getName());
        responseDTO.setPhone(worker.getUser().getPhone());
        responseDTO.setExperienceYears(worker.getExperienceYears());

        return responseDTO;







    }

    public WorkerJobResponseDTO getJobForWorker(Long jobId, Long workerId)  {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if(!job.getAssignedWorker().getId().equals(workerId)){
            throw new RuntimeException("YOu are not assigned to this Job");
        }

        return modelMapper.map(job, WorkerJobResponseDTO.class);

    }





    }



