package com.example.visitor_crm_be.service;

import com.example.visitor_crm_be.model.*;
import com.example.visitor_crm_be.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    @Autowired private HotelRepository hotelRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private DriverRepository driverRepository;
    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private LocationRepository locationRepository;

    // Create or update hotel
    public Hotel saveHotel(Hotel hotel) {
        hotel.setCreatedAt(OffsetDateTime.now());
        hotel.setUpdatedAt(OffsetDateTime.now());
        return hotelRepository.save(hotel);
    }

    // Get all hotels
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    // Get hotel by ID
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    // Delete hotel
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    // Related Entity Methods
    public List<Company> getCompaniesByHotelId(Long hotelId) {
        return companyRepository.findByHotelId(hotelId);
    }

    public List<Driver> getDriversByHotelId(Long hotelId) {
        return driverRepository.findByHotelId(hotelId);
    }

    public List<Vehicle> getVehiclesByHotelId(Long hotelId) {
        return vehicleRepository.findByHotelId(hotelId);
    }

    public List<Location> getLocationsByHotelId(Long hotelId) {
        return locationRepository.findByHotelId(hotelId);
    }

    // Add company to hotel
    public Company addCompanyToHotel(Long hotelId, Company company) {
        Hotel hotel = getHotelById(hotelId);
        company.setHotel(hotel);
        return companyRepository.save(company);
    }

    // Add driver to hotel
    public Driver addDriverToHotel(Long hotelId, Driver driver) {
        Hotel hotel = getHotelById(hotelId);
        driver.setHotel(hotel);
        return driverRepository.save(driver);
    }

    // Add vehicle to hotel
    public Vehicle addVehicleToHotel(Long hotelId, Vehicle vehicle) {
        Hotel hotel = getHotelById(hotelId);
        vehicle.setHotel(hotel);
        return vehicleRepository.save(vehicle);
    }

    // Add location to hotel
    public Location addLocationToHotel(Long hotelId, Location location) {
        Hotel hotel = getHotelById(hotelId);
        location.setHotel(hotel);
        return locationRepository.save(location);
    }

    // Optional: update timestamps
    public Hotel updateHotel(Long id, Hotel updatedHotel) {
        Hotel hotel = getHotelById(id);
        hotel.setHotelName(updatedHotel.getHotelName());
        hotel.setStaffName(updatedHotel.getStaffName());
        hotel.setStaffSurname(updatedHotel.getStaffSurname());
        hotel.setStaffPhoneNumber(updatedHotel.getStaffPhoneNumber());
        hotel.setAddressLine(updatedHotel.getAddressLine());
        hotel.setUpdatedAt(OffsetDateTime.now());
        return hotelRepository.save(hotel);
    }
}
