package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.*;
import com.example.visitor_crm_be.model.Trip;
import com.example.visitor_crm_be.model.Visitor;
import com.example.visitor_crm_be.repository.*;
import com.example.visitor_crm_be.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @PostMapping
    public ResponseEntity<?> createVisitor(@RequestBody VisitorTripRequestDTO request) {

        Visitor visitor = new Visitor();
        visitor.setFullName(request.getFullName());
        visitor.setPhoneNumber(request.getPhoneNumber());
        visitor.setNumberOfPersons(request.getNumberOfPersons());
        visitor.setSpecialNote(request.getSpecialNote());
        visitor.setTripType(request.getTripType());

        visitor.setCompany(companyRepository.findById(request.getCompanyId()).orElseThrow());
        visitor.setVehicle(vehicleRepository.findById(request.getVehicleId()).orElseThrow());
        visitor.setDriver(driverRepository.findById(request.getDriverId()).orElseThrow());
        visitor.setHotel(hotelRepository.findById(request.getHotelId()).orElseThrow());

        List<Trip> trips = new ArrayList<>();
        for (TripRequestDTO t : request.getTrips()) {
            Trip trip = new Trip();
            trip.setDirection(t.getDirection());
            trip.setFlightNumber(t.getFlightNumber());
            trip.setDatetime(OffsetDateTime.parse(t.getDatetime()));
            trip.setLocation(locationRepository.findById(t.getLocationId()).orElseThrow());
            trip.setVisitor(visitor);
            trips.add(trip);
        }
        visitor.setTrips(trips);
        visitorRepository.save(visitor); // cascades trips

        return ResponseEntity.ok().build();
    }
}