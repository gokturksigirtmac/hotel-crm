package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.CompanyCreateDTO;
import com.example.visitor_crm_be.dto.CompanyResponseDTO;
import com.example.visitor_crm_be.dto.CompanyUpdateDTO;
import com.example.visitor_crm_be.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponseDTO> createCompany(@RequestBody CompanyCreateDTO dto) {
        return ResponseEntity.ok(companyService.createCompany(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> updateCompany(
            @PathVariable Integer id,
            @RequestBody CompanyUpdateDTO dto) {
        return ResponseEntity.ok(companyService.updateCompany(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> getCompanyById(@PathVariable Integer id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Integer id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<CompanyResponseDTO>> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(companyService.getAllCompanies(pageable));
    }
}
