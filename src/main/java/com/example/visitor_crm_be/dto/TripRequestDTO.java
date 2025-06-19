package com.example.visitor_crm_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripRequestDTO {
    private String direction; // "airport_to_hotel" or "hotel_to_airport"
    private String flightNumber;
    private String datetime; // ISO format (e.g., 2025-06-19T10:00:00+03:00)
    private Long fromLocationId; // Hotel or Airport ID
    private Long toLocationId; // Hotel or Airport ID
    private Long companyId;
    private Long vehicleId;
    private Long driverId;
}
