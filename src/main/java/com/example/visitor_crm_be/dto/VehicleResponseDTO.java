package com.example.visitor_crm_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleResponseDTO {
    private Long id;
    private Long hotelId;
    private String brand;
    private String model;
    private String type;
    private String plateNumber;

}
