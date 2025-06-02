package com.example.visitor_crm_be.repository;

import com.example.visitor_crm_be.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
