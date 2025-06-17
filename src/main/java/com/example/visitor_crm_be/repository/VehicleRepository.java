package com.example.visitor_crm_be.repository;

import com.example.visitor_crm_be.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByHotelId(Long hotelId);

    Optional<Vehicle> findByLicensePlateNumber(String licensePlateNumber);
}
