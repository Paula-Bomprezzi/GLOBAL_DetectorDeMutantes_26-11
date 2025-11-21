# ✅ Cosas Hechas

## Estructura del Proyecto
- ✅ Estructura de paquetes creada (controller, service, repository, dto, entity, exception, validation, config)
- ✅ Clases base creadas (aunque algunas pendientes de implementación)
- ✅ Configuración de Spring Boot 3.5.7
- ✅ Java 21 configurado

## Configuración
- ✅ `application.properties` configurado con H2 Database
- ✅ H2 Console habilitada en `/h2-console`
- ✅ Logging configurado
- ✅ JPA/Hibernate configurado con `create-drop`
- ✅ `build.gradle` con todas las dependencias necesarias:
  - Spring Boot Web, Data JPA, Validation
  - SpringDoc OpenAPI (Swagger)
  - Lombok
  - H2 Database
  - JUnit 5 y JaCoCo para testing
  - ModelMapper (para conversión Entidad ↔ DTO)

## Base de Datos
- ✅ Entidad `DnaRecord` creada con:
  - `id` (PK, auto-increment)
  - `dnaHash` (VARCHAR 64, unique, nullable=false)
  - `isMutant` (BOOLEAN, nullable=false)
  - `createdAt` (TIMESTAMP)
- ✅ **Índices agregados** (RÚBRICAS 5.3):
  - `idx_dna_hash` en columna `dnaHash`
  - `idx_is_mutant` en columna `isMutant`
- ✅ Repository `DnaRecordRepository` creado:
  - `extends JpaRepository<DnaRecord, Long>` ✅
  - Métodos: `findByDnaHash(String)` ✅
  - Método: `countByIsMutant(boolean)` ✅
- ✅ Anotaciones JPA aplicadas (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`)

## Lombok (RÚBRICAS 2.3)
- ✅ `DnaRecord` con anotaciones Lombok:
  - `@AllArgsConstructor`
  - `@NoArgsConstructor`
  - `@Setter`
  - `@Getter`
  - `@ToString`
  - `@Builder`

## DTOs (Data Transfer Objects)
- ✅ **`DnaRequest` COMPLETO** (RÚBRICAS 2.5 y 4.3):
  - `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor` (Lombok)
  - `@NotNull`, `@NotEmpty` (Bean Validation)
  - `@ValidDnaSequence` (Custom validator)
  - `@Schema` en clase y campo `dna` con ejemplo y descripción detallada
  - Campo `dna` de tipo `String[]`
- ✅ **`ErrorResponse` COMPLETO**:
  - `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor` (Lombok)
  - `@Schema` en clase y todos los campos (`timestamp`, `status`, `error`, `message`, `path`)
  - Constructor que inicializa `timestamp` automáticamente
- ✅ **`StatsResponse` COMPLETO** (RÚBRICAS 4.3):
  - `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder` (Lombok)
  - `@Schema` en clase y todos los campos con descripciones
  - `@JsonProperty` para formato JSON con guiones bajos (`count_mutant_dna`, `count_human_dna`)
  - Campos: `countMutantDna`, `countHumanDna`, `ratio`

## Validaciones
- ✅ Anotación `@ValidDnaSequence` creada con:
  - `@Constraint(validatedBy = ValidDnaSequenceValidator.class)`
  - `@Target` y `@Retention` configurados
- ✅ **`ValidDnaSequenceValidator` COMPLETO** (RÚBRICAS 2.5 - 1.5 pts):
  - ✅ Valida que `dna` no sea `null` o vacío
  - ✅ Valida que sea matriz cuadrada NxN
  - ✅ Valida tamaño mínimo 4x4
  - ✅ Valida que solo contenga caracteres A, T, C, G (usando `Set.of()` para O(1))
  - ✅ Valida que ninguna fila sea `null`

## Excepciones
- ✅ `DnaHashCalculationException` creada:
  - Extiende `RuntimeException`
  - Constructores con mensaje y causa
- ✅ **`GlobalExceptionHandler` COMPLETO** (RÚBRICAS 2.4 - 2.0 pts):
  - ✅ Anotado con `@RestControllerAdvice` (2.0 pts)
  - ✅ Maneja `MethodArgumentNotValidException` → 400 Bad Request (para errores de `@Valid` en DTOs)
  - ✅ Maneja `ConstraintViolationException` → 400 Bad Request (para errores de validación de parámetros)
  - ✅ Maneja `DnaHashCalculationException` → 500 Internal Server Error
  - ✅ Retorna `ErrorResponse` con formato JSON según CONSIGNAS
  - ✅ Extrae mensajes de error de validación y los formatea correctamente

## Servicios - Implementación

### MutantService - ✅ COMPLETO
- ✅ Método `calculateDnaHash()` implementado con SHA-256 completo
- ✅ Método `analyzeDna()` **COMPLETO** con toda la lógica:
  - ✅ Calcula hash SHA-256
  - ✅ Busca en BD con `findByDnaHash()`
  - ✅ Retorna cacheado si existe
  - ✅ Llama a `MutantDetector.isMutant()` si no existe
  - ✅ Crea `DnaRecord` con `builder()` incluyendo `createdAt`
  - ✅ Guarda en BD con `repository.save()`
  - ✅ Retorna resultado
- ✅ **`@RequiredArgsConstructor` + campos `final`** implementado (RÚBRICAS 2.2 - 2.0 pts) ✅
- ⚠️ Nota: Tiene `System.out.println()` que podría reemplazarse con logging (opcional)

### MutantDetector - ⚠️ Pendiente (Algoritmo)
- ✅ Método `isMutant(String[] dna)` creado y público
- ⚠️ Retorna siempre `true` (lógica del algoritmo pendiente - DEJAR PARA EL FINAL)

### StatsService - ✅ COMPLETO
- ✅ Inyectado `DnaRecordRepository` con `@RequiredArgsConstructor` + `final`
- ✅ Método `getStats()` que retorna `StatsResponse`
- ✅ Cuenta mutantes: `repository.countByIsMutant(true)`
- ✅ Cuenta humanos: `repository.countByIsMutant(false)`
- ✅ Calcula ratio: `(double) countMutant / countHuman`
- ✅ Maneja caso especial: si `countHuman == 0`, retorna solo `countMutantDna` (sin ratio)
- ✅ Crea y retorna `StatsResponse` con `builder()`
- ⚠️ Nota: Tiene `System.out.println()` que podría reemplazarse con logging (opcional)

## Controller - API REST

### MutantController - ✅ POST /mutant COMPLETO
- ✅ **POST /mutant COMPLETO** (RÚBRICAS 4.1 - 4 pts):
  - ✅ Inyectado `MutantService` con `@RequiredArgsConstructor` + `final`
  - ✅ Método `checkMutant(@RequestBody @Valid DnaRequest request)` con `@PostMapping("/mutant")`
  - ✅ Llama a `MutantService.analyzeDna(request.getDna())`
  - ✅ Retorna `200 OK` si es mutante (1.5 pts)
  - ✅ Retorna `403 Forbidden` si NO es mutante (1.5 pts)
  - ✅ `400 Bad Request` manejado por validaciones (1.0 pts)
  - ✅ Documentado con Swagger: `@Tag`, `@Operation`, `@ApiResponse` (RÚBRICAS 4.2)

- ✅ **GET /stats COMPLETO** (RÚBRICAS 4.1 - 1 pt):
  - ✅ `StatsService` inyectado con `@RequiredArgsConstructor` + `final`
  - ✅ Método `getStats()` con `@GetMapping("/stats")` implementado
  - ✅ Llama a `statsService.getStats()` y retorna el resultado
  - ✅ Retorna `200 OK` con `StatsResponse` JSON (0.5 pts)
  - ✅ JSON correcto con campos: `count_mutant_dna`, `count_human_dna`, `ratio` (0.5 pts)
  - ✅ Documentado con Swagger: `@Operation`, `@ApiResponse`

## Swagger (Documentación API) - RÚBRICAS 4.2 (4.0 pts)
- ✅ **`SwaggerConfig` COMPLETO**:
  - ✅ Anotado con `@Configuration`
  - ✅ `@Bean` de `OpenAPI` configurado
  - ✅ Información de la API (título, versión, descripción, contacto)
  - ✅ Swagger UI accesible en `/swagger-ui.html` (1.0 pts)
  - ✅ OpenAPI JSON en `/api-docs` (1.0 pts)
- ✅ **Anotaciones en Controller** (1.0 pts):
  - ✅ `@Tag` en `MutantController` con descripción
  - ✅ `@Operation` en ambos endpoints (`POST /mutant` y `GET /stats`)
  - ✅ `@ApiResponse` (3+) en `POST /mutant` (200, 403, 400)
  - ✅ `@ApiResponse` en `GET /stats` (200)
- ✅ **`@Schema` en DTOs** (1.0 pts):
  - ✅ `@Schema` en clase `DnaRequest` + campo `dna` con ejemplo
  - ✅ `@Schema` en clase `StatsResponse` + todos los campos
  - ✅ `@Schema` en clase `ErrorResponse` + todos los campos

## Configuración Adicional
- ✅ `ModelMapperConfig` creado con `@Bean` para ModelMapper
- ✅ `MapperService` creado (servicio para conversiones)

## Docker
- ✅ `Dockerfile` creado con multi-stage build

## Estructura de Tests
- ✅ Clases de test creadas (vacías):
  - `MutantControllerTest`
  - `MutantDetectorTest`
  - `MutantServiceTest`
  - `StatsServiceTest`
