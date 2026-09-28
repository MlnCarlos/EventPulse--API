package com.uniremington.eventpulse.controller;

import com.uniremington.eventpulse.dto.ClimaResponseDTO;
import com.uniremington.eventpulse.dto.EventoRequestDTO;
import com.uniremington.eventpulse.dto.EventoResponseDTO;
import com.uniremington.eventpulse.service.ClimaService;
import com.uniremington.eventpulse.service.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    private final EventoService eventoService;
    private final ClimaService climaService;

    public EventoController(EventoService eventoService, ClimaService climaService) {
        this.eventoService = eventoService;
        this.climaService = climaService;
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> obtenerEventos(@RequestParam(required = false) String categoria) {
        return ResponseEntity.ok(eventoService.listarTodos(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return eventoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> registrarEvento(@RequestBody EventoRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.crearEvento(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> actualizarEvento(@PathVariable Long id, @RequestBody EventoRequestDTO requestDTO) {
        return eventoService.actualizarEvento(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Long id) {
        return eventoService.eliminarEvento(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Endpoint de integración con la API Externa de Clima
    @GetMapping("/{id}/clima")
    public ResponseEntity<ClimaResponseDTO> consultarClimaEvento(@PathVariable Long id) {
        EventoResponseDTO evento = eventoService.buscarPorId(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado"));
        return ResponseEntity.ok(climaService.consultarPronostico(evento.latitud(), evento.longitud()));
    }
}