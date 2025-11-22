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
**Estado:** ⚠️ **PARCIAL** (README básico existe, sección de performance agregada)  
**Ubicación:** `README.md`


**Tareas pendientes:**
- ⚠️ Agregar instrucciones de cómo ejecutar el programa localmente
- ⚠️ Agregar instrucciones de cómo ejecutar la API
- ⚠️ Agregar ejemplos de uso de los endpoints
- ⚠️ Agregar información sobre cómo acceder a Swagger UI
- ⚠️ Agregar información sobre cómo acceder a H2 Console
- ⚠️ Agregar URL de la API desplegada (después del despliegue)
- ⚠️ Agregar requisitos previos (Java, Gradle, etc.)

---

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


## 🔵 Ejercicios Opcionales (No otorgan puntos para la evaluación principal)

### Nivel 1: Básico


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
- [x] **Corrección de secuencias superpuestas** ✅
- [x] **Tests de performance implementados** ✅

**Nivel 2:**
- [x] API REST con Spring Boot ✅
- [x] Endpoint `POST /mutant/` implementado ✅
- [x] Retorna 200 OK (mutante) y 403 Forbidden (humano) ✅
- [ ] **Despliegue en Render** ❌

**Nivel 3:**
- [x] Base de datos H2 configurada ✅
- [x] Solo 1 registro por ADN (hash único) ✅
- [x] Endpoint `GET /stats` implementado ✅
- [x] Manejo de caso cuando ambos contadores son 0 ✅
- [x] Tests implementados (42 tests: 38 funcionales + 4 performance) ✅
- [x] **Code coverage > 80% verificado** ✅ (87% total)
- [x] **Tests de performance verificados** ✅ (cumplen límites óptimos)
- [x] **Patrones de nombres de test cumplidos** ✅ (RÚBRICAS 3.3)
- [ ] **Diagrama de Secuencia** ❌

**Entrega:**
- [x] Código fuente en repositorio GitHub ✅
- [x] **README con sección de performance** ✅
- [ ] **Instrucciones completas de ejecución en README** ⚠️ (parcial)
- [ ] **URL de la API (después de despliegue)** ❌
- [ ] **Diagrama de Secuencia en PDF** ❌

### Checklist Adicional (RÚBRICAS)
- [x] 17+ tests en `MutantDetectorTest` pasan (✅ 23/17 tests - COMPLETO, incluye 4 performance)
- [x] `if (sequenceCount > 1) return true;` implementado ✅
- [x] Conversión a `char[][]` para acceso rápido ✅
- [x] Boundary checking antes de buscar ✅
- [x] Sin estructuras auxiliares innecesarias ✅
- [x] Evita secuencias superpuestas en la misma dirección ✅
- [x] 6 carpetas creadas (controller, dto, service, repository, entity, config)
- [x] `@RequiredArgsConstructor` en services/controllers ✅
- [x] 2+ DTOs con `@Data` (DnaRequest ✅, ErrorResponse ✅, StatsResponse ✅)
- [x] Repository `extends JpaRepository` ✅
- [x] `GlobalExceptionHandler` con `@RestControllerAdvice` ✅
- [x] 35+ tests totales (✅ 42/36 implementado: 38 funcionales + 4 performance)
- [x] POST /mutant → 200 (mutante), 403 (humano), 400 (inválido) ✅
- [x] GET /stats → JSON correcto ✅
- [x] GET /stats maneja caso cuando ambos contadores son 0 ✅
- [x] Swagger UI en `/swagger-ui.html` funciona ✅
- [x] `@Tag`, `@Operation`, `@Schema` implementados ✅
- [x] Campo `dnaHash` con `unique = true` ✅
- [x] Método `calculateDnaHash()` con SHA-256 ✅
- [x] `findByDnaHash()` y `countByIsMutant()` en repository ✅
- [x] **Tests de performance con múltiples ejecuciones y promedio** ✅
- [x] **Patrones de nombres de test cumplidos** (RÚBRICAS 3.3) ✅
- [x] **Todos los métodos de test en inglés** ✅
