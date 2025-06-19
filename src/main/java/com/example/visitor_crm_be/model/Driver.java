package com.example.visitor_crm_be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @OneToMany(mappedBy = "driver")
    private List<Trip> trips;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String licenseNumber;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
