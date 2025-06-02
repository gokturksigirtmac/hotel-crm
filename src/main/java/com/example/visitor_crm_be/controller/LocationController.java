package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.LocationCreateDTO;
import com.example.visitor_crm_be.dto.LocationResponseDTO;
import com.example.visitor_crm_be.dto.LocationUpdateDTO;
import com.example.visitor_crm_be.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

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
}
