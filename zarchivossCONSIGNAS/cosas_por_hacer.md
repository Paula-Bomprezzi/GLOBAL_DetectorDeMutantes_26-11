# 📋 Cosas Por Hacer

## 🎯 Orden Recomendado de Implementación

**Estrategia:** Dejar el algoritmo `isMutant()` para el final. Primero construir todo el contexto (Swagger, Tests básicos) para tener un sistema funcional que luego solo necesite el algoritmo.

---


## 🧪 FASE 5: Tests Básicos (Verificar Funcionamiento)

### MutantServiceTest
**Prioridad: MEDIA** - Verificar lógica de negocio  
**Estado:** ✅ **COMPLETO - 5/5 tests implementado**  
**Ubicación:** `src/test/java/com/utn/DetectorDeMutantes/service/MutantServiceTest.java`

**✅ COMPLETADO según RÚBRICAS 3.2:**
- ✅ DNA nuevo (debe calcular hash, analizar, guardar)
- ✅ DNA existente (debe retornar cacheado)
- ✅ Manejo de excepciones
- ✅ Verificar que se guarda en BD con `createdAt`
- ✅ Verificar que el hash se calcula correctamente
- ✅ Usa `@Mock` para `DnaRecordRepository` y `MutantDetector`

### StatsServiceTest
**Prioridad: MEDIA** - Verificar estadísticas  
**Estado:** ✅ **COMPLETO - 6/6 tests implementado**  
**Ubicación:** `src/test/java/com/utn/DetectorDeMutantes/service/StatsServiceTest.java`

**✅ COMPLETADO según RÚBRICAS 3.2:**
- ✅ Ratio normal (mutantes/humanos)
- ✅ Sin humanos (caso especial)
- ✅ Sin mutantes
- ✅ Sin registros
- ✅ Verificar cálculo correcto del ratio
- ✅ Usa `@Mock` para `DnaRecordRepository`

### MutantControllerTest
**Prioridad: MEDIA** - Verificar endpoints  
**Estado:** ✅ **COMPLETO - 8/8 tests implementado**  
**Ubicación:** `src/test/java/com/utn/DetectorDeMutantes/controller/MutantControllerTest.java`

**✅ COMPLETADO según CONSIGNAS (líneas 1642-1666) y RÚBRICAS (4.1):**
- ✅ POST /mutant con mutante → 200 OK (1.5 pts)
- ✅ POST /mutant con humano → 403 Forbidden (1.5 pts)
- ✅ POST /mutant con DNA inválido → 400 Bad Request (1.0 pts)
- ✅ POST /mutant con DNA null → 400 Bad Request
- ✅ POST /mutant con DNA vacío → 400 Bad Request
- ✅ POST /mutant con matriz no cuadrada → 400 Bad Request
- ✅ GET /stats → 200 OK con JSON correcto (1.0 pts)
- ✅ GET /stats sin humanos → 200 OK con JSON correcto
- ✅ Usa `@SpringBootTest` y `@AutoConfigureMockMvc`
- ✅ Mockea `MutantService` y `StatsService`

---


## 🧪 FASE 7: Tests del Algoritmo

- ⚠️ **Cobertura mínima: 85%** | **Óptimo: 95%+** - **PENDIENTE VERIFICAR** (RÚBRICAS 3.1 - 3.2 pts)

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
- [x] 17+ tests en `MutantDetectorTest` pasan (✅ 19/17 tests - COMPLETO)
- [x] `if (sequenceCount > 1) return true;` implementado ✅
- [x] Conversión a `char[][]` para acceso rápido ✅
- [x] Boundary checking antes de buscar ✅
- [x] Sin estructuras auxiliares innecesarias ✅
- [ ] Cobertura >85%
- [x] 6 carpetas creadas (controller, dto, service, repository, entity, config)
- [x] `@RequiredArgsConstructor` en services/controllers ✅
- [x] 2+ DTOs con `@Data` (DnaRequest ✅, ErrorResponse ✅, StatsResponse ✅)
- [x] Repository `extends JpaRepository` ✅
- [x] `GlobalExceptionHandler` con `@RestControllerAdvice` ✅
- [x] 35+ tests totales (✅ 38/36 implementado: 19 MutantDetectorTest ✅, 5 MutantServiceTest ✅, 6 StatsServiceTest ✅, 8 MutantControllerTest ✅)
- [x] POST /mutant → 200 (mutante), 403 (humano), 400 (inválido) ✅
- [x] GET /stats → JSON correcto ✅
- [x] Swagger UI en `/swagger-ui.html` funciona ✅
- [x] `@Tag`, `@Operation`, `@Schema` implementados ✅
- [x] Campo `dnaHash` con `unique = true` ✅
- [x] Método `calculateDnaHash()` con SHA-256 ✅
- [x] `findByDnaHash()` y `countByIsMutant()` en repository ✅
