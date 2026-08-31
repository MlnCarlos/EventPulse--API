package com.uniremington.eventpulse.controller;

import com.uniremington.eventpulse.dto.EventoRequestDTO;
import com.uniremington.eventpulse.dto.EventoResponseDTO;
import com.uniremington.eventpulse.service.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> obtenerEventos(
            @RequestParam(required = false) String categoria) {
        List<EventoResponseDTO> eventos = eventoService.listarTodos(categoria);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return eventoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> registrarEvento(@RequestBody EventoRequestDTO requestDTO) {
        EventoResponseDTO creado = eventoService.crearEvento(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}