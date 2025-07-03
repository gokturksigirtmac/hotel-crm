package com.example.visitor_crm_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverCreateDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String licenseNumber;
}
