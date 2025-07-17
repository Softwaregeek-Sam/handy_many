package com.sumit.handymany.job.repository;

import com.sumit.handymany.job.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
