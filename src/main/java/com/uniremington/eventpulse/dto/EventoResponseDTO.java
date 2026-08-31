package com.uniremington.eventpulse.dto;

import java.time.LocalDate;

public record EventoResponseDTO(
    Long id,
    String nombre,
    String categoria,
    LocalDate fecha,
    Integer capacidadMaxima,
    Double precioEntrada
) {}