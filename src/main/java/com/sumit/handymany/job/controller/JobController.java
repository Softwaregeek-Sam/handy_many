package com.sumit.handymany.job.controller;

import com.sumit.handymany.auth.response.ApiResponse;
import com.sumit.handymany.auth.service.CustomUserDetails;
import com.sumit.handymany.job.manager.JobManager;
import com.sumit.handymany.job.model.dtos.ClientJobResponseDTO;
import com.sumit.handymany.job.model.dtos.JobRequestDTO;
import io.grpc.EquivalentAddressGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobManager jobManager;

    @PostMapping("/book-now")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ApiResponse> bookNow(@RequestBody JobRequestDTO request,
                                               @AuthenticationPrincipal Long userId){

        try{
            request.setClientId(userId);
            UUID tempRequestId = jobManager.bookJob(request);
            return ResponseEntity.ok(new ApiResponse("Matching started, Awaiting worker acceptance", tempRequestId));
        }catch (Exception e){
             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                     .body(new ApiResponse("Failed to Start matching", e.getMessage()));
        }

    }

    @PostMapping("/confirm-job")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ApiResponse> confirmJob(@RequestParam UUID tempRequestID,
                                                  @AuthenticationPrincipal Long userId){
           try{
                ClientJobResponseDTO jobResponseDTO = jobManager.crateJob(tempRequestID, userId);
               return ResponseEntity.ok(new ApiResponse("Job created successfully", jobResponseDTO));
           }catch (AccessDeniedException ex){
               return ResponseEntity.status(HttpStatus.FORBIDDEN)
                       .body(new ApiResponse("Access Denied",ex.getMessage()));
           }
           catch (IllegalStateException e) {
               return ResponseEntity.status(HttpStatus.CONFLICT)
                       .body(new ApiResponse("Job confirmation failed", e.getMessage()));
           } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body(new ApiResponse("Unexpected error while confirming job", e.getMessage()));
           }
    }
}

