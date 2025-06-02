package com.example.visitor_crm_be.service;

import com.example.visitor_crm_be.dto.LocationCreateDTO;
import com.example.visitor_crm_be.dto.LocationResponseDTO;
import com.example.visitor_crm_be.dto.LocationUpdateDTO;
import com.example.visitor_crm_be.model.Hotel;
import com.example.visitor_crm_be.model.Location;
import com.example.visitor_crm_be.repository.HotelRepository;
import com.example.visitor_crm_be.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final HotelRepository hotelRepository;

    public LocationResponseDTO create(LocationCreateDTO dto) {
        Hotel hotel = hotelRepository.findById(dto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Location location = new Location();
        location.setHotel(hotel);
        location.setLocation(dto.getLocation());
        location.setDescription(dto.getDescription());
        location.setCreatedAt(OffsetDateTime.now());
        location.setUpdatedAt(OffsetDateTime.now());

        return mapToDTO(locationRepository.save(location));
    }

    public LocationResponseDTO update(Long id, LocationUpdateDTO dto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        location.setLocation(dto.getLocation());
        location.setDescription(dto.getDescription());
        location.setUpdatedAt(OffsetDateTime.now());

        return mapToDTO(locationRepository.save(location));
    }

    public LocationResponseDTO getById(Long id) {
        return mapToDTO(locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found")));
    }

    public void delete(Long id) {
        if (!locationRepository.existsById(id)) throw new RuntimeException("Location not found");
        locationRepository.deleteById(id);
    }

    public Page<LocationResponseDTO> getAll(Pageable pageable) {
        return locationRepository.findAll(pageable).map(this::mapToDTO);
    }

    private LocationResponseDTO mapToDTO(Location location) {
        LocationResponseDTO dto = new LocationResponseDTO();
        dto.setId(location.getId());
        dto.setHotelId(location.getHotel().getId());
        dto.setLocation(location.getLocation());
        dto.setDescription(location.getDescription());
        dto.setCreatedAt(location.getCreatedAt());
        dto.setUpdatedAt(location.getUpdatedAt());
        return dto;
    }
}
