package com.uniremington.eventpulse.service;

import com.uniremington.eventpulse.dto.EventoRequestDTO;
import com.uniremington.eventpulse.dto.EventoResponseDTO;
import com.uniremington.eventpulse.model.Evento;
import com.uniremington.eventpulse.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public EventoResponseDTO crearEvento(EventoRequestDTO dto) {
        Evento nuevoEvento = new Evento(
                null,
                dto.nombre(),
                dto.categoria(),
                dto.fecha(),
                dto.capacidadMaxima(),
                dto.precioEntrada()
        );
        Evento guardado = eventoRepository.save(nuevoEvento);
        return mapearADTO(guardado);
    }

    public List<EventoResponseDTO> listarTodos(String categoria) {
        List<Evento> eventos;
        if (categoria != null && !categoria.isBlank()) {
            eventos = eventoRepository.findByCategoriaIgnoreCase(categoria);
        } else {
            eventos = eventoRepository.findAll();
        }
        return eventos.stream().map(this::mapearADTO).toList();
    }

    public Optional<EventoResponseDTO> buscarPorId(Long id) {
        return eventoRepository.findById(id).map(this::mapearADTO);
    }

    public Optional<EventoResponseDTO> actualizarEvento(Long id, EventoRequestDTO dto) {
        return eventoRepository.findById(id).map(eventoExistente -> {
            eventoExistente.setNombre(dto.nombre());
            eventoExistente.setCategoria(dto.categoria());
            eventoExistente.setFecha(dto.fecha());
            eventoExistente.setCapacidadMaxima(dto.capacidadMaxima());
            eventoExistente.setPrecioEntrada(dto.precioEntrada());
            
            Evento actualizado = eventoRepository.save(eventoExistente);
            return mapearADTO(actualizado);
        });
    }

    public boolean eliminarEvento(Long id) {
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private EventoResponseDTO mapearADTO(Evento evento) {
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