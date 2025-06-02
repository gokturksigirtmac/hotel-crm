package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.DriverCreateDTO;
import com.example.visitor_crm_be.dto.DriverResponseDTO;
import com.example.visitor_crm_be.dto.DriverUpdateDTO;
import com.example.visitor_crm_be.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<DriverResponseDTO> create(@RequestBody DriverCreateDTO dto) {
        return ResponseEntity.ok(driverService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> update(@PathVariable Long id, @RequestBody DriverUpdateDTO dto) {
        return ResponseEntity.ok(driverService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        driverService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DriverResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "30") int size) {
        return ResponseEntity.ok(driverService.getAll(PageRequest.of(page, size)));
    }
}
