package com.example.citronix.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class VenteDTO {
    @NotNull(message = "Sale date is required")
    private LocalDate date;

    @NotNull(message = "Unit price is required")
    @Min(value = 1, message = "Unit price must be greater than zero")
    private Double unitPrice;

    @NotBlank(message = "Client is required")
    private String client;

    @NotNull(message = "Recolte ID is required")
    private Long recolteId;
}
