package com.example.visitor_crm_be.repository;

import com.example.visitor_crm_be.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByVisitorId(Long visitorId);
    List<Trip> findByDirection(String direction);
    List<Trip> findByFlightNumber(String flightNumber);
    List<Trip> findByLocationId(Long locationId);
    List<Trip> findByDatetimeBetween(OffsetDateTime start, OffsetDateTime end);
    List<Trip> findByVisitorHotelId(Long hotelId);
}
