# API y pruebas en Postman

Usa `Content-Type: application/json`. Todos los recursos están bajo `/api`.

## Pacientes — `http://localhost:8081`

| Método | Ruta | Acción |
|---|---|---|
| POST | `/api/pacientes` | Registrar |
| GET | `/api/pacientes` | Listar |
| GET | `/api/pacientes/{id}` | Buscar |
| PUT | `/api/pacientes/{id}` | Actualizar |
| DELETE | `/api/pacientes/{id}` | Eliminar |

```json
{
  "dni": "12345678",
  "nombres": "Ana",
  "apellidos": "Pérez Gómez",
  "fechaNacimiento": "1995-03-12",
  "sexo": "F",
  "telefono": "987654321",
  "direccion": "Av. Javier Prado 123, Lima",
  "correo": "ana@example.com"
}
```

## Médicos y especialidades — `http://localhost:8082`

| Método | Ruta | Acción |
|---|---|---|
| POST | `/api/especialidades` | Registrar especialidad |
| GET | `/api/especialidades` | Listar especialidades |
| GET/PUT/DELETE | `/api/especialidades/{id}` | Consultar, actualizar o eliminar |
| POST | `/api/medicos` | Registrar médico |
| GET | `/api/medicos` | Listar médicos |
| GET/PUT/DELETE | `/api/medicos/{id}` | Consultar, actualizar o eliminar |

Registra primero la especialidad y usa su `id` en el médico.

```json
{ "nombre": "Medicina general", "descripcion": "Atención primaria" }
```

```json
{
  "cmp": "CMP-12345",
  "nombres": "Luis",
  "apellidos": "Soto Ramos",
  "telefono": "912345678",
  "correo": "luis.soto@example.com",
  "idEspecialidad": 1
}
```

## Citas — `http://localhost:8083`

| Método | Ruta | Acción |
|---|---|---|
| POST | `/api/citas` | Programar |
| GET | `/api/citas` | Listar |
| GET | `/api/citas/{id}` | Buscar |
| PATCH | `/api/citas/{id}/estado` | Actualizar estado |
| DELETE | `/api/citas/{id}` | Cancelar |

```json
{
  "fecha": "2026-11-20",
  "hora": "09:30:00",
  "idPaciente": 1,
  "idMedico": 1,
  "motivo": "Consulta de control"
}
```

Para actualizar estado envía `{"estado":"CONFIRMADA"}`. Estados permitidos: `PROGRAMADA`, `CONFIRMADA`, `ATENDIDA`, `CANCELADA`. El borrado lógico marca la cita como `CANCELADA`. No se permiten citas activas duplicadas para un médico en el mismo horario.

## Respuestas de error

Los errores de validación devuelven HTTP 400 con los campos inválidos. Los recursos inexistentes devuelven HTTP 404, los DNI/CMP duplicados y horarios ocupados HTTP 409. Swagger UI está disponible en cada servicio mediante `/swagger-ui.html`.
