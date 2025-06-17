package com.example.visitor_crm_be.repository;

import com.example.visitor_crm_be.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByHotelId(Long hotelId);

    Optional<Driver> findByFirstName(String firstName);
}
