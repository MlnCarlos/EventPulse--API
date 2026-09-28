package com.uniremington.eventpulse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public record ClimaResponseDTO(
    double latitude,
    double longitude,
    @JsonProperty("current_weather")
    Map<String, Object> currentWeather
) {}