package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.LocationResponseDTO;
import com.example.visitor_crm_be.dto.VehicleCreateDTO;
import com.example.visitor_crm_be.dto.VehicleResponseDTO;
import com.example.visitor_crm_be.dto.VehicleUpdateDTO;
import com.example.visitor_crm_be.model.Hotel;
import com.example.visitor_crm_be.model.User;
import com.example.visitor_crm_be.model.Vehicle;
import com.example.visitor_crm_be.repository.UserRepository;
import com.example.visitor_crm_be.repository.VehicleRepository;
import com.example.visitor_crm_be.service.UserService;
import com.example.visitor_crm_be.service.VehicleService;
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
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    private final VehicleRepository vehicleRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    @PostMapping("/by-hotel")
    public ResponseEntity<VehicleResponseDTO> createVehicleByHotel(@RequestBody VehicleCreateDTO vehicleCreateDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        VehicleResponseDTO vehicleResponseDTO = new VehicleResponseDTO();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user == null) {
            vehicleResponseDTO.setHttpMessage("Kullanıcı bulunamadı, token kontrol edin");
            return new ResponseEntity<>(vehicleResponseDTO, HttpStatus.NOT_FOUND);
        }

        Hotel hotel = user.getHotel();

        if (hotel == null) {
            vehicleResponseDTO.setHttpMessage("Kayıtlı firma bulunamadı");
            return new ResponseEntity<>(vehicleResponseDTO, HttpStatus.NOT_FOUND);
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setHotel(hotel);
        vehicle.setModel(vehicleCreateDTO.getModel());
        vehicle.setBrand(vehicleCreateDTO.getBrand());
        vehicle.setPlateNumber(vehicleCreateDTO.getPlateNumber());
        vehicle.setType(vehicleCreateDTO.getType());
        vehicle.setCreatedAt(OffsetDateTime.now());
        vehicle.setUpdatedAt(OffsetDateTime.now());
        vehicle = vehicleRepository.save(vehicle);

        if (vehicle == null) {
            vehicleResponseDTO.setHttpMessage("Araç kaydedilemedi");
            return new ResponseEntity<>(vehicleResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        vehicleResponseDTO.setHotelId(vehicle.getId());
        vehicleResponseDTO.setModel(vehicleCreateDTO.getModel());
        vehicleResponseDTO.setBrand(vehicleCreateDTO.getBrand());
        vehicleResponseDTO.setPlateNumber(vehicleCreateDTO.getPlateNumber());
        vehicleResponseDTO.setType(vehicleCreateDTO.getType());
        vehicleResponseDTO.setHttpMessage("Araç başarıyla kaydedildi");

        return new ResponseEntity<>(vehicleResponseDTO, HttpStatus.CREATED);
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

    @GetMapping("/by-hotel")
    public ResponseEntity<List<VehicleResponseDTO>> getVehiclesByUserHotel() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("email: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long hotelId = user.getHotel().getId();
        List<Vehicle> vehicles = vehicleRepository.findByHotelId(hotelId);

        List<VehicleResponseDTO> response = vehicles.stream().map(vehicle -> {
            VehicleResponseDTO dto = new VehicleResponseDTO();
            dto.setId(vehicle.getId());
            dto.setHotelId(vehicle.getHotel().getId());
            dto.setPlateNumber(vehicle.getPlateNumber());
            dto.setBrand(vehicle.getBrand());
            dto.setModel(vehicle.getModel());
            dto.setType(vehicle.getType());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
