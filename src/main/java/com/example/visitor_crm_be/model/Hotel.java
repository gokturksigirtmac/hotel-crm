package com.example.visitor_crm_be.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Visitor> visitors = new ArrayList<>();
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();
    private String hotelName;                 // Hotel name
    private String staffName;           // First name of staff
    private String staffSurname;        // Last name of staff
    private String staffPhoneNumber;    // Contact number
    private String addressLine;         // Address
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<Visitor> visitors) {
        this.visitors = visitors;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffSurname() {
        return staffSurname;
    }

    public void setStaffSurname(String staffSurname) {
        this.staffSurname = staffSurname;
    }

    public String getStaffPhoneNumber() {
        return staffPhoneNumber;
    }

    public void setStaffPhoneNumber(String staffPhoneNumber) {
        this.staffPhoneNumber = staffPhoneNumber;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}


