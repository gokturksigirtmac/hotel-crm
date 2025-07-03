package com.example.visitor_crm_be.service;

import com.example.visitor_crm_be.dto.DriverCreateDTO;
import com.example.visitor_crm_be.dto.DriverResponseDTO;
import com.example.visitor_crm_be.dto.DriverUpdateDTO;
import com.example.visitor_crm_be.model.Driver;
import com.example.visitor_crm_be.model.Hotel;
import com.example.visitor_crm_be.repository.DriverRepository;
import com.example.visitor_crm_be.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverResponseDTO update(Long id, DriverUpdateDTO dto) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setFirstName(dto.getFirstName());
        driver.setLastName(dto.getLastName());
        driver.setPhoneNumber(dto.getPhoneNumber());
        driver.setUpdatedAt(OffsetDateTime.now());

        return mapToDTO(driverRepository.save(driver));
    }

    public DriverResponseDTO getById(Long id) {
        return mapToDTO(driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found")));
    }

    public void delete(Long id) {
        if (!driverRepository.existsById(id)) throw new RuntimeException("Driver not found");
        driverRepository.deleteById(id);
    }

    public Page<DriverResponseDTO> getAll(Pageable pageable) {
        return driverRepository.findAll(pageable).map(this::mapToDTO);
    }

    private DriverResponseDTO mapToDTO(Driver driver) {
        DriverResponseDTO dto = new DriverResponseDTO();
        dto.setId(driver.getId());
        dto.setHotelId(driver.getHotel().getId());
        dto.setFirstName(driver.getFirstName());
        dto.setLastName(driver.getLastName());
        dto.setPhoneNumber(driver.getPhoneNumber());
        return dto;
    }
}

