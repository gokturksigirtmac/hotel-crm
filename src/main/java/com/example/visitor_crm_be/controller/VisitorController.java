package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.VisitorCreateDTO;
import com.example.visitor_crm_be.dto.VisitorResponseDTO;
import com.example.visitor_crm_be.dto.VisitorUpdateDTO;
import com.example.visitor_crm_be.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
public class VisitorController {
    private final VisitorService visitorService;

    @PostMapping
    public ResponseEntity<VisitorResponseDTO> create(@RequestBody VisitorCreateDTO dto) {
        return ResponseEntity.ok(visitorService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitorResponseDTO> update(@PathVariable Long id, @RequestBody VisitorUpdateDTO dto) {
        return ResponseEntity.ok(visitorService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitorResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(visitorService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        visitorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<VisitorResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(visitorService.getAll(PageRequest.of(page, size)));
    }
}
