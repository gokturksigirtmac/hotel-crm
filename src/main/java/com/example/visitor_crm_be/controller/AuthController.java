package com.example.visitor_crm_be.controller;

import com.example.visitor_crm_be.dto.AuthRequestDTO;
import com.example.visitor_crm_be.dto.AuthResponseDTO;

import com.example.visitor_crm_be.dto.UserCreateDTO;
import com.example.visitor_crm_be.dto.UserResponseDTO;
import com.example.visitor_crm_be.error.UserAlreadyExistsException;
import com.example.visitor_crm_be.model.User;
import com.example.visitor_crm_be.repository.UserRepository;
import com.example.visitor_crm_be.security.JwtHelper;
import com.example.visitor_crm_be.security.SecurityConfig;
import com.example.visitor_crm_be.service.UserImpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;
    @Autowired
    private UserImpService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<AuthResponseDTO> createUser(@RequestBody UserCreateDTO userRequestDto) {
        try {
            UserResponseDTO userResponseDto = userService.createUser(userRequestDto);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userResponseDto.getUsername());
            System.out.println("from db info");
            System.out.println(userDetails.getUsername());
            System.out.println(userDetails.getPassword());

            String token = this.helper.generateToken(userDetails);
            AuthResponseDTO authResponseDTO = AuthResponseDTO.builder().token(token).build();
            return new ResponseEntity<>(authResponseDTO, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException ex) {
            // Handle the exception and return an appropriate response
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(AuthResponseDTO.builder()
                            .message("User already exists: " + ex.getMessage())
                            .build()
                    );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        this.doAuthenticate(authRequestDTO.getUsername(), authRequestDTO.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDTO.getUsername());
        Optional<User> user = userRepository.findByEmail(authRequestDTO.getUsername());

        AuthResponseDTO authResponseDTO = null;

        // Check if trial is suspended
        if (user.isPresent() && user.get().getHotel().isSuspended()) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(authResponseDTO.builder()
                            .message("Trial period expired. Please contact support.")
                            .build()
                    );
        }
        // Generate JWT
        String token = this.helper.generateToken(userDetails);

        // Return token and success message
        return ResponseEntity.ok(
                authResponseDTO.builder()
                        .token(token)
                        .message("Authenticated successfully.")
                        .build()
        );
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AuthResponseDTO> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(AuthResponseDTO.builder()
                        .message("Invalid username or password")
                        .build()
                );
    }

}