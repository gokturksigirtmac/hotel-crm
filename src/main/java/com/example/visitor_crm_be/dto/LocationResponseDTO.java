package com.example.visitor_crm_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponseDTO {
    private Long id;
    private Long hotelId;
    private String location;
    private String description;
}
