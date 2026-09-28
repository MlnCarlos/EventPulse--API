# EventPulse API - Integración, Persistencia MySQL y Observabilidad

API REST desarrollada con Spring Boot para la gestión y monitoreo de eventos culturales y tecnológicos. Este proyecto corresponde a la **Actividad Colaborativa #3** del curso **Lenguaje de Programación III**.

---

## Información del Proyecto

* **Asignatura:** Lenguaje de Programación III
* **Institución:** Corporación Universitaria Remington (Modalidad Virtual)
* **Docente:** Leli Liliana Díaz Izquierdo
* **Estudiante:** Carlos Alfredo Loaiza Molina
* **Contexto de la Solución:** Backend para registro de eventos, control de categorías relacionales, pronóstico climático en tiempo real y telemetría operativa para monitoreo en producción.

---

## Tecnologías Utilizadas

* **Java 17**
* **Spring Boot 4.x** (Spring Web MVC, Spring Data JPA, Spring Boot Actuator)
* **Hibernate** (Mapeo Objeto-Relacional)
* **MySQL 8.x** (Motor de persistencia relacional)
* **Micrometer & Prometheus** (Métricas de observabilidad)
* **Open-Meteo REST API** (Servicio externo meteorológico consumido mediante `RestClient`)
* **Maven** (Gestión de dependencias)

---

## Entidades y Relación

El modelo de datos implementa una relación bidireccional Many-to-One:
* **`Categoria` (`1`):** Define las agrupaciones temáticas (`id`, `nombre`, `descripcion`).
* **`Evento` (`N`):** Contiene la información del evento (`id`, `nombre`, `fecha`, `capacidadMaxima`, `precioEntrada`, `latitud`, `longitud`).
* **Relación:** `@ManyToOne` en la entidad `Evento` mapeando la clave foránea `categoria_id`.

---

## Configuración de MySQL

La aplicación utiliza variables de entorno para proteger las credenciales de acceso:

1. Crear la base de datos en MySQL:
   ```sql
   CREATE DATABASE IF NOT EXISTS eventpulsedb;