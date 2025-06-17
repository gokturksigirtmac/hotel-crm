package com.example.visitor_crm_be.repository;

import com.example.visitor_crm_be.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByHotelId(Long hotelId);

    Optional<Location> findByLocation(String location);
}
