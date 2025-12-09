<p align="center">
  <img src="https://raw.githubusercontent.com/CODIGOAFRX/petcare-manager/master/src/main/resources/images/PetcareLogo2.png" width="220" />
</p>

<h1 align="center">🐾 PetCare Manager</h1>
<p align="center">Backend en Java + Spring Boot para la gestión completa de mascotas, visitas veterinarias, medicaciones y eventos.</p>

---

## 🚀 Tecnologías utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA + Hibernate**
- **PostgreSQL**
- **Lombok**
- **Bean Validation**
- **Tareas programadas con @Scheduled**
- **Arquitectura en capas (Controller, Service, Repository, Model)**

---

## 🐶 Funcionalidades principales

### ✔️ Gestión de Mascotas (CRUD)
- Crear, actualizar, listar y eliminar mascotas.
- Validaciones de datos (peso, fecha de nacimiento, especie, etc.).

### ✔️ Visitas Veterinarias
- Registrar visitas por mascota.
- Motivo, clínica, fecha, notas, etc.

### ✔️ Medicaciones
- Registrar medicaciones con dosis, frecuencia y próxima toma.
- Consultar medicaciones activas de una mascota.
- Detectar medicaciones atrasadas.
- Detectar medicaciones próximas (en X minutos).

### ✔️ Eventos
- Registrar eventos varios: paseos, baños, comidas especiales…

### ✔️ Timeline unificado
Endpoint:

GET /api/pets/{id}/timeline
Combina en orden cronológico:

- Visitas veterinarias  
- Eventos  
- Medicaciones (usando fecha de próxima dosis)

---

## ⏰ Programación automática de avisos

Se ejecuta periódicamente una tarea con `@Scheduled` que:

- Detecta medicaciones atrasadas  
- Detecta medicaciones próximas  
- Imprime avisos por logs (extensible a emails, notificaciones, etc.)

Configuración desde `application.properties`:

```properties
petcare.medication.check-interval-ms=300000
petcare.medication.upcoming-window-minutes=60

📂 Estructura del proyecto
src/main/java/com/pedro/petcare
 ├── controller
 ├── service
 │    └── impl
 ├── repository
 ├── model
 ├── scheduler
 └── exception

🧪 Endpoints principales
Mascotas
GET /api/pets
GET /api/pets/{id}
POST /api/pets
PUT /api/pets/{id}
DELETE /api/pets/{id}

Medicaciones
GET /api/pets/{id}/medications
POST /api/pets/{id}/medications
PUT /api/medications/{id}
DELETE /api/medications/{id}
GET /api/medications/overdue
GET /api/medications/upcoming?minutes=60

Eventos
POST /api/pets/{id}/events

Timeline
GET /api/pets/{id}/timeline

🧰 Base de datos

Configurable desde application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/petcare
spring.datasource.username=postgres
spring.datasource.password=tu_password

📌 Estado del proyecto

✨ Proyecto funcional y listo para ser usado como:

Proyecto de portfolio

Base para una aplicación real

Demostración técnica en entrevistas

🧑‍💻 Autor

Pedro Jesús Gómez Pérez (CODIGOAFRX)
👉 https://github.com/CODIGOAFRX
