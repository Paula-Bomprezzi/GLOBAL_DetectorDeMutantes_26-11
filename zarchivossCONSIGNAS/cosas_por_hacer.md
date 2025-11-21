# 📋 Cosas Por Hacer

## 🎯 Orden Recomendado de Implementación

**Estrategia:** Dejar el algoritmo `isMutant()` para el final. Primero construir todo el contexto (Swagger, Tests básicos) para tener un sistema funcional que luego solo necesite el algoritmo.

---

## 📚 FASE 1: Swagger (Documentación) - ✅ COMPLETADO

---

## 📚 FASE 4: Swagger (Documentación) - ✅ COMPLETADO

### SwaggerConfig - RÚBRICAS 4.2 (4.0 pts) ✅
**Prioridad: MEDIA** - Documentación de la API  
**Estado:** ✅ COMPLETO  
**Ubicación:** `src/main/java/com/utn/DetectorDeMutantes/config/SwaggerConfig.java`

**Implementación según CONSIGNAS (líneas 984-985) y RÚBRICAS (líneas 293-300):**
- ✅ Anotado con `@Configuration`
- ✅ Creado `@Bean` de `OpenAPI`
- ✅ Configurada información de la API (título, versión, descripción, contacto)
- ✅ Swagger UI accesible en `/swagger-ui.html` (1.0 pts) ✅
- ✅ OpenAPI JSON en `/api-docs` (1.0 pts) ✅
- ✅ Anotaciones en Controller: `@Tag`, `@Operation`, `@ApiResponse` (3+) (1.0 pts) ✅
  - `@Tag` en `MutantController`
  - `@Operation` en `POST /mutant` y `GET /stats`
  - `@ApiResponse` (3 respuestas) en `POST /mutant`
- ✅ `@Schema` en 2+ DTOs (1.0 pts) ✅
  - `@Schema` en `DnaRequest` (clase + campo `dna` con ejemplo)
  - `@Schema` en `StatsResponse` (clase + todos los campos)
  - `@Schema` en `ErrorResponse` (clase + todos los campos)

---

## 🧪 FASE 5: Tests Básicos (Verificar Funcionamiento)

### MutantServiceTest
**Prioridad: MEDIA** - Verificar lógica de negocio  
**Estado:** Clase vacía  
**Ubicación:** `src/test/java/com/utn/DetectorDeMutantes/service/MutantServiceTest.java`

**Implementar tests para (RÚBRICAS 3.2):**
- ⚠️ DNA nuevo (debe calcular hash, analizar, guardar)
- ⚠️ DNA existente (debe retornar cacheado)
- ⚠️ Manejo de excepciones (`DnaHashCalculationException`)
- ⚠️ Verificar que se guarda en BD con `createdAt`
- ⚠️ Verificar que el hash se calcula correctamente
- ⚠️ Usar `@Mock` para `DnaRecordRepository` y `MutantDetector`

### StatsServiceTest
**Prioridad: MEDIA** - Verificar estadísticas  
**Estado:** Clase vacía  
**Ubicación:** `src/test/java/com/utn/DetectorDeMutantes/service/StatsServiceTest.java`

**Implementar tests para (RÚBRICAS 3.2):**
- ⚠️ Ratio normal (mutantes/humanos)
- ⚠️ Sin humanos (caso especial)
- ⚠️ Sin mutantes
- ⚠️ Sin registros
- ⚠️ Verificar cálculo correcto del ratio
- ⚠️ Usar `@Mock` para `DnaRecordRepository`

### MutantControllerTest
**Prioridad: MEDIA** - Verificar endpoints  
**Estado:** Clase vacía  
**Ubicación:** `src/test/java/com/utn/DetectorDeMutantes/controller/MutantControllerTest.java`

**Implementar tests de integración según CONSIGNAS (líneas 1642-1666) y RÚBRICAS (4.1):**
- ⚠️ POST /mutant con mutante → 200 OK (1.5 pts)
- ⚠️ POST /mutant con humano → 403 Forbidden (1.5 pts)
- ⚠️ POST /mutant con DNA inválido → 400 Bad Request (1.0 pts)
- ⚠️ GET /stats → 200 OK con JSON correcto (1.0 pts)
- ⚠️ Verificar formato JSON de respuesta
- ⚠️ Usar `@SpringBootTest` y `@AutoConfigureMockMvc`
- ⚠️ Mockear `MutantService` y `StatsService`

---

## 🔴 FASE 6: Algoritmo (Dejar para el Final)

### MutantDetector (Algoritmo de Detección) - 35 pts
**Prioridad: BAJA** - Lo más complejo, pero solo una parte del sistema  
**Estado:** Método `isMutant()` retorna siempre `true`  
**Ubicación:** `src/main/java/com/utn/DetectorDeMutantes/service/MutantDetector.java`

**Requisitos según RÚBRICAS (líneas 28-144):**

#### 1.1 Correctitud Funcional (10 pts)
- ✅ Método `isMutant(String[] dna)` que retorna `boolean`
- ⚠️ Validar que DNA sea válido (NxN, solo A/T/C/G, mínimo 4x4) - **Nota:** Esta validación ya la hace `ValidDnaSequenceValidator` en el DTO, pero el algoritmo debe poder manejar un input ya validado.
- ⚠️ Convertir `String[]` a `char[][]` para mejor rendimiento
- ⚠️ Buscar secuencias de 4 letras iguales en 4 direcciones:
  - Horizontal (→)
  - Vertical (↓)
  - Diagonal descendente (↘)
  - Diagonal ascendente (↗)
- ⚠️ **Early termination:** `if (sequenceCount > 1) return true;` (CRÍTICO - 2.4 pts)
- ⚠️ Métodos auxiliares: `checkHorizontal()`, `checkVertical()`, `checkDiagonalDescending()`, `checkDiagonalAscending()`

**Implementación según CONSIGNAS (líneas 1148-1244):**
```java
private boolean checkHorizontal(char[][] matrix, int row, int col) {
    final char base = matrix[row][col];
    return matrix[row][col + 1] == base &&
           matrix[row][col + 2] == base &&
           matrix[row][col + 3] == base;
}
```

#### 1.2 Complejidad Temporal - RENDIMIENTO (12 pts)
**Benchmarks requeridos (RÚBRICAS líneas 64-76):**
- 6x6: ≤ 1ms (óptimo) / ≤ 5ms (aceptable)
- 100x100: ≤ 20ms (óptimo) / ≤ 100ms (aceptable)
- 1000x1000: ≤ 500ms (óptimo) / ≤ 5000ms (aceptable)

#### 1.3 Complejidad Espacial (5 pts)
- ❌ **NO usar** `ArrayList`, `HashMap`, `List`, `Set`, `Map` dentro de `isMutant()` (-3 pts si se usa)
- ✅ Usar `toCharArray()` para conversión eficiente (+2 pts bonus)
- ✅ Conversión a `char[][]` para acceso O(1)

#### 1.4 Optimizaciones (8 pts)
**Checklist (RÚBRICAS líneas 107-143):**
- ⚠️ Early Termination (2.4 pts): `if (sequenceCount > 1) return true;`
- ⚠️ Single Pass (2.0 pts): Solo 2 loops anidados (row, col)
- ⚠️ Boundary Checking (1.6 pts): `if (col <= n - SEQUENCE_LENGTH)`
- ⚠️ Direct Comparison (1.2 pts): Comparación directa sin loops
- ⚠️ Validation Set O(1) (0.8 pts): `Set.of('A','T','C','G')` para validación

---

## 🧪 FASE 7: Tests del Algoritmo

### MutantDetectorTest
**Prioridad: BAJA** - Después de implementar el algoritmo  
**Estado:** Clase vacía  
**Ubicación:** `src/test/java/com/utn/DetectorDeMutantes/service/MutantDetectorTest.java`

**Implementar según CONSIGNAS (líneas 1673-1740) y RÚBRICAS (3.2, 3.3):**
- ⚠️ Tests para casos mutantes (horizontal+diagonal, verticales, múltiples horizontales, diagonales, matriz grande)
- ⚠️ Tests para casos humanos (solo 1 secuencia, sin secuencias)
- ⚠️ Tests de validación (null, vacío, no cuadrada, caracteres inválidos, muy pequeña) - **Nota:** Aunque la validación principal está en el DTO, es buena práctica tener tests unitarios para el detector.

**Requisitos según RÚBRICAS (líneas 249-266):**
- **Mínimo: 15 tests** | **Óptimo: 17+ tests** (RÚBRICAS 3.2 - 2.45 pts)
- **Cobertura mínima: 85%** | **Óptimo: 95%+** (RÚBRICAS 3.1 - 3.2 pts)

**Casos requeridos (RÚBRICAS 3.3 - 5 pts):**
1. ⚠️ Mutante - Horizontal (1 pt): `test.*[Hh]orizontal.*[Mm]utant`
2. ⚠️ Mutante - Diagonal (1 pt): `test.*[Dd]iagonal.*[Mm]utant`
3. ⚠️ Humano - Sin secuencias (1 pt): `test.*[Nn]o.*[Ss]equence`
4. ⚠️ Humano - 1 secuencia (1 pt): `test.*[Oo]ne.*[Ss]equence`
5. ⚠️ Validación - Inválido (1 pt): `test.*(Invalid|[Nn]ull|[Ee]mpty)`

---

## 📈 Cobertura Requerida (8 pts según RÚBRICAS 3.1)

**Métricas (RÚBRICAS líneas 225-231):**
- ⚠️ Cobertura Total: ≥90% (óptimo) / ≥70% (aceptable) - (3.2 pts)
- ⚠️ Cobertura Service: ≥96% (óptimo) / ≥85% (aceptable) - (3.2 pts)
- ⚠️ Cobertura Controller: ≥95% (óptimo) / ≥80% (aceptable) - (1.6 pts)

**Comandos de verificación:**
```bash
./gradlew test jacocoTestReport
# Ver: build/reports/jacoco/test/html/index.html
```

---

## 🔵 Ejercicios Opcionales (No otorgan puntos para la evaluación principal)

### Nivel 1: Básico

#### Ejercicio 1: Agregar Logging (Opcional)
**Ubicación:** `MutantDetector.java` y `MutantService.java`  
**Implementación (CONSIGNAS líneas 2427-2441):**
- ⚠️ Agregar `@Slf4j` a la clase
- ⚠️ Reemplazar `System.out.println()` con `log.debug()` o `log.info()`
- ⚠️ Usar `log.debug()` para ver qué direcciones se están chequeando

#### Ejercicio 2: Endpoint de Salud (Opcional)
**Ubicación:** Nuevo Controller o `MutantController.java`  
**Implementación (CONSIGNAS líneas 2444-2451):**
- ⚠️ Crear un endpoint `GET /health`
- ⚠️ Retornar JSON: `{"status": "UP", "timestamp": "..."}`

#### Ejercicio 3: Validación de Tamaño Máximo (Opcional)
**Ubicación:** `ValidDnaSequenceValidator.java` o nueva anotación  
**Implementación (CONSIGNAS línea 2454):**
- ⚠️ Agregar validación para rechazar matrices mayores a 1000x1000

### Nivel 2: Intermedio

#### Ejercicio 4: Endpoint DELETE (Opcional)
**Ubicación:** `MutantController.java`  
**Implementación (CONSIGNAS línea 2460):**
- ⚠️ Crear endpoint `DELETE /mutant/{hash}`
- ⚠️ Buscar registro por hash
- ⚠️ Eliminar si existe, retornar 404 si no existe
- ⚠️ Retornar 200 OK si se eliminó correctamente

#### Ejercicio 5: Filtro de Estadísticas (Opcional)
**Ubicación:** `StatsService.java` y `MutantController.java`  
**Implementación (CONSIGNAS líneas 2463-2467):**
- ⚠️ Modificar `GET /stats` para aceptar parámetros:
  - `startDate` (opcional)
  - `endDate` (opcional)
- ⚠️ Filtrar registros por rango de fechas en `createdAt`
- ⚠️ Ejemplo: `GET /stats?startDate=2025-01-01&endDate=2025-01-07`

#### Ejercicio 6: Rate Limiting (Opcional)
**Ubicación:** Nueva configuración o interceptor  
**Implementación (CONSIGNAS línea 2469):**
- ⚠️ Implementar rate limiting: máximo 10 requests por minuto por IP
- ⚠️ Usar librería como Bucket4j o Spring Cloud Gateway
- ⚠️ Retornar 429 Too Many Requests cuando se exceda el límite

### Nivel 3: Avanzado

#### Ejercicio 7: Caché en Memoria (Opcional)
**Ubicación:** `MutantService.java`  
**Implementación (CONSIGNAS línea 2477):**
- ⚠️ Agregar dependencia `spring-boot-starter-cache` en `build.gradle`
- ⚠️ Habilitar caché con `@EnableCaching` en clase de configuración
- ⚠️ Agregar `@Cacheable(value = "dnaCache", key = "#dna")` al método `analyzeDna()`
- ⚠️ Configurar caché (Caffeine, Redis, o simple)
- ⚠️ **Nota:** Esto es adicional al caché en BD, no reemplazo

#### Ejercicio 8: Async Processing (Opcional)
**Ubicación:** `MutantService.java`  
**Implementación (CONSIGNAS línea 2480):**
- ⚠️ Habilitar async con `@EnableAsync` en configuración
- ⚠️ Convertir `analyzeDna()` en asíncrono usando `@Async`
- ⚠️ Cambiar retorno a `CompletableFuture<Boolean>`
- ⚠️ Ajustar Controller para manejar respuesta asíncrona

#### Ejercicio 9: Migrar a PostgreSQL (Opcional)
**Ubicación:** `application.properties` y `Dockerfile`  
**Implementación (CONSIGNAS línea 2483):**
- ⚠️ Configurar PostgreSQL en Docker Compose
- ⚠️ Cambiar dependencia de H2 a PostgreSQL en `build.gradle`
- ⚠️ Actualizar `application.properties` con conexión a PostgreSQL
- ⚠️ Migrar esquema de BD
- ⚠️ Actualizar `Dockerfile` si es necesario

---

## 📝 Notas de Implementación según RÚBRICAS

### Puntuación Total: 100 puntos
**Aprobación Mínima:** 70 puntos

**Distribución:**
- 🧬 Algoritmo: 35 pts (35%)
- 🏗️ Arquitectura: 25 pts (25%)
- 🧪 Testing: 20 pts (20%)
- 🌐 API REST: 12 pts (12%)
- 💾 Persistencia: 8 pts (8%)

### Comandos de Verificación (RÚBRICAS líneas 472-496)
```bash
# Compilar y verificar build
./gradlew clean build

# Ejecutar todos los tests
./gradlew test

# Generar reporte de cobertura
./gradlew test jacocoTestReport

# Tests específicos
./gradlew test --tests MutantDetectorTest
./gradlew test --tests MutantControllerTest

# Iniciar aplicación
./gradlew bootRun
# Swagger: http://localhost:8080/swagger-ui.html
# H2 Console: http://localhost:8080/h2-console
```

### Checklist Rápido de Entrega (RÚBRICAS líneas 435-468)
- [ ] 17+ tests en `MutantDetectorTest` pasan
- [ ] `if (sequenceCount > 1) return true;` implementado
- [ ] Conversión a `char[][]` para acceso rápido
- [ ] Boundary checking antes de buscar
- [ ] Sin estructuras auxiliares innecesarias
- [ ] Cobertura >85%
- [x] 6 carpetas creadas (controller, dto, service, repository, entity, config)
- [x] `@RequiredArgsConstructor` en services/controllers ✅
- [x] 2+ DTOs con `@Data` (DnaRequest ✅, ErrorResponse ✅, StatsResponse ✅)
- [x] Repository `extends JpaRepository` ✅
- [x] `GlobalExceptionHandler` con `@RestControllerAdvice` ✅
- [ ] 35+ tests totales
- [x] POST /mutant → 200 (mutante), 403 (humano), 400 (inválido) ✅
- [x] GET /stats → JSON correcto ✅
- [ ] Swagger UI en `/swagger-ui.html` funciona
- [x] `@Tag`, `@Operation`, `@Schema` implementados (parcial) ✅
- [x] Campo `dnaHash` con `unique = true` ✅
- [x] Método `calculateDnaHash()` con SHA-256 ✅
- [x] `findByDnaHash()` y `countByIsMutant()` en repository ✅
