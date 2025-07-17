package com.sumit.handymany.user.controller;

import com.sumit.handymany.auth.response.ApiResponse;
import com.sumit.handymany.auth.service.CustomUserDetails;
import com.sumit.handymany.job.manager.JobManager;
import com.sumit.handymany.job.model.dtos.WorkerJobResponseDTO;
import com.sumit.handymany.job.service.WorkerJobClaimService;
import com.sumit.handymany.user.dtos.CompleteWorkerProfile;
import com.sumit.handymany.user.dtos.WorkerDto;
import com.sumit.handymany.user.dtos.UserDto;
import com.sumit.handymany.user.dtos.WorkerProfileResponse;
import com.sumit.handymany.user.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse> createWorkerProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                           @RequestBody CompleteWorkerProfile request){
        try {
            WorkerProfileResponse response = workerService.createWorkerProfile(userDetails.getUser(),request);
            return ResponseEntity.ok(new ApiResponse("You can find jobs", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("failed creating profile", e.getMessage()));

        }

    }
    @PostMapping("/claim-job")
    public ResponseEntity<ApiResponse> acceptJob(@RequestParam UUID tempRequestId
    ,@AuthenticationPrincipal CustomUserDetails userDetails){

        try{
            Long workerId = userDetails.getUser().getId();
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
    public ResponseEntity<ApiResponse> getMyJob(@PathVariable Long jobId,
                                                @AuthenticationPrincipal CustomUserDetails userDetails)  {
        try {
            Long workerId = userDetails.getUser().getId();

            WorkerJobResponseDTO response = jobManager.getJobDetailsForWorker(jobId, workerId);

            return ResponseEntity.ok(new ApiResponse("Job fetched successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Could not fetch job details", e.getMessage()));
        }
    }


}
