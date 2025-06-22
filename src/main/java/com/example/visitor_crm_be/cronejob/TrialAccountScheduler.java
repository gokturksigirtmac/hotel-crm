package com.example.visitor_crm_be.cronejob;

import com.example.visitor_crm_be.model.Hotel;
import com.example.visitor_crm_be.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TrialAccountScheduler {

    @Autowired
    private HotelRepository hotelRepository;

    @Scheduled(cron = "0 0 0 * * *") // runs every day at 00:00
    public void suspendExpiredTrials() {
        List<Hotel> expired = hotelRepository.findAllByTrialTrueAndSuspendedFalseAndTrialExpiresAtBefore(LocalDateTime.now());

        for (Hotel hotel : expired) {
            hotel.setSuspended(true);
            hotelRepository.save(hotel);
            System.out.println("Suspended trial hotel: " + hotel.getHotelName());
        }
    }
}
