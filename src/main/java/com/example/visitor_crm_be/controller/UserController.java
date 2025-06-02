package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.UserCreateDTO;
import com.example.visitor_crm_be.dto.UserResponseDTO;
import com.example.visitor_crm_be.model.User;
import com.example.visitor_crm_be.security.JwtService;
import com.example.visitor_crm_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestHeader("Authorization") String authHeader,
                                                  @RequestBody UserCreateDTO dto) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        User requester = userService.getByUsername(username);
        return ResponseEntity.ok(userService.create(dto, requester));
    }

    // Other endpoints can remain the same or also use @PreAuthorize if desired.
}
