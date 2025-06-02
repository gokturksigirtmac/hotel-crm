package com.example.visitor_crm_be.dto;

public class CompanyCreateDTO {
    private String name;
    private Long hotelId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }
}

