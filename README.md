# EventPulse API - Gestión de Eventos con Persistencia JPA

API REST desarrollada con Spring Boot para la gestión integral de eventos, incorporando persistencia relacional con JPA e Hibernate y operaciones CRUD completas. Este proyecto corresponde a la **Actividad Colaborativa #2** del curso **Lenguaje de Programación III**.

---

## Información del Proyecto

* **Asignatura:** Lenguaje de Programación III
* **Institución:** Corporación Universitaria Remington (Modalidad Virtual)
* **Docente:** Leli Liliana Díaz Izquierdo[cite: 3]
* **Estudiante:** Carlos Alfredo Loaiza Molina[cite: 1, 3]
* **Contexto de la solución:** Plataforma para el registro, consulta, actualización y control de aforo para eventos culturales, tecnológicos o a necesidad.

---

## Tecnologías Utilizadas

* **Java 17**[cite: 4]
* **Spring Boot 4.x** (Spring Web, Spring Data JPA)[cite: 4]
* **Hibernate** (Motor ORM)
* **Base de datos H2** (Motor relacional en memoria con consola interactiva)
* **Maven** (Gestor de dependencias y construcción)[cite: 4]
* **Postman** (Pruebas funcionales de endpoints)

---

## Estructura y Persistencia

El proyecto implementa una arquitectura desacoplada por capas:
* **Entidad (`Evento`):** Mapeada con JPA (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`) con atributos de nombre, categoría, fecha, capacidad máxima y precio[cite: 3].
* **Repositorio (`EventoRepository`):** Extiende de `JpaRepository` e implementa una consulta derivada personalizada (`findByCategoriaIgnoreCase`).
* **Capa DTO:** Implementada con `Java Records` (`EventoRequestDTO` y `EventoResponseDTO`) para asegurar inmutabilidad y separar el contrato de transporte del modelo de dominio[cite: 6, 7].
* **Controlador (`EventoController`):** Expone las rutas REST gestionando los códigos de respuesta semánticos (`200 OK`, `201 Created`, `204 No Content`, `404 Not Found`).

---

## Instrucciones para Ejecutar el Proyecto

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/tu-usuario/eventpulse-api.git](https://github.com/tu-usuario/eventpulse-api.git)
   cd eventpulse-api
   ./mvnw spring-boot:run