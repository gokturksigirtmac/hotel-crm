package com.example.visitor_crm_be.service;

import com.example.visitor_crm_be.dto.UserCreateDTO;
import com.example.visitor_crm_be.dto.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserResponseDTO> getAllUser();
    UserResponseDTO createUser(UserCreateDTO userCreateDTO);

}
