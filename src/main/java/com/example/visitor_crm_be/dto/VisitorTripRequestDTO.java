package com.example.visitor_crm_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VisitorTripRequestDTO {
    private String fullName;
    private String phoneNumber;
    private int numberOfPerson;
    private Long hotelId;
    private String specialNote;
    private String tripType; // From Airport, From Hotel, Round Trip
    private List<TripRequestDTO> trips;
}

