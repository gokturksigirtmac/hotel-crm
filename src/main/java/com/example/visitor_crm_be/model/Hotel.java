package com.example.visitor_crm_be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Company> companies = new ArrayList<>();
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Driver> drivers = new ArrayList<>();
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Vehicle> vehicles = new ArrayList<>();
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Location> locations = new ArrayList<>();
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visitor> visitors = new ArrayList<>();
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    private boolean trial;
    private boolean suspended;
    private LocalDateTime trialExpiresAt;
    private String hotelName;                 // Hotel name
    private String staffName;           // First name of staff
    private String staffSurname;        // Last name of staff
    private String staffPhoneNumber;    // Contact number
    private String addressLine;         // Address
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}


