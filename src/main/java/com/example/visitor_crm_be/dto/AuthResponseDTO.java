package com.example.visitor_crm_be.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthResponseDTO {
    private String message;
    private String token;
}

