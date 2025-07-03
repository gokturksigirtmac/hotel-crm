package com.example.visitor_crm_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverResponseDTO {
    private Long id;
    private Long hotelId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String licenseNumber;
    private String httpMessage;
}
