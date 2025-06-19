package com.example.visitor_crm_be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @OneToMany(mappedBy = "vehicle")
    private List<Trip> trips;

    private String brand;
    private String model;
    private String type;
    private String plateNumber;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
