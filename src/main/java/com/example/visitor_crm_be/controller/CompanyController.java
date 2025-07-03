package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.CompanyCreateDTO;
import com.example.visitor_crm_be.dto.CompanyResponseDTO;
import com.example.visitor_crm_be.model.Company;
import com.example.visitor_crm_be.model.Hotel;
import com.example.visitor_crm_be.model.User;
import com.example.visitor_crm_be.repository.CompanyRepository;
import com.example.visitor_crm_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @PostMapping("by-hotel")
    public ResponseEntity<CompanyResponseDTO> createCompanyByHotel(@RequestBody CompanyCreateDTO companyCreateDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        CompanyResponseDTO companyResponseDTO = new CompanyResponseDTO();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user == null) {
            companyResponseDTO.setHttpMessage("Kullanıcı bulunamadı, token kontrol edin");
            return new ResponseEntity<>(companyResponseDTO, HttpStatus.NOT_FOUND);
        }

        Hotel hotel = user.getHotel();

        if (hotel == null) {
            companyResponseDTO.setHttpMessage("Kayıtlı firma bulunamadı");
            return new ResponseEntity<>(companyResponseDTO, HttpStatus.NOT_FOUND);
        }

        Company company = new Company();
        company.setName(companyCreateDTO.getName());
        company.setHotel(hotel);
        company.setCreatedAt(OffsetDateTime.now());
        company.setUpdatedAt(OffsetDateTime.now());
        company = companyRepository.save(company);

        if (company == null) {
            companyResponseDTO.setHttpMessage("Firma kaydedilemedi");
            return new ResponseEntity<>(companyResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        companyResponseDTO.setId(company.getId());
        companyResponseDTO.setName(company.getName());
        companyResponseDTO.setHotelId(company.getHotel().getId());
        companyResponseDTO.setHttpMessage("Firma başarıyla kaydedildi");

        return ResponseEntity.created(URI.create("api/companies/by-hotel")).body(companyResponseDTO);
    }

    @GetMapping("/by-hotel")
    public ResponseEntity<List<CompanyResponseDTO>> getCompaniesByUserHotel() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long hotelId = user.getHotel().getId();

        List<Company> companies = companyRepository.findByHotelId(hotelId);

        List<CompanyResponseDTO> response = companies.stream().map(company -> {
            CompanyResponseDTO dto = new CompanyResponseDTO();
            dto.setId(company.getId());
            dto.setHotelId(company.getHotel().getId());
            dto.setName(company.getName());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
