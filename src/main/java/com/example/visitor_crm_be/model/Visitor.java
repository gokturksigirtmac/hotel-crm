package com.example.visitor_crm_be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "visitor")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> trips = new ArrayList<>();

    private String fullName;
    private String phoneNumber;
    private int numberOfPersons;
    private String specialNote;
    private String tripType;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
