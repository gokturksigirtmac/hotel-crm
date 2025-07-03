package com.example.visitor_crm_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyResponseDTO {
    private Integer id;
    private String name;
    private Long hotelId;
    private String httpMessage;
}

