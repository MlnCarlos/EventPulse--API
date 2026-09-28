package com.uniremington.eventpulse.dto;

import java.time.LocalDate;

public record EventoResponseDTO(
    Long id,
    String nombre,
    String categoriaNombre,
    LocalDate fecha,
    Integer capacidadMaxima,
    Double precioEntrada,
    Double latitud,
    Double longitud
) {}