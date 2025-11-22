# 📋 Cosas Por Hacer

## 📋 Examen Mercadolibre - Pendientes

### 🚀 Prioridad ALTA (Requisitos del Examen)

#### 1. Despliegue en Render
**Prioridad: ALTA** - Requisito Nivel 2  
**Estado:** ❌ **PENDIENTE**  
**Ubicación:** Configuración de despliegue

**Tareas:**
- ⚠️ Crear cuenta en Render (si no existe)
- ⚠️ Configurar servicio web en Render
- ⚠️ Conectar repositorio GitHub con Render
- ⚠️ Configurar variables de entorno si es necesario
- ⚠️ Realizar despliegue inicial
- ⚠️ Verificar que la API funciona en producción
- ⚠️ Obtener URL de la API desplegada

#### 2. Code Coverage > 80% ✅ VERIFICADO
**Prioridad: ALTA** - Requisito Nivel 3  
**Estado:** ✅ **COMPLETO** - Cobertura verificada: **87%**  
**Ubicación:** Reportes de JaCoCo

**Resultados:**
- ✅ Cobertura Total: **87%** (requisito: >80%) ✅
- ✅ Controller: 100%
- ✅ Service: 94%
- ✅ Validation: 95%
- ✅ DTO: 100%
- ⚠️ Exception: 38% (mejorable, pero no crítico para cumplir requisito)

**Nota:** El requisito de >80% está cumplido. La cobertura del paquete de excepciones es baja pero no afecta el cumplimiento del requisito. Si se desea mejorar, se pueden agregar tests para:
- `GlobalExceptionHandler.handleConstraintViolationException()` (probablemente no probado)
- `GlobalExceptionHandler.handleDnaHashCalculationException()` (probablemente no probado)
- Ambos constructores de `DnaHashCalculationException`

#### 3. Diagrama de Secuencia
**Prioridad: ALTA** - Requisito Nivel 3  
**Estado:** ❌ **PENDIENTE**  
**Ubicación:** Documentación (PDF o imagen)

**Tareas:**
- ⚠️ Crear diagrama de secuencia para el flujo completo:
  - Cliente → MutantController → MutantService → MutantDetector
  - MutantService → DnaRecordRepository → Base de Datos
  - Cliente → MutantController → StatsService → DnaRecordRepository
- ⚠️ Puede usar herramientas como:
  - PlantUML
  - Draw.io
  - Lucidchart
  - Mermaid
- ⚠️ Guardar como PDF o imagen
- ⚠️ Incluir en documentación del proyecto

#### 4. README Completo con Instrucciones
**Prioridad: ALTA** - Requisito de Entrega  
**Estado:** ⚠️ **PARCIAL** (README básico existe)  
**Ubicación:** `README.md`

**Tareas:**
- ⚠️ Agregar instrucciones de cómo ejecutar el programa localmente
- ⚠️ Agregar instrucciones de cómo ejecutar la API
- ⚠️ Agregar ejemplos de uso de los endpoints
- ⚠️ Agregar información sobre cómo acceder a Swagger UI
- ⚠️ Agregar información sobre cómo acceder a H2 Console
- ⚠️ Agregar URL de la API desplegada (después del despliegue)
- ⚠️ Agregar requisitos previos (Java, Gradle, etc.)

---

## ✅ Tests Completados (Ya implementados - No hacer)

### MutantServiceTest
**Estado:** ✅ **COMPLETO - 5/5 tests implementado**

### StatsServiceTest
**Estado:** ✅ **COMPLETO - 6/6 tests implementado**

### MutantControllerTest
**Estado:** ✅ **COMPLETO - 8/8 tests implementado**

### MutantDetectorTest
**Estado:** ✅ **COMPLETO - 19/17 tests implementado**

---

## 🔵 Mejoras Opcionales (No críticas - Requisito cumplido)

### Mejorar Cobertura del Paquete de Excepciones (Opcional)
**Prioridad: BAJA** - El requisito de >80% ya está cumplido (87% total)  
**Estado:** ⚠️ **OPCIONAL**  
**Ubicación:** Nuevos tests para `GlobalExceptionHandler`

**Nota:** El paquete de excepciones tiene 38% de cobertura, pero esto no afecta el cumplimiento del requisito de >80% total. Si se desea mejorar:

**Tareas opcionales:**
- ⚠️ Crear `GlobalExceptionHandlerTest` para probar:
  - `handleConstraintViolationException()` - Manejo de `ConstraintViolationException`
  - `handleDnaHashCalculationException()` - Manejo de `DnaHashCalculationException`
  - Verificar que ambos métodos retornan el formato correcto de `ErrorResponse`
- ⚠️ Probar ambos constructores de `DnaHashCalculationException`:
  - Constructor con mensaje
  - Constructor con mensaje y causa

**Ubicación sugerida:** `src/test/java/com/utn/DetectorDeMutantes/exception/GlobalExceptionHandlerTest.java`

---

## 🧪 FASE 7: Tests del Algoritmo

- ✅ **Cobertura verificada: 87%** (requisito examen: >80%) ✅

---

## 📈 Cobertura Requerida

**Requisito Examen Mercadolibre (Nivel 3):**
- ✅ **Code coverage > 80%** - **CUMPLIDO** (87% total)

**Métricas Adicionales (RÚBRICAS):**
- ⚠️ Cobertura Total: ≥90% (óptimo) / ≥70% (aceptable)
- ⚠️ Cobertura Service: ≥96% (óptimo) / ≥85% (aceptable)
- ⚠️ Cobertura Controller: ≥95% (óptimo) / ≥80% (aceptable)

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

### Checklist Rápido de Entrega - Examen Mercadolibre

**Nivel 1:**
- [x] Función `isMutant(String[] dna)` implementada ✅
- [x] Validación completa del input ✅
- [x] Búsqueda en horizontal, vertical y oblicua ✅
- [x] Algoritmo optimizado ✅

**Nivel 2:**
- [x] API REST con Spring Boot ✅
- [x] Endpoint `POST /mutant/` implementado ✅
- [x] Retorna 200 OK (mutante) y 403 Forbidden (humano) ✅
- [ ] **Despliegue en Render** ❌

**Nivel 3:**
- [x] Base de datos H2 configurada ✅
- [x] Solo 1 registro por ADN (hash único) ✅
- [x] Endpoint `GET /stats` implementado ✅
- [x] Tests implementados (38 tests) ✅
- [x] **Code coverage > 80% verificado** ✅ (87% total)
- [ ] **Diagrama de Secuencia** ❌

**Entrega:**
- [x] Código fuente en repositorio GitHub ✅
- [ ] **Instrucciones de ejecución en README** ⚠️
- [ ] **URL de la API (después de despliegue)** ❌
- [ ] **Diagrama de Secuencia en PDF** ❌

### Checklist Adicional (RÚBRICAS)
- [x] 17+ tests en `MutantDetectorTest` pasan (✅ 19/17 tests - COMPLETO)
- [x] `if (sequenceCount > 1) return true;` implementado ✅
- [x] Conversión a `char[][]` para acceso rápido ✅
- [x] Boundary checking antes de buscar ✅
- [x] Sin estructuras auxiliares innecesarias ✅
- [x] 6 carpetas creadas (controller, dto, service, repository, entity, config)
- [x] `@RequiredArgsConstructor` en services/controllers ✅
- [x] 2+ DTOs con `@Data` (DnaRequest ✅, ErrorResponse ✅, StatsResponse ✅)
- [x] Repository `extends JpaRepository` ✅
- [x] `GlobalExceptionHandler` con `@RestControllerAdvice` ✅
- [x] 35+ tests totales (✅ 38/36 implementado)
- [x] POST /mutant → 200 (mutante), 403 (humano), 400 (inválido) ✅
- [x] GET /stats → JSON correcto ✅
- [x] Swagger UI en `/swagger-ui.html` funciona ✅
- [x] `@Tag`, `@Operation`, `@Schema` implementados ✅
- [x] Campo `dnaHash` con `unique = true` ✅
- [x] Método `calculateDnaHash()` con SHA-256 ✅
- [x] `findByDnaHash()` y `countByIsMutant()` en repository ✅
