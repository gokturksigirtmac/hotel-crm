package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.DriverCreateDTO;
import com.example.visitor_crm_be.dto.DriverResponseDTO;
import com.example.visitor_crm_be.dto.DriverUpdateDTO;
import com.example.visitor_crm_be.model.Driver;
import com.example.visitor_crm_be.model.Hotel;
import com.example.visitor_crm_be.model.User;
import com.example.visitor_crm_be.repository.DriverRepository;
import com.example.visitor_crm_be.repository.UserRepository;
import com.example.visitor_crm_be.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;

    @PostMapping("by-hotel")
    public ResponseEntity<DriverResponseDTO> createDriverByHotel(@RequestBody DriverCreateDTO driverCreateDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        DriverResponseDTO driverResponseDTO = new DriverResponseDTO();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user == null) {
            driverResponseDTO.setHttpMessage("Kullanıcı bulunamadı, token kontrol edin");
            return new ResponseEntity<>(driverResponseDTO, HttpStatus.NOT_FOUND);
        }

        Hotel hotel = user.getHotel();

        if (hotel == null) {
            driverResponseDTO.setHttpMessage("Kayıtlı firma bulunamadı");
            return new ResponseEntity<>(driverResponseDTO, HttpStatus.NOT_FOUND);
        }

        Driver driver = new Driver();
        driver.setHotel(hotel);
        driver.setFirstName(driverCreateDTO.getFirstName());
        driver.setLastName(driverCreateDTO.getLastName());
        driver.setPhoneNumber(driverCreateDTO.getPhoneNumber());
        driver.setLicenseNumber(driverCreateDTO.getLicenseNumber());

        driver = driverRepository.save(driver);

        if (driver == null) {
            driverResponseDTO.setHttpMessage("Sürücü kaydedilemedi");
            return new ResponseEntity<>(driverResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        driverResponseDTO.setId(driver.getId());
        driverResponseDTO.setHotelId(hotel.getId());
        driverResponseDTO.setFirstName(driver.getFirstName());
        driverResponseDTO.setLastName(driver.getLastName());
        driverResponseDTO.setPhoneNumber(driver.getPhoneNumber());
        driverResponseDTO.setLicenseNumber(driver.getLicenseNumber());
        driverResponseDTO.setHttpMessage("Sürücü başarıyla kaydedildi");

        return new ResponseEntity<>(driverResponseDTO, HttpStatus.CREATED);
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

    @GetMapping("/by-hotel")
    public ResponseEntity<List<DriverResponseDTO>> getDriversByUserHotel() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long hotelId = user.getHotel().getId();

        List<Driver> drivers = driverRepository.findByHotelId(hotelId);

        List<DriverResponseDTO> response = drivers.stream().map(driver -> {
            DriverResponseDTO dto = new DriverResponseDTO();
            dto.setId(driver.getId());
            dto.setHotelId(driver.getHotel().getId());
            dto.setFirstName(driver.getFirstName());
            dto.setLastName(driver.getLastName());
            dto.setPhoneNumber(driver.getPhoneNumber());
            dto.setLicenseNumber(driver.getLicenseNumber());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
