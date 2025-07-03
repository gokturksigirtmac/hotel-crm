package com.example.visitor_crm_be.service;

import com.example.visitor_crm_be.dto.VehicleCreateDTO;
import com.example.visitor_crm_be.dto.VehicleResponseDTO;
import com.example.visitor_crm_be.dto.VehicleUpdateDTO;
import com.example.visitor_crm_be.model.Hotel;
import com.example.visitor_crm_be.model.Vehicle;
import com.example.visitor_crm_be.repository.HotelRepository;
import com.example.visitor_crm_be.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleResponseDTO update(Long id, VehicleUpdateDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setPlateNumber(dto.getLicensePlateNumber());
        vehicle.setUpdatedAt(OffsetDateTime.now());

        return mapToDTO(vehicleRepository.save(vehicle));
    }

    public VehicleResponseDTO getById(Long id) {
        return mapToDTO(vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found")));
    }

    public void delete(Long id) {
        if (!vehicleRepository.existsById(id)) throw new RuntimeException("Vehicle not found");
        vehicleRepository.deleteById(id);
    }

    public Page<VehicleResponseDTO> getAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable).map(this::mapToDTO);
    }

    private VehicleResponseDTO mapToDTO(Vehicle vehicle) {
        VehicleResponseDTO dto = new VehicleResponseDTO();
        dto.setId(vehicle.getId());
        dto.setHotelId(vehicle.getHotel().getId());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setPlateNumber(vehicle.getPlateNumber());
        return dto;
    }
}
