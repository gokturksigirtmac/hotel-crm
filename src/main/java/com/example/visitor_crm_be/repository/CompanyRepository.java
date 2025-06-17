package com.example.visitor_crm_be.repository;

import com.example.visitor_crm_be.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByHotelId(Long hotelId);

    Optional<Company> findByName(String defaultCompany);
}
