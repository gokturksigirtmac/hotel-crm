package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.TrialRequestDTO;
import com.example.visitor_crm_be.dto.TrialResponseDTO;
import com.example.visitor_crm_be.model.*;
import com.example.visitor_crm_be.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trial")
@RequiredArgsConstructor
public class TrialController {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final HotelRepository hotelRepository;
    @Autowired
    private final LocationRepository locationRepository;
    @Autowired
    private final DriverRepository driverRepository;
    @Autowired
    private final VehicleRepository vehicleRepository;
    @Autowired
    private final CompanyRepository companyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping()
    public ResponseEntity<TrialResponseDTO> createTrialHotel(@RequestBody TrialRequestDTO request) {
        TrialResponseDTO response = new TrialResponseDTO();
        if (userRepository.existsByEmail(request.getAdminEmail())) {
            response.setMessage("Email already exists");
            return ResponseEntity.badRequest().body(response);
        }

        // Step 1: Create Hotel
        Hotel hotel = new Hotel();
        hotel.setHotelName(request.getHotelName());
        hotel.setTrial(true);
        hotel.setSuspended(false);
        hotel.setTrialExpiresAt(LocalDateTime.now().plusDays(7));
        hotelRepository.save(hotel);

        // Step 2: Create User (Hotel Admin)
        User user = new User();
        user.setEmail(request.getAdminEmail());
        user.setUsername(request.getAdminEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_ADMIN");
        user.setHotel(hotel);
        userRepository.save(user);

        // Step 3: Seed Demo Data
        // 1. Locations
        Location airport = new Location();
        airport.setLocation("Istanbul Airport");
        airport.setDescription("International Terminal");
        airport.setHotel(hotel);

        Location cityCenter = new Location();
        cityCenter.setLocation("Taksim Square");
        cityCenter.setDescription("City Center");
        cityCenter.setHotel(hotel);

        Location sultanahmet = new Location();
        sultanahmet.setLocation("Sultanahmet");
        sultanahmet.setDescription("Historical Area");
        sultanahmet.setHotel(hotel);

        locationRepository.saveAll(List.of(airport, cityCenter, sultanahmet));

        // 2. Drivers
        Driver driver1 = new Driver();
        driver1.setFirstName("Ahmet");
        driver1.setLastName("Yılmaz");
        driver1.setLicenseNumber("DR123456");
        driver1.setHotel(hotel);

        Driver driver2 = new Driver();
        driver2.setFirstName("Mehmet");
        driver2.setLastName("Kaya");
        driver2.setLicenseNumber("DR654321");
        driver2.setHotel(hotel);

        driverRepository.saveAll(List.of(driver1, driver2));

        // 3. Vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setPlateNumber("34 TRIAL 01");
        vehicle1.setBrand("Mercedes Vito");
        vehicle1.setHotel(hotel);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setPlateNumber("34 DEMO 99");
        vehicle2.setBrand("Ford Transit");
        vehicle2.setHotel(hotel);

        vehicleRepository.saveAll(List.of(vehicle1, vehicle2));

        // 4. Companies
        Company company1 = new Company();
        company1.setName("Demo Tourism Agency");
        company1.setHotel(hotel);

        Company company2 = new Company();
        company2.setName("Fake Travel Co");
        company2.setHotel(hotel);

        companyRepository.saveAll(List.of(company1, company2));

        response.setMessage("Trial account created successfully! You will redirect to login page.");
        return ResponseEntity.created(URI.create("/api/trial/")).body(response);
    }


}
