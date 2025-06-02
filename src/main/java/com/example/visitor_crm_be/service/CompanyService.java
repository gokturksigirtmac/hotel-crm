package com.example.visitor_crm_be.service;

import com.example.visitor_crm_be.dto.CompanyCreateDTO;
import com.example.visitor_crm_be.dto.CompanyResponseDTO;
import com.example.visitor_crm_be.dto.CompanyUpdateDTO;
import com.example.visitor_crm_be.model.Company;
import com.example.visitor_crm_be.model.Hotel;
import com.example.visitor_crm_be.repository.CompanyRepository;
import com.example.visitor_crm_be.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final HotelRepository hotelRepository;

    // Create a new company
    public CompanyResponseDTO createCompany(CompanyCreateDTO dto) {
        Hotel hotel = hotelRepository.findById(dto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Company company = new Company();
        company.setName(dto.getName());
        company.setHotel(hotel);
        company.setCreatedAt(OffsetDateTime.now());
        company.setUpdatedAt(OffsetDateTime.now());

        companyRepository.save(company);
        return mapToDTO(company);
    }

    // Update existing company
    public CompanyResponseDTO updateCompany(Integer id, CompanyUpdateDTO dto) {
        Company company = companyRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setName(dto.getName());
        company.setUpdatedAt(OffsetDateTime.now());

        companyRepository.save(company);
        return mapToDTO(company);
    }

    // Get company by ID
    public CompanyResponseDTO getCompanyById(Integer id) {
        Company company = companyRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Company not found"));

        return mapToDTO(company);
    }

    // Delete company
    public void deleteCompany(Integer id) {
        if (!companyRepository.existsById(Long.valueOf(id))) {
            throw new RuntimeException("Company not found");
        }
        companyRepository.deleteById(Long.valueOf(id));
    }

    // Get paginated list of companies
    public Page<CompanyResponseDTO> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // Manual mapper
    private CompanyResponseDTO mapToDTO(Company company) {
        CompanyResponseDTO dto = new CompanyResponseDTO();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setHotelId(company.getHotel().getId());
        dto.setCreatedAt(company.getCreatedAt());
        dto.setUpdatedAt(company.getUpdatedAt());
        return dto;
    }
}
