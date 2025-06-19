package com.example.visitor_crm_be.config;

import com.example.visitor_crm_be.model.*;
import com.example.visitor_crm_be.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class AppStartupConfig implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private final DriverRepository driverRepository;
    private final CompanyRepository companyRepository;
    private final LocationRepository locationRepository;
    private final VehicleRepository vehicleRepository;
    private final VisitorRepository visitorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AppStartupConfig(
            HotelRepository hotelRepository,
            DriverRepository driverRepository,
            CompanyRepository companyRepository,
            LocationRepository locationRepository,
            VehicleRepository vehicleRepository,
            VisitorRepository visitorRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.hotelRepository = hotelRepository;
        this.driverRepository = driverRepository;
        this.companyRepository = companyRepository;
        this.locationRepository = locationRepository;
        this.vehicleRepository = vehicleRepository;
        this.visitorRepository = visitorRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Seed default Hotel
        Hotel defaultAdminHotel = hotelRepository.findByHotelName("Default Hotel")
                .orElseGet(() -> {
                    Hotel hotel = new Hotel();
                    hotel.setHotelName("Admin Hotel");
                    hotel.setAddressLine("123 Main St");
                    hotel.setStaffName("Admin Hotel's Staff");
                    hotel.setStaffSurname("Admin Hotel's Staff Surname");
                    hotel.setStaffPhoneNumber("1234567890");
                    hotel.setCreatedAt(OffsetDateTime.now());
                    hotel.setUpdatedAt(OffsetDateTime.now());
                    return hotelRepository.save(hotel);
                });

        // Seed default Driver
        Driver defaultAdminDriver = driverRepository.findByFirstName("Admin Driver")
                .orElseGet(() -> {
                    Driver driver = new Driver();
                    driver.setFirstName("Admin Driver");
                    driver.setLastName("Admin Driver's LastName");
                    driver.setPhoneNumber("1234567890");
                    driver.setHotel(defaultAdminHotel);
                    driver.setCreatedAt(OffsetDateTime.now());
                    driver.setUpdatedAt(OffsetDateTime.now());
                    return driverRepository.save(driver);
                });

        // Seed default Admin Company
        Company defaultAdminCompany = companyRepository.findByName("Default Company")
                .orElseGet(() -> {
                    Company company = new Company();
                    company.setName("Default Company");
                    company.setHotel(defaultAdminHotel); // ← make sure this is set correctly
                    company.setCreatedAt(OffsetDateTime.now());
                    company.setUpdatedAt(OffsetDateTime.now());
                    return companyRepository.save(company);
                });


        // Seed default Location
        Location defaultAdminLocation = locationRepository.findByLocation("Default Location")
                .orElseGet(() -> {
                    Location location = new Location();
                    location.setLocation("Default Location");
                    location.setDescription("City Center");
                    location.setHotel(defaultAdminHotel); // required due to nullable = false
                    location.setCreatedAt(OffsetDateTime.now());
                    location.setUpdatedAt(OffsetDateTime.now());
                    return locationRepository.save(location);
                });


        // Seed default Vehicle
        Vehicle defaultAdminVehicle = vehicleRepository.findByLicensePlateNumber("ADMIN-000")
                .orElseGet(() -> {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setHotel(defaultAdminHotel); // required due to nullable = false
                    vehicle.setBrand("DefaultBrand");    // Optional: provide a sensible default
                    vehicle.setModel("DefaultModel");    // Optional: same here
                    vehicle.setType("Sedan");
                    vehicle.setLicensePlateNumber("ADMIN-000");
                    vehicle.setCreatedAt(OffsetDateTime.now());
                    vehicle.setUpdatedAt(OffsetDateTime.now());
                    return vehicleRepository.save(vehicle);
                });


        // Seed default Visitor
        Visitor defaultVisitor = visitorRepository.findByFullName("Admin Visitor")
                .orElseGet(() -> {
                    Visitor visitor = new Visitor();
                    visitor.setFullName("Admin Visitor");
                    visitor.setPhoneNumber("555-000-0000");
                    visitor.setNumberOfPersons(1);
                    visitor.setSpecialNote("Initial seeded visitor");
                    visitor.setTripType("From Airport");

                    visitor.setHotel(defaultAdminHotel);
                    visitor.setCompany(defaultAdminCompany);
                    visitor.setDriver(defaultAdminDriver);
                    visitor.setVehicle(defaultAdminVehicle);
                    visitor.setCreatedAt(OffsetDateTime.now());
                    visitor.setUpdatedAt(OffsetDateTime.now());

                    return visitorRepository.save(visitor);
                });



        // Seed Admin User
        if (!userRepository.existsByUsername("admin@example.com")) {
            User admin = new User();
            admin.setName("System");
            admin.setSurname("Administrator");
            admin.setUsername("admin@example.com");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // hashed
            admin.setRole("ROLE_ADMIN");
            admin.setHotel(defaultAdminHotel); // or defaultAdminHotel

            userRepository.save(admin);
        }

    }
}

