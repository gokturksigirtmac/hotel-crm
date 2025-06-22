package com.example.visitor_crm_be.repository;

import com.example.visitor_crm_be.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByHotelName(String hotelName);
    List<Hotel> findAllByTrialTrueAndSuspendedFalseAndTrialExpiresAtBefore(LocalDateTime trial_expires_at);
}
