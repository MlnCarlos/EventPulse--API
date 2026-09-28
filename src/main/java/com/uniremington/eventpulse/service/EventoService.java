package com.uniremington.eventpulse.service;

import com.uniremington.eventpulse.dto.EventoRequestDTO;
import com.uniremington.eventpulse.dto.EventoResponseDTO;
import com.uniremington.eventpulse.model.Categoria;
import com.uniremington.eventpulse.model.Evento;
import com.uniremington.eventpulse.repository.CategoriaRepository;
import com.uniremington.eventpulse.repository.EventoRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private static final Logger logger = LoggerFactory.getLogger(EventoService.class);

    private final EventoRepository eventoRepository;
    private final CategoriaRepository categoriaRepository;
    private final Counter eventosCreadosCounter;

    public EventoService(EventoRepository eventoRepository,
                         CategoriaRepository categoriaRepository,
                         MeterRegistry meterRegistry) {
        this.eventoRepository = eventoRepository;
        this.categoriaRepository = categoriaRepository;
        this.eventosCreadosCounter = Counter.builder("eventpulse.eventos.creados.total")
                .description("Total de eventos creados exitosamente en EventPulse API")
                .register(meterRegistry);
    }

    public EventoResponseDTO crearEvento(EventoRequestDTO dto) {
        logger.info("Iniciando creación de evento: {}", dto.nombre());
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> {
                    logger.warn("Categoría no encontrada con ID: {}", dto.categoriaId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada");
                });

        Evento nuevo = new Evento(null, dto.nombre(), categoria, dto.fecha(), dto.capacidadMaxima(), dto.precioEntrada(), dto.latitud(), dto.longitud());
        Evento guardado = eventoRepository.save(nuevo);
        eventosCreadosCounter.increment();
        logger.info("Evento creado con éxito con ID: {}", guardado.getId());
        return mapearADTO(guardado);
    }

    public List<EventoResponseDTO> listarTodos(String categoria) {
        List<Evento> eventos = (categoria != null && !categoria.isBlank())
                ? eventoRepository.findByCategoriaNombreIgnoreCase(categoria)
                : eventoRepository.findAll();
        return eventos.stream().map(this::mapearADTO).toList();
    }

    public Optional<EventoResponseDTO> buscarPorId(Long id) {
        return eventoRepository.findById(id).map(this::mapearADTO);
    }

    public Optional<EventoResponseDTO> actualizarEvento(Long id, EventoRequestDTO dto) {
        return eventoRepository.findById(id).map(existente -> {
            Categoria cat = categoriaRepository.findById(dto.categoriaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
            existente.setNombre(dto.nombre());
            existente.setCategoria(cat);
            existente.setFecha(dto.fecha());
            existente.setCapacidadMaxima(dto.capacidadMaxima());
            existente.setPrecioEntrada(dto.precioEntrada());
            existente.setLatitud(dto.latitud());
            existente.setLongitud(dto.longitud());
            return mapearADTO(eventoRepository.save(existente));
        });
    }

    public boolean eliminarEvento(Long id) {
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
            logger.info("Evento con ID {} eliminado con éxito", id);
            return true;
        }
        logger.warn("Intento de eliminar evento inexistente con ID {}", id);
        return false;
    }

    private EventoResponseDTO mapearADTO(Evento e) {
        return new EventoResponseDTO(
                e.getId(),
                e.getNombre(),
                e.getCategoria().getNombre(),
                e.getFecha(),
                e.getCapacidadMaxima(),
                e.getPrecioEntrada(),
                e.getLatitud(),
                e.getLongitud()
        );
    }
}