# Sistema de citas médicas con microservicios

Implementación de la actividad del laboratorio para la clínica Salud Integral. El repositorio local estaba vacío; esta solución separa pacientes, médicos/especialidades y citas en tres aplicaciones Spring Boot independientes.

## Requisitos

- IntelliJ IDEA
- Java 21 o superior
- Maven 3.9+
- MySQL 8+

Cada servicio usa su propia base de datos. Los servicios almacenan identificadores de entidades de otros servicios sin crear claves foráneas entre bases de datos, como corresponde a esta separación.

## Preparación de MySQL

Ejecuta `sql/create-databases.sql`. Configura `DB_USERNAME` y `DB_PASSWORD` como variables de entorno si tus credenciales no son `root` y una contraseña vacía.

## Abrir y ejecutar en IntelliJ IDEA

1. Abre la carpeta raíz del repositorio en IntelliJ IDEA.
2. Espera a que Maven importe los tres proyectos ubicados en `microservicios`.
3. Comprueba que el Project SDK sea Java 21 o superior.
4. Ejecuta la clase `PacienteApplication`, `MedicoApplication` y `CitaApplication` (en terminales separadas).
5. Prueba las rutas de `docs/API.md` con Postman. Cada servicio también publica Swagger UI en `/swagger-ui.html`.

Puertos: paciente `8081`, médico `8082`, citas `8083`.

## Estructura

```text
microservicios/
  paciente-service/
  medico-service/   # especialidades y médicos
  cita-service/
sql/create-databases.sql
docs/API.md
```

Las entidades propias usan JPA y cada servicio actualiza su esquema con `ddl-auto=update`. Las citas validan su estado y evitan duplicar una cita activa para el mismo médico en fecha y hora. Como no hay una capa de descubrimiento o gateway en el alcance del laboratorio, el servicio de citas registra los identificadores de paciente y médico y no hace llamadas remotas.

## Perfiles

El perfil predeterminado es `dev`. Para producción inicia cada aplicación con `SPRING_PROFILES_ACTIVE=prod` y configura `DB_URL`, `DB_USERNAME` y `DB_PASSWORD`. No guardes credenciales reales en el repositorio.

## Conclusiones del laboratorio

1. Separar el sistema por capacidades de negocio permite desplegar y mantener pacientes, médicos y citas de forma independiente.
2. Cada servicio administra su propio modelo y base de datos, evitando dependencias de esquema entre servicios.
3. Las API REST facilitan que clientes como Postman consuman las operaciones CRUD mediante contratos HTTP claros.
4. La validación de datos y el control de estados previenen registros incompletos y transiciones inválidas en las citas.
5. La configuración por perfiles permite cambiar entre desarrollo y producción sin incorporar secretos al código fuente.
