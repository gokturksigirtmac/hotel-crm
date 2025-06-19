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
        Hotel hotel = hotelRepository.findById(dto.getHotel().getId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Visitor visitor = new Visitor();
        visitor.setHotel(hotel);
        visitor.setCompany(dto.getCompany());
        visitor.setDriver(dto.getDriver());
        visitor.setVehicle(dto.getVehicle());
        visitor.setFullName(dto.getFullName());
        visitor.setPhoneNumber(dto.getPhoneNumber());
        visitor.setNumberOfPersons(dto.getNumberofPersons());
        visitor.setCreatedAt(OffsetDateTime.now());
        visitor.setUpdatedAt(OffsetDateTime.now());

        return mapToDTO(visitorRepository.save(visitor));
    }

    public VisitorResponseDTO update(Long id, VisitorUpdateDTO dto) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));

        visitor.setCompany(dto.getCompany());
        visitor.setDriver(dto.getDriver());
        visitor.setVehicle(dto.getVehicle());
        visitor.setFullName(dto.getFullName());
        visitor.setPhoneNumber(dto.getPhoneNumber());
        visitor.setNumberOfPersons(dto.getNumberOfPersons());
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
        dto.setHotel(visitor.getHotel());
        dto.setCompany(visitor.getCompany());
        dto.setDriver(visitor.getDriver());
        dto.setVehicle(visitor.getVehicle());
        dto.setFullName(visitor.getFullName());
        dto.setPhoneNumber(visitor.getPhoneNumber());
        dto.setNumberOfPersons(visitor.getNumberOfPersons());
        dto.setCreatedAt(visitor.getCreatedAt());
        dto.setUpdatedAt(visitor.getUpdatedAt());
        return dto;
    }
}
