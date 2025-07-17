package com.sumit.handymany.job.service;

import com.sumit.handymany.job.model.dtos.JobRequestDTO;
import com.sumit.handymany.user.model.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class NotificationService {
    public  void sendJobNotification(Worker worker, JobRequestDTO request, UUID tempRequestId){
        String token = worker.getUser().getFcmToken();

        if(token == null || token.isBlank()){
            log.warn("🚫 No FCM token for worker: {}", worker.getUser().getName());
            return;

        }

        String title = "📢 New Job Opportunity!";
        String body = String.format(
                "Work needed: %s\nLocation: %s\nTap to accept job.",
                request.getProfession(),
                request.getLocationText()
        );

        log.info("📤 [FCM STUB] Sending push notification to token: {}", token);
        log.info("🔔 Title: {}", title);
        log.info("📝 Body: {}", body);
        log.info("📦 Payload: tempRequestId = {}", tempRequestId);
    }

}
