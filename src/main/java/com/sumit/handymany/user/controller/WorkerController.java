package com.sumit.handymany.user.controller;

import com.sumit.handymany.auth.response.ApiResponse;
import com.sumit.handymany.auth.service.CustomUserDetails;
import com.sumit.handymany.job.manager.JobManager;
import com.sumit.handymany.job.model.dtos.WorkerJobResponseDTO;
import com.sumit.handymany.job.service.WorkerJobClaimService;
import com.sumit.handymany.user.dtos.CompleteWorkerProfile;
import com.sumit.handymany.user.dtos.WorkerProfileResponse;
import com.sumit.handymany.user.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/worker")
@RequiredArgsConstructor
public class WorkerController {
    private final WorkerService workerService;
    private final WorkerJobClaimService workerJobClaimService;
    private final JobManager jobManager;

    @PostMapping("/complete-worker-profile")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ApiResponse> createWorkerProfile(@AuthenticationPrincipal Long userId,
                                                           @RequestBody CompleteWorkerProfile request){
        try {
            WorkerProfileResponse response = workerService.createWorkerProfile( userId,request);
            return ResponseEntity.ok(new ApiResponse("You can find jobs", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("failed creating profile", e.getMessage()));

        }

    }
    @PostMapping("/claim-job")
    @PreAuthorize("hasRole('WORKER')")
    public ResponseEntity<ApiResponse> acceptJob(@RequestParam UUID tempRequestId
    ,@AuthenticationPrincipal Long workerId){

        try{
            boolean accepted = workerJobClaimService.acceptJob(tempRequestId, workerId);

            if(!accepted){
                 throw new IllegalArgumentException("Job has already been accepted by someone else" );
            }
            return ResponseEntity.ok(new ApiResponse("Job accepted successfully", null));
        }catch (Exception ex){
             return ResponseEntity.status(HttpStatus.CONFLICT)
                     .body(new ApiResponse("Job acceptance failed", ex.getMessage()));
        }
    }

    @GetMapping("/jobs/{jobId}")
    @PreAuthorize("hasRole('WORKER')")
    public ResponseEntity<ApiResponse> getMyJob(@PathVariable Long jobId,
                                                @AuthenticationPrincipal Long workerId)  {
        try {
            WorkerJobResponseDTO response = jobManager.getJobDetailsForWorker(jobId, workerId);

            return ResponseEntity.ok(new ApiResponse("Job fetched successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Could not fetch job details", e.getMessage()));
        }
    }


}
