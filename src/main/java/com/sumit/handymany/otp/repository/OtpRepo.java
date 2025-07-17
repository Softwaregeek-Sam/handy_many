package com.sumit.handymany.otp.repository;

import com.sumit.handymany.otp.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface OtpRepo extends JpaRepository<Otp, Long> {

    Optional<Otp> findByPhone(String phone);
}
