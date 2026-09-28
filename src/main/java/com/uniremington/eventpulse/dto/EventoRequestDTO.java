package com.uniremington.eventpulse.dto;

import java.time.LocalDate;

public record EventoRequestDTO(
    String nombre,
    Long categoriaId,
    LocalDate fecha,
    Integer capacidadMaxima,
    Double precioEntrada,
    Double latitud,
    Double longitud
) {}