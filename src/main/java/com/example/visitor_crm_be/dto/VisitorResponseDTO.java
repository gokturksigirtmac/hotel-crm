package com.example.visitor_crm_be.dto;

import com.example.visitor_crm_be.model.Company;
import com.example.visitor_crm_be.model.Driver;
import com.example.visitor_crm_be.model.Hotel;
import com.example.visitor_crm_be.model.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class VisitorResponseDTO {
    private Long id;
    private Hotel hotel;
    private Company company;
    private Driver driver;
    private Vehicle vehicle;
    private String fullName;
    private String phoneNumber;
    private int numberOfPersons;
    private String specialNote;
    private String tripType;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
