package com.example.visitor_crm_be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direction; // airport_to_hotel or hotel_to_airport
    private String flightNumber;
    private OffsetDateTime datetime;

    @ManyToOne
    @JoinColumn(name = "from_location_id")
    private Location fromLocation;

    @ManyToOne
    @JoinColumn(name = "to_location_id")
    private Location toLocation;

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    // Getters and setters
}