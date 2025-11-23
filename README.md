# Detector de Mutantes - API REST
## Paula Bomprezzi - 48870

![X-Men Logo](images/X-Men-logo.png)

API REST desarrollada con Spring Boot para detectar si una secuencia de ADN pertenece a un mutante. Un humano es mutante si tiene más de una secuencia de 4 letras iguales consecutivas en horizontal, vertical o diagonal.

## La aplicación se encuentra hosteada en: https://global-detectordemutantes-26-11-1.onrender.com

## Tecnologías Utilizadas

- **Java 21** - Lenguaje de programación
- **Spring Boot 3.5.7** - Framework para aplicaciones Java
- **Spring Data JPA** - Persistencia de datos
- **H2 Database** - Base de datos en memoria
- **SpringDoc OpenAPI (Swagger)** - Documentación de la API
- **Lombok** - Reducción de código boilerplate
- **JUnit 5** - Framework de testing
- **JaCoCo** - Cobertura de código
- **Gradle** - Herramienta de construcción
- **Caffeine** - Caché en memoria
- **SLF4J** - Logging estructurado

## Requisitos del Proyecto

### Algoritmo de Detección
- Detecta secuencias de 4 letras iguales en 4 direcciones: horizontal, vertical, diagonal descendente y diagonal ascendente
- Un mutante tiene más de una secuencia de 4 letras iguales
- Validaciones: matriz cuadrada NxN, mínimo 4x4, máximo 1000x1000, solo caracteres A, T, C, G
- Optimizaciones: early termination, single pass, conversión eficiente a char[][]

### API REST

#### POST /mutant
Verifica si un ADN es mutante.

**Request:**
```json
{
  "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
}
```

**Response:**
- `200 OK` - Es mutante
- `403 Forbidden` - No es mutante
- `400 Bad Request` - DNA inválido

#### GET /stats
Obtiene estadísticas de los ADNs analizados.

**Response:**
```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

#### GET /health
Endpoint de salud para monitoreo.

**Response:**
```json
{
  "status": "UP",
  "timestamp": "2025-11-22T20:30:00Z"
}
```

#### DELETE /mutant/{hash}
Elimina un registro de ADN por su hash.

**Response:**
- `200 OK` - Eliminado correctamente
- `404 Not Found` - Registro no encontrado

### Persistencia
- Base de datos H2 en memoria
- Entidad `DnaRecord` con campos: id, dnaHash, isMutant, createdAt
- Deduplicación usando hash SHA-256 (solo 1 registro por ADN)
- Repository con métodos: `findByDnaHash()`, `countByIsMutant()`

### Testing
- 42 tests totales (38 funcionales + 4 de performance)
- Cobertura de código: 87% (requisito: >80%)
- Tests de performance para verificar rendimiento del algoritmo

### Arquitectura
- Arquitectura en capas: controller, service, repository, dto, entity, config
- Dependency Injection con `@RequiredArgsConstructor`
- DTOs para Request/Response
- Manejo global de excepciones con `@RestControllerAdvice`
- Validaciones con Bean Validation y validadores custom
- **Diagrama de secuencias** disponible en `zDIAGRAMA DE SECUENCIA/DiagramadeSecuencia.puml` (PlantUML)

## Cómo Ejecutar

### Requisitos Previos
- Java 21 o superior
- Gradle 7.x o superior

### Compilar y Ejecutar
```bash
# Compilar el proyecto
./gradlew build

# Ejecutar la aplicación
./gradlew bootRun
```

La aplicación estará disponible en `http://localhost:8080`

### Ejecutar Tests
```bash
# Ejecutar todos los tests
./gradlew test

# Ejecutar tests de performance
./gradlew test --tests MutantDetectorTest.testPerformance*

# Generar reporte de cobertura
./gradlew test jacocoTestReport
```

El reporte de cobertura se encuentra en: `build/reports/jacoco/test/html/index.html`

## Documentación de la API

Una vez que la aplicación esté ejecutándose, puedes acceder a:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:mutantesdb`
  - Usuario: `sa`
  - Contraseña: (vacía)

## Estructura del Proyecto

```
src/main/java/com/utn/DetectorDeMutantes/
├── controller/     # Endpoints REST
├── service/        # Lógica de negocio
├── repository/     # Acceso a datos
├── dto/            # Objetos de transferencia
├── entity/         # Entidades JPA
├── validation/     # Validadores custom
├── exception/      # Manejo de excepciones
└── config/         # Configuraciones (Swagger, Cache, etc.)
```

## Características Adicionales

- **Logging estructurado** con SLF4J
- **Caché en memoria** con Caffeine para mejorar rendimiento
- **Procesamiento asíncrono** para mejor escalabilidad
- **Validación de tamaño máximo** (1000x1000) para proteger el servidor
- **Endpoint de salud** para monitoreo

