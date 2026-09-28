package com.uniremington.eventpulse.service;

import com.uniremington.eventpulse.dto.ClimaResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class ClimaService {

    private static final Logger logger = LoggerFactory.getLogger(ClimaService.class);
    private final RestClient restClient;

    public ClimaService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.open-meteo.com/v1")
                .build();
    }

    public ClimaResponseDTO consultarPronostico(Double lat, Double lon) {
        if (lat == null || lon == null) {
            logger.warn("Se intentó consultar el clima con coordenadas nulas");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las coordenadas latitud y longitud son obligatorias");
        }

        logger.info("Consultando API externa Open-Meteo para lat={}, lon={}", lat, lon);

        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/forecast")
                            .queryParam("latitude", lat)
                            .queryParam("longitude", lon)
                            .queryParam("current_weather", "true")
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        logger.error("Error 4xx en API externa Open-Meteo: {}", res.getStatusCode());
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coordenadas geográficas inválidas para el servicio de clima");
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        logger.error("Error 5xx en API externa Open-Meteo: {}", res.getStatusCode());
                        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "El servicio externo de clima no está disponible");
                    })
                    .body(ClimaResponseDTO.class);
        } catch (Exception ex) {
            logger.error("Fallo de comunicación al consultar el servicio externo: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error de comunicación con el servicio externo de clima");
        }
    }
}