package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.VehicleCreateDTO;
import com.example.visitor_crm_be.dto.VehicleResponseDTO;
import com.example.visitor_crm_be.dto.VehicleUpdateDTO;
import com.example.visitor_crm_be.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> create(@RequestBody VehicleCreateDTO dto) {
        return ResponseEntity.ok(vehicleService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> update(@PathVariable Long id, @RequestBody VehicleUpdateDTO dto) {
        return ResponseEntity.ok(vehicleService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(vehicleService.getAll(PageRequest.of(page, size)));
    }
}
