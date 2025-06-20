package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.LocationCreateDTO;
import com.example.visitor_crm_be.dto.LocationResponseDTO;
import com.example.visitor_crm_be.dto.LocationUpdateDTO;
import com.example.visitor_crm_be.model.Location;
import com.example.visitor_crm_be.model.User;
import com.example.visitor_crm_be.repository.LocationRepository;
import com.example.visitor_crm_be.repository.UserRepository;
import com.example.visitor_crm_be.service.LocationService;
import com.example.visitor_crm_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @PostMapping
    public ResponseEntity<LocationResponseDTO> create(@RequestBody LocationCreateDTO dto) {
        return ResponseEntity.ok(locationService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> update(@PathVariable Long id, @RequestBody LocationUpdateDTO dto) {
        return ResponseEntity.ok(locationService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<LocationResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(locationService.getAll(PageRequest.of(page, size)));
    }

    @GetMapping("/by-hotel")
    public ResponseEntity<List<LocationResponseDTO>> getLocationsByUserHotel() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long hotelId = user.getHotel().getId();

        List<Location> locations = locationRepository.findByHotelId(hotelId);

        List<LocationResponseDTO> response = locations.stream().map(location -> {
            LocationResponseDTO dto = new LocationResponseDTO();
            dto.setId(location.getId());
            dto.setHotelId(location.getHotel().getId());
            dto.setLocation(location.getLocation());
            dto.setDescription(location.getDescription());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
