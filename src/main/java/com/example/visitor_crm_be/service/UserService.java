package com.example.visitor_crm_be.service;

import com.example.visitor_crm_be.dto.UserCreateDTO;
import com.example.visitor_crm_be.dto.UserResponseDTO;
import com.example.visitor_crm_be.dto.UserUpdateDTO;
import com.example.visitor_crm_be.model.Hotel;
import com.example.visitor_crm_be.model.User;
import com.example.visitor_crm_be.repository.HotelRepository;
import com.example.visitor_crm_be.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public UserResponseDTO create(UserCreateDTO dto, User requester) {
        if (!"ADMIN".equals(requester.getRole())) {
            throw new AccessDeniedException("Only ADMIN can create users");
        }

        Hotel hotel = hotelRepository.findById(dto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        User user = new User();
        user.setHotel(hotel);
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        return mapToDTO(userRepository.save(user));
    }

    public Long extractHotelIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("hotelId", Long.class);
    }

    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword()); // Again, hash in production!
        user.setRole(dto.getRole());

        return mapToDTO(userRepository.save(user));
    }

    public UserResponseDTO getById(Long id) {
        return mapToDTO(userRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }


    public void delete(Long id) {
        if (!userRepository.existsById(String.valueOf(id))) throw new RuntimeException("User not found");
        userRepository.deleteById(String.valueOf(id));
    }

    public Page<UserResponseDTO> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::mapToDTO);
    }

    private UserResponseDTO mapToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setHotelId(user.getHotel().getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

