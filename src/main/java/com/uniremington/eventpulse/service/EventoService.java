package com.uniremington.eventpulse.service;

import com.uniremington.eventpulse.dto.EventoRequestDTO;
import com.uniremington.eventpulse.dto.EventoResponseDTO;
import com.uniremington.eventpulse.model.Evento;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EventoService {

    private final Map<Long, Evento> baseDeDatos = new HashMap<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public EventoService() {
        guardarEventoInterno(new Evento(contadorId.getAndIncrement(), "DevOps Summit", "Tecnologia", LocalDate.of(2026, 10, 15), 300, 120.0));
        guardarEventoInterno(new Evento(contadorId.getAndIncrement(), "Java Cloud Conf", "Tecnologia", LocalDate.of(2026, 11, 20), 500, 150.0));
        guardarEventoInterno(new Evento(contadorId.getAndIncrement(), "Festival Indie Sound", "Musica", LocalDate.of(2026, 12, 5), 1200, 80.0));
    }

    private void guardarEventoInterno(Evento evento) {
        baseDeDatos.put(evento.getId(), evento);
    }

    public List<EventoResponseDTO> listarTodos(String categoria) {
        return baseDeDatos.values().stream()
                .filter(e -> categoria == null || e.getCategoria().equalsIgnoreCase(categoria))
                .map(this::mapearADto)
                .toList();
    }

    public Optional<EventoResponseDTO> buscarPorId(Long id) {
        return Optional.ofNullable(baseDeDatos.get(id)).map(this::mapearADto);
    }

    public EventoResponseDTO crearEvento(EventoRequestDTO dto) {
        Long nuevoId = contadorId.getAndIncrement();
        Evento nuevoEvento = new Evento(
                nuevoId,
                dto.nombre(),
                dto.categoria(),
                dto.fecha(),
                dto.capacidadMaxima(),
                dto.precioEntrada()
        );
        baseDeDatos.put(nuevoId, nuevoEvento);
        return mapearADto(nuevoEvento);
    }

    private EventoResponseDTO mapearADto(Evento evento) {
        return new EventoResponseDTO(
                evento.getId(),
                evento.getNombre(),
                evento.getCategoria(),
                evento.getFecha(),
                evento.getCapacidadMaxima(),
                evento.getPrecioEntrada()
        );
    }
}