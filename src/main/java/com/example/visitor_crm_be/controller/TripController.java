package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.TripResponseDTO;
import com.example.visitor_crm_be.model.Trip;
import com.example.visitor_crm_be.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    @Autowired
    private TripRepository tripRepository;

    @DeleteMapping("/{id}")
    public ResponseEntity<TripResponseDTO> deleteTrip(@PathVariable Long id) {
        Trip trip = tripRepository.findById(id).orElse(null);

        TripResponseDTO tripResponseDTO = new TripResponseDTO();

        if (trip == null) {
            tripResponseDTO.setHttpMessage("Böyle bir seyahat bulunamadı");
            return new ResponseEntity<>(tripResponseDTO, HttpStatus.NOT_FOUND);
        }

        try {
            tripRepository.deleteById(id);
            tripResponseDTO.setHttpMessage("Seyahat başarıyla silindi");
            return new ResponseEntity<>(tripResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            tripResponseDTO.setHttpMessage("Silme işlemi başarısız oldu: " + e.getMessage());
            return new ResponseEntity<>(tripResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
