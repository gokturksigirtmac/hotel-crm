package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.CompanyResponseDTO;
import com.example.visitor_crm_be.dto.LocationCreateDTO;
import com.example.visitor_crm_be.dto.LocationResponseDTO;
import com.example.visitor_crm_be.dto.LocationUpdateDTO;
import com.example.visitor_crm_be.model.Hotel;
import com.example.visitor_crm_be.model.Location;
import com.example.visitor_crm_be.model.User;
import com.example.visitor_crm_be.repository.LocationRepository;
import com.example.visitor_crm_be.repository.UserRepository;
import com.example.visitor_crm_be.service.LocationService;
import com.example.visitor_crm_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @PostMapping("by-hotel")
    public ResponseEntity<LocationResponseDTO> createLocationByHotel(@RequestBody LocationCreateDTO locationCreateDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        LocationResponseDTO locationResponseDTO = new LocationResponseDTO();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user == null) {
            locationResponseDTO.setHttpMessage("Kullanıcı bulunamadı, token kontrol edin");
            return new ResponseEntity<>(locationResponseDTO, HttpStatus.NOT_FOUND);
        }

        Hotel hotel = user.getHotel();

        if (hotel == null) {
            locationResponseDTO.setHttpMessage("Kayıtlı firma bulunamadı");
            return new ResponseEntity<>(locationResponseDTO, HttpStatus.NOT_FOUND);
        }

        Location location = new Location();
        location.setHotel(hotel);
        location.setLocation(locationCreateDTO.getLocation());
        location.setDescription(locationCreateDTO.getDescription());
        location.setCreatedAt(OffsetDateTime.now());
        location.setUpdatedAt(OffsetDateTime.now());
        Location savedLocation = locationRepository.save(location);

        if (savedLocation == null) {
            locationResponseDTO.setHttpMessage("Konum kaydedilemedi");
            return new ResponseEntity<>(locationResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        locationResponseDTO.setId(savedLocation.getId());
        locationResponseDTO.setLocation(savedLocation.getLocation());
        locationResponseDTO.setDescription(savedLocation.getDescription());
        locationResponseDTO.setHttpMessage("Konum başarıyla kaydedildi");

        return new ResponseEntity<>(locationResponseDTO, HttpStatus.CREATED);
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
