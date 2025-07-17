package com.sumit.handymany.user.repository;

import com.sumit.handymany.user.enums.AvailabilityStatus;
import com.sumit.handymany.user.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {



    List<Worker> findAllByAvailabilityStatus(AvailabilityStatus availabilityStatus);
}
