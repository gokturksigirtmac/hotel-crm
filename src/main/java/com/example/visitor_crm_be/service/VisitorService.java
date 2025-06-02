package com.example.visitor_crm_be.service;

import com.example.visitor_crm_be.dto.VisitorCreateDTO;
import com.example.visitor_crm_be.dto.VisitorResponseDTO;
import com.example.visitor_crm_be.dto.VisitorUpdateDTO;
import com.example.visitor_crm_be.model.Hotel;
import com.example.visitor_crm_be.model.Visitor;
import com.example.visitor_crm_be.repository.HotelRepository;
import com.example.visitor_crm_be.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.*;

@Service
@RequiredArgsConstructor
public class VisitorService {
    private final VisitorRepository visitorRepository;
    private final HotelRepository hotelRepository;

    public VisitorResponseDTO create(VisitorCreateDTO dto) {
        Hotel hotel = hotelRepository.findById(dto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Visitor visitor = new Visitor();
        visitor.setHotel(hotel);
        visitor.setCompanyID(dto.getCompanyID());
        visitor.setDriverID(dto.getDriverID());
        visitor.setVehicleID(dto.getVehicleID());
        visitor.setFullName(dto.getFullName());
        visitor.setPhoneNumber(dto.getPhoneNumber());
        visitor.setEmail(dto.getEmail());
        visitor.setPassangers(dto.getPassangers());
        visitor.setOneWayOrRoundTrip(dto.getOneWayOrRoundTrip());
        visitor.setDepartureDateTime(dto.getDepartureDateTime());
        visitor.setVisitorFrom(dto.getVisitorFrom());
        visitor.setVisitorTo(dto.getVisitorTo());
        visitor.setFlightNumber(dto.getFlightNumber());
        visitor.setPrice(dto.getPrice());
        visitor.setCurrency(dto.getCurrency());
        visitor.setHotelName(dto.getHotelName());
        visitor.setNote(dto.getNote());
        visitor.setCreatedAt(OffsetDateTime.now());
        visitor.setUpdatedAt(OffsetDateTime.now());

        return mapToDTO(visitorRepository.save(visitor));
    }

    public VisitorResponseDTO update(Long id, VisitorUpdateDTO dto) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));

        visitor.setCompanyID(dto.getCompanyID());
        visitor.setDriverID(dto.getDriverID());
        visitor.setVehicleID(dto.getVehicleID());
        visitor.setFullName(dto.getFullName());
        visitor.setPhoneNumber(dto.getPhoneNumber());
        visitor.setEmail(dto.getEmail());
        visitor.setPassangers(dto.getPassangers());
        visitor.setOneWayOrRoundTrip(dto.getOneWayOrRoundTrip());
        visitor.setDepartureDateTime(dto.getDepartureDateTime());
        visitor.setVisitorFrom(dto.getVisitorFrom());
        visitor.setVisitorTo(dto.getVisitorTo());
        visitor.setFlightNumber(dto.getFlightNumber());
        visitor.setPrice(dto.getPrice());
        visitor.setCurrency(dto.getCurrency());
        visitor.setHotelName(dto.getHotelName());
        visitor.setNote(dto.getNote());
        visitor.setUpdatedAt(OffsetDateTime.now());

        return mapToDTO(visitorRepository.save(visitor));
    }

    public VisitorResponseDTO getById(Long id) {
        return mapToDTO(visitorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitor not found")));
    }

    public void delete(Long id) {
        if (!visitorRepository.existsById(id)) throw new RuntimeException("Visitor not found");
        visitorRepository.deleteById(id);
    }

    public Page<VisitorResponseDTO> getAll(Pageable pageable) {
        return visitorRepository.findAll(pageable).map(this::mapToDTO);
    }

    private VisitorResponseDTO mapToDTO(Visitor visitor) {
        VisitorResponseDTO dto = new VisitorResponseDTO();
        dto.setId(visitor.getId());
        dto.setHotelId(visitor.getHotel().getId());
        dto.setCompanyID(visitor.getCompanyID());
        dto.setDriverID(visitor.getDriverID());
        dto.setVehicleID(visitor.getVehicleID());
        dto.setFullName(visitor.getFullName());
        dto.setPhoneNumber(visitor.getPhoneNumber());
        dto.setEmail(visitor.getEmail());
        dto.setPassangers(visitor.getPassangers());
        dto.setOneWayOrRoundTrip(visitor.getOneWayOrRoundTrip());
        dto.setDepartureDateTime(visitor.getDepartureDateTime());
        dto.setVisitorFrom(visitor.getVisitorFrom());
        dto.setVisitorTo(visitor.getVisitorTo());
        dto.setFlightNumber(visitor.getFlightNumber());
        dto.setPrice(visitor.getPrice());
        dto.setCurrency(visitor.getCurrency());
        dto.setHotelName(visitor.getHotelName());
        dto.setNote(visitor.getNote());
        dto.setCreatedAt(visitor.getCreatedAt());
        dto.setUpdatedAt(visitor.getUpdatedAt());
        return dto;
    }
}
