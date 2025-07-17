package com.sumit.handymany.job.service;

import com.sumit.handymany.job.model.JobMatchRequest;
import com.sumit.handymany.job.model.dtos.JobRequestDTO;
import com.sumit.handymany.job.model.enums.JobMatchStatus;
import com.sumit.handymany.job.repository.JobMatchRequestRepository;
import com.sumit.handymany.user.enums.AvailabilityStatus;
import com.sumit.handymany.user.model.Worker;
import com.sumit.handymany.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobMatching {

    private final WorkerRepository workerRepository;
    private final JobMatchRequestRepository matchRequestRepository;
    private final NotificationService notificationService;

    private static final double[] SEARCH_RADIUS_STEPS = {1.0, 2.5, 5.0};

    public UUID matchAndNotify(JobRequestDTO request) {
        UUID tempId = UUID.randomUUID();

        // Save initial match request
        JobMatchRequest matchRequest = JobMatchRequest.builder()
                .tempRequestId(tempId)
                .clientId(request.getClientId())
                .profession(request.getProfession())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .status(JobMatchStatus.PENDING)
                .build();
        matchRequestRepository.save(matchRequest);

        // 🔁 Begin radius-based search
        for (double radius : SEARCH_RADIUS_STEPS) {
            System.out.println("📍 Searching within radius: " + radius + " km");

            if (isJobAlreadyAccepted(tempId)) {
                System.out.println("✅ Job already accepted. Stopping search.");
                break;
            }

            List<Worker> availableWorkers = workerRepository.findAllByAvailabilityStatus(AvailabilityStatus.AVAILABLE);
            List<Worker> professionMatched = filterByProfession(availableWorkers, request.getProfession());
            List<Worker> nearbyWorkers = filterByProximity(professionMatched, request.getLongitude(), request.getLatitude(), radius);

            for (Worker worker : nearbyWorkers) {
                if (isJobAlreadyAccepted(tempId)) {
                    System.out.println("🛑 Job was accepted just now. Stop notifying.");
                    break;
                }

                System.out.println("📨 Notifying worker: " + worker.getUser().getName());
                notificationService.sendJobNotification(worker, request, tempId);
            }
        }

        return tempId;
    }

    // ✨ Helper Method 1 — Clean abstraction for checking match status
    private boolean isJobAlreadyAccepted(UUID tempRequestId) {
        return matchRequestRepository.findByTempRequestId(tempRequestId)
                .map(match -> match.getStatus() == JobMatchStatus.ACCEPTED)
                .orElse(false); // return false if no match found
    }

    // ✨ Helper Method 2 — Filter workers by profession
    private List<Worker> filterByProfession(List<Worker> workers, String profession) {
        return workers.stream()
                .filter(worker -> profession.equalsIgnoreCase(worker.getProfession()))
                .toList();
    }

    // ✨ Helper Method 3 — Filter workers by location
    private List<Worker> filterByProximity(List<Worker> workers, Double longitude, Double latitude, double radius) {
        return workers.stream()
                .filter(worker -> distanceInKm(latitude, longitude, worker.getCurrentLatitude(), worker.getCurrentLongitude()) <= radius)
                .toList();
    }

    // ✨ Helper Method 4 — Haversine formula to calculate distance in KM
    private double distanceInKm(Double latitude, Double longitude, Double currLat, Double currLong) {
        final int EARTH_RADIUS_KM = 6371;

        double dLat = Math.toRadians(currLat - latitude);
        double dLon = Math.toRadians(currLong - longitude);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(currLat)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}

