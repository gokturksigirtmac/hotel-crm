package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.*;
import com.example.visitor_crm_be.model.Trip;
import com.example.visitor_crm_be.model.Visitor;
import com.example.visitor_crm_be.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        visitor.setNumberOfPersons(request.getNumberOfPerson());
        visitor.setSpecialNote(request.getSpecialNote());
        visitor.setTripType(request.getTripType());

        visitor.setHotel(hotelRepository.findById(request.getHotelId()).orElseThrow());

        List<Trip> trips = new ArrayList<>();
        for (TripRequestDTO t : request.getTrips()) {
            Trip trip = new Trip();
            trip.setDirection(t.getDirection());
            trip.setFlightNumber(t.getFlightNumber());
            trip.setDatetime(OffsetDateTime.parse(t.getDatetime()));
            trip.setFromLocation(locationRepository.findById(t.getFromLocationId()).orElseThrow());
            trip.setToLocation(locationRepository.findById(t.getToLocationId()).orElseThrow());
            trip.setCompany(companyRepository.findById(t.getCompanyId()).orElseThrow());
            trip.setVehicle(vehicleRepository.findById(t.getVehicleId()).orElseThrow());
            trip.setDriver(driverRepository.findById(t.getDriverId()).orElseThrow());
            trip.setVisitor(visitor);
            trips.add(trip);
        }
        visitor.setTrips(trips);
        visitorRepository.save(visitor); // cascades trips

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<VisitorResponseDTO>> getAllVisitors() {
        List<Visitor> visitors = visitorRepository.findAll();

        List<VisitorResponseDTO> response = visitors.stream().map(visitor -> {
            VisitorResponseDTO dto = new VisitorResponseDTO();
            dto.setId(visitor.getId());
            dto.setFullName(visitor.getFullName());
            dto.setPhoneNumber(visitor.getPhoneNumber());
            dto.setSpecialNote(visitor.getSpecialNote());
            dto.setTripType(visitor.getTripType());

            List<VisitorResponseDTO.TripDTO> tripDTOs = visitor.getTrips().stream().map(trip -> {
                VisitorResponseDTO.TripDTO tripDTO = new VisitorResponseDTO.TripDTO();
                tripDTO.setFlightNumber(trip.getFlightNumber());
                tripDTO.setDirection(trip.getDirection());
                tripDTO.setDatetime(trip.getDatetime().toString());

                // Set fromLocation
                if (trip.getFromLocation() != null) {
                    VisitorResponseDTO.LocationDTO fromLocation = new VisitorResponseDTO.LocationDTO();
                    fromLocation.setId(trip.getFromLocation().getId());
                    fromLocation.setLocation(trip.getFromLocation().getLocation());
                    fromLocation.setDescription(trip.getFromLocation().getDescription());
                    tripDTO.setFromLocation(fromLocation);
                }

                // Set toLocation
                if (trip.getToLocation() != null) {
                    VisitorResponseDTO.LocationDTO toLocation = new VisitorResponseDTO.LocationDTO();
                    toLocation.setId(trip.getToLocation().getId());
                    toLocation.setLocation(trip.getToLocation().getLocation());
                    toLocation.setDescription(trip.getToLocation().getDescription());
                    tripDTO.setToLocation(toLocation);
                }

                // Set company
                if (trip.getCompany() != null) {
                    VisitorResponseDTO.CompanyDTO companyDTO = new VisitorResponseDTO.CompanyDTO();
                    companyDTO.setId(Long.valueOf(trip.getCompany().getId()));
                    companyDTO.setName(trip.getCompany().getName());
                    tripDTO.setCompany(companyDTO);
                }

                // Set vehicle
                if (trip.getVehicle() != null) {
                    VisitorResponseDTO.VehicleDTO vehicleDTO = new VisitorResponseDTO.VehicleDTO();
                    vehicleDTO.setId(trip.getVehicle().getId());
                    vehicleDTO.setBrand(trip.getVehicle().getBrand());
                    vehicleDTO.setModel(trip.getVehicle().getModel());
                    vehicleDTO.setType(trip.getVehicle().getType());
                    vehicleDTO.setPlateNumber(trip.getVehicle().getPlateNumber());
                    tripDTO.setVehicle(vehicleDTO);
                }

                // Set driver
                if (trip.getDriver() != null) {
                    VisitorResponseDTO.DriverDTO driverDTO = new VisitorResponseDTO.DriverDTO();
                    driverDTO.setId(trip.getDriver().getId());
                    driverDTO.setFullName(trip.getDriver().getFirstName() + " " + trip.getDriver().getLastName());
                    driverDTO.setLicenseNumber(trip.getDriver().getLicenseNumber());
                    tripDTO.setDriver(driverDTO);
                }

                return tripDTO;
            }).collect(Collectors.toList());

            dto.setTrips(tripDTOs);

            // Optional global fields (first trip fallback)
            if (!visitor.getTrips().isEmpty()) {
                var firstTrip = visitor.getTrips().get(0);

                if (firstTrip.getCompany() != null) {
                    VisitorResponseDTO.CompanyDTO companyDTO = new VisitorResponseDTO.CompanyDTO();
                    companyDTO.setId(Long.valueOf(firstTrip.getCompany().getId()));
                    companyDTO.setName(firstTrip.getCompany().getName());
                    dto.setCompany(companyDTO);
                }

                if (firstTrip.getDriver() != null) {
                    VisitorResponseDTO.DriverDTO driverDTO = new VisitorResponseDTO.DriverDTO();
                    driverDTO.setId(firstTrip.getDriver().getId());
                    driverDTO.setFullName(firstTrip.getDriver().getFirstName() + " " + firstTrip.getDriver().getLastName());
                    driverDTO.setLicenseNumber(firstTrip.getDriver().getLicenseNumber());
                    dto.setDriver(driverDTO);
                }

                if (firstTrip.getFromLocation() != null) {
                    VisitorResponseDTO.LocationDTO locationDTO = new VisitorResponseDTO.LocationDTO();
                    locationDTO.setId(firstTrip.getFromLocation().getId());
                    locationDTO.setLocation(firstTrip.getFromLocation().getLocation());
                    locationDTO.setDescription(firstTrip.getFromLocation().getDescription());
                    dto.setLocation(locationDTO);
                }
            }

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


}