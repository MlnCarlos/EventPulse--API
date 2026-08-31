package com.uniremington.eventpulse.model;

import java.time.LocalDate;

public class Evento {
    private Long id;
    private String nombre;
    private String categoria;
    private LocalDate fecha;
    private Integer capacidadMaxima;
    private Double precioEntrada;

    public Evento() {}

    public Evento(Long id, String nombre, String categoria, LocalDate fecha, Integer capacidadMaxima, Double precioEntrada) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.fecha = fecha;
        this.capacidadMaxima = capacidadMaxima;
        this.precioEntrada = precioEntrada;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Integer getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(Integer capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }
    public Double getPrecioEntrada() { return precioEntrada; }
    public void setPrecioEntrada(Double precioEntrada) { this.precioEntrada = precioEntrada; }
}