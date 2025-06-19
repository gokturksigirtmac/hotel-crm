package com.example.visitor_crm_be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @OneToMany(mappedBy = "fromLocation")
    private List<Trip> fromTrips;

    @OneToMany(mappedBy = "toLocation")
    private List<Trip> toTrips;


    private String location;
    private String description;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
