# ✅ Cosas Hechas

## 📋 Examen Mercadolibre - Estado de Implementación

### ✅ Nivel 1: Función isMutant
- ✅ Método `boolean isMutant(String[] dna)` implementado completamente
- ✅ Validación completa del input:
  - ✅ Verifica que el array no sea null o vacío
  - ✅ Valida que sea matriz cuadrada NxN
  - ✅ Valida que solo contenga caracteres A, T, C, G
  - ✅ Valida tamaño mínimo 4x4
- ✅ Búsqueda en 4 direcciones:
  - ✅ Horizontal (→)
  - ✅ Vertical (↓)
  - ✅ Diagonal descendente (↘)
  - ✅ Diagonal ascendente (↗)
- ✅ Algoritmo optimizado:
  - ✅ Early termination (retorna true cuando encuentra más de 1 secuencia)
  - ✅ Conversión eficiente a `char[][]` para acceso O(1)
  - ✅ Single pass con boundary checking
  - ✅ Sin estructuras auxiliares innecesarias

### ✅ Nivel 2: API REST
- ✅ Endpoint `POST /mutant/` implementado:
  - ✅ Recibe JSON con formato: `{"dna":["ATGCGA","CAGTGC",...]}`
  - ✅ Retorna `HTTP 200 OK` si es mutante
  - ✅ Retorna `HTTP 403 Forbidden` si no es mutante
  - ✅ Retorna `HTTP 400 Bad Request` para DNA inválido
- ✅ Spring Boot configurado y funcionando
- ⚠️ **Pendiente:** Despliegue en Render (cloud computing libre)

### ✅ Nivel 3: Persistencia y Estadísticas
- ✅ Base de datos H2 configurada y funcionando:
  - ✅ Entidad `DnaRecord` creada
  - ✅ Solo 1 registro por ADN (usando hash SHA-256 único)
  - ✅ Repository con métodos `findByDnaHash()` y `countByIsMutant()`
- ✅ Endpoint `GET /stats` implementado:
  - ✅ Retorna JSON con formato: `{"count_mutant_dna":40, "count_human_dna":100, "ratio":0.4}`
  - ✅ Calcula ratio correctamente (mutantes/humanos)
  - ✅ Maneja caso especial cuando no hay humanos
- ✅ Tests implementados:
  - ✅ Tests unitarios para `isMutant()` (19 tests)
  - ✅ Tests de integración para endpoints API (8 tests)
  - ✅ Tests para servicios (11 tests)
  - ✅ **Code coverage verificado: 87%** (cumple requisito > 80%)
    - ✅ Cobertura Total: **87%** (requisito: >80%)
    - ✅ Controller: 100%
    - ✅ Service: 94%
    - ✅ Validation: 95%
    - ✅ DTO: 100%
    - ⚠️ Exception: 38% (mejorable, pero no crítico)
- ❌ **Pendiente:** Diagrama de Secuencia

### 📝 Documentación
- ⚠️ README básico creado
- ⚠️ **Pendiente:** Instrucciones detalladas de cómo ejecutar el programa/API
- ⚠️ **Pendiente:** URL de la API (después del despliegue en Render)

---

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
  - Métodos requeridos: `message()`, `groups()`, `payload()` (corregido para Bean Validation)
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

### MutantDetector - ✅ COMPLETO (Algoritmo de Detección) - RÚBRICAS 1.1-1.4 (35 pts)
- ✅ Método `isMutant(String[] dna)` implementado completamente
- ✅ **Validación completa** (RÚBRICAS 1.1):
  - ✅ Valida que `dna` no sea `null` o vacío
  - ✅ Valida tamaño mínimo 4x4
  - ✅ Valida matriz cuadrada NxN
  - ✅ Valida caracteres válidos (A, T, C, G) usando `Set.of()` para O(1)
- ✅ **Conversión eficiente** (RÚBRICAS 1.3 - +2 pts bonus):
  - ✅ Conversión `String[]` a `char[][]` con `toCharArray()`
  - ✅ Acceso O(1) a la matriz
- ✅ **Búsqueda en 4 direcciones** (RÚBRICAS 1.1):
  - ✅ Horizontal (→) con `checkHorizontal()`
  - ✅ Vertical (↓) con `checkVertical()`
  - ✅ Diagonal descendente (↘) con `checkDiagonalDescending()`
  - ✅ Diagonal ascendente (↗) con `checkDiagonalAscending()`
- ✅ **Early Termination** (RÚBRICAS 1.4 - 2.4 pts):
  - ✅ `if (sequenceCount > 1) return true;` después de cada incremento
- ✅ **Single Pass** (RÚBRICAS 1.4 - 2.0 pts):
  - ✅ Solo 2 loops anidados (row, col)
- ✅ **Boundary Checking** (RÚBRICAS 1.4 - 1.6 pts):
  - ✅ Verifica límites antes de cada búsqueda
- ✅ **Direct Comparison** (RÚBRICAS 1.4 - 1.2 pts):
  - ✅ Comparación directa sin loops en métodos auxiliares
- ✅ **Validation Set O(1)** (RÚBRICAS 1.4 - 0.8 pts):
  - ✅ `Set.of('A','T','C','G')` para validación eficiente
- ✅ **Sin estructuras auxiliares** (RÚBRICAS 1.3 - 3.0 pts):
  - ✅ No usa `ArrayList`, `HashMap`, `List`, `Set`, `Map` dentro de `isMutant()`
  - ✅ Solo variables primitivas y `char[][]`

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
- ✅ Clases de test creadas y completas:
  - `MutantControllerTest` (✅ 8/8 tests implementado - COMPLETO)
  - `MutantDetectorTest` (✅ 19/17 tests implementado - COMPLETO)
  - `MutantServiceTest` (✅ 5/5 tests implementado - COMPLETO)
  - `StatsServiceTest` (✅ 6/6 tests implementado - COMPLETO)

## Tests Implementados

### MutantServiceTest (✅ 5/5 tests - COMPLETO)
**Tests unitarios con mocks:**
- ✅ `testAnalyzeDna_NewDna_ShouldCalculateHashAnalyzeAndSave()` - DNA nuevo (calcula hash, analiza, guarda)
- ✅ `testAnalyzeDna_ExistingDna_ShouldReturnCached()` - DNA existente (retorna cacheado)
- ✅ `testAnalyzeDna_NewDna_ShouldSaveWithCreatedAt()` - Verifica que se guarda con createdAt
- ✅ `testAnalyzeDna_NewDna_ShouldCalculateHashCorrectly()` - Verifica cálculo correcto del hash
- ✅ `testAnalyzeDna_ShouldHandleExceptionsCorrectly()` - Manejo de excepciones

### StatsServiceTest (✅ 6/6 tests - COMPLETO)
**Tests unitarios con mocks:**
- ✅ `testGetStats_NormalRatio_ShouldCalculateCorrectly()` - Ratio normal (mutantes/humanos)
- ✅ `testGetStats_NoHumans_ShouldReturnOnlyMutants()` - Sin humanos (caso especial)
- ✅ `testGetStats_NoMutants_ShouldReturnZeroRatio()` - Sin mutantes
- ✅ `testGetStats_NoRecords_ShouldReturnZeros()` - Sin registros
- ✅ `testGetStats_RatioCalculation_ShouldBeCorrect()` - Verificar cálculo correcto del ratio
- ✅ `testGetStats_LargeValues_ShouldCalculateCorrectly()` - Ratio con valores grandes

### MutantControllerTest (✅ 8/8 tests - COMPLETO)
**Tests de integración:**
- ✅ `testCheckMutant_WhenMutant_ShouldReturn200()` - POST /mutant con mutante → 200 OK (1.5 pts)
- ✅ `testCheckMutant_WhenHuman_ShouldReturn403()` - POST /mutant con humano → 403 Forbidden (1.5 pts)
- ✅ `testCheckMutant_WhenInvalidDna_ShouldReturn400()` - POST /mutant con DNA inválido → 400 Bad Request (1.0 pts)
- ✅ `testCheckMutant_WhenDnaIsNull_ShouldReturn400()` - POST /mutant con DNA null → 400 Bad Request
- ✅ `testCheckMutant_WhenDnaIsEmpty_ShouldReturn400()` - POST /mutant con DNA vacío → 400 Bad Request
- ✅ `testCheckMutant_WhenDnaIsNotSquare_ShouldReturn400()` - POST /mutant con matriz no cuadrada → 400 Bad Request
- ✅ `testGetStats_ShouldReturn200WithCorrectJson()` - GET /stats → 200 OK con JSON correcto (1.0 pts)
- ✅ `testGetStats_WhenNoHumans_ShouldReturn200WithCorrectJson()` - GET /stats sin humanos → 200 OK

### MutantDetectorTest (✅ 19/17 tests - COMPLETO)
**Casos Mutantes (8 tests):**
- ✅ `testMutantWithHorizontalAndDiagonalSequences()` - Horizontal + Diagonal
- ✅ `testMutantWithVerticalSequences()` - Secuencias verticales
- ✅ `testMutantWithMultipleHorizontalSequences()` - Múltiples horizontales
- ✅ `testMutantWithBothDiagonals()` - Diagonales ascendentes y descendentes
- ✅ `testMutantWithLargeDna()` - Matriz grande 10x10
- ✅ `testMutantAllSameCharacter()` - Todos los caracteres iguales
- ✅ `testMutantDiagonalInCorner()` - Diagonal en esquina
- ✅ `testHorizontalMutant()` - Cumple patrón requerido RÚBRICAS 3.3
- ✅ `testDiagonalMutant()` - Cumple patrón requerido RÚBRICAS 3.3

**Casos Humanos (3 tests):**
- ✅ `testNotMutantWithOnlyOneSequence()` - Solo 1 secuencia (RÚBRICAS 3.3)
- ✅ `testNotMutantWithNoSequences()` - Sin secuencias (RÚBRICAS 3.3)
- ✅ `testNotMutantSmallDna()` - Matriz 4x4 sin secuencias

**Validaciones (6 tests):**
- ✅ `testNotMutantWithNullDna()` - DNA null (RÚBRICAS 3.3)
- ✅ `testNotMutantWithEmptyDna()` - Array vacío (RÚBRICAS 3.3)
- ✅ `testNotMutantWithNonSquareDna()` - Matriz no cuadrada
- ✅ `testNotMutantWithInvalidCharacters()` - Caracteres inválidos (RÚBRICAS 3.3)
- ✅ `testNotMutantWithNullRow()` - Fila null
- ✅ `testNotMutantWithTooSmallDna()` - Matriz muy pequeña

**Edge Cases (2 tests):**
- ✅ `testNotMutantWithSequenceLongerThanFour()` - Secuencia de longitud 5

## Code Coverage (Cobertura de Código)

### ✅ Cobertura Total: 87% (Cumple requisito > 80% del Examen Mercadolibre)

**Desglose por paquete:**
- ✅ **Controller:** 100% (22 instrucciones, 0 missed)
- ✅ **Service:** 94% (543 instrucciones, 33 missed)
- ✅ **Validation:** 95% (93 instrucciones, 2 missed)
- ✅ **DTO:** 100% (18 instrucciones, 0 missed)
- ⚠️ **Exception:** 38% (37 instrucciones, 60 missed) - Mejorable pero no crítico
- ⚠️ **Main:** 37% (5 instrucciones, 2 missed) - No crítico (clase de inicio)

**Métricas adicionales:**
- ✅ Cobertura de Branches: 90% (10 missed de 108)
- ✅ Cobertura de Líneas: 84% (26 missed de 160)
- ✅ Métodos cubiertos: 75% (7 missed de 28)
- ✅ Clases cubiertas: 90% (1 missed de 10)

**Comando para generar reporte:**
```bash
./gradlew test jacocoTestReport
# Ver reporte en: build/reports/jacoco/test/html/index.html
```

**Nota:** El requisito del examen es >80% de cobertura total, que está cumplido con 87%. El paquete de excepciones tiene baja cobertura (38%) pero esto no afecta el cumplimiento del requisito. Si se desea mejorar, se pueden agregar tests para los métodos del `GlobalExceptionHandler` que no están siendo probados directamente.
