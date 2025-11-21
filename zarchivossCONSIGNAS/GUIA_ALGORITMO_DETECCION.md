# 🧬 Guía Completa: Algoritmo de Detección de Mutantes

## 📋 Resumen Ejecutivo

**Objetivo:** Implementar el método `isMutant(String[] dna)` que detecta si un ADN es mutante.

**Regla Principal:** Un humano es **mutante** si tiene **MÁS DE UNA** secuencia de **4 letras iguales** en horizontal, vertical o diagonal.

**Puntos Totales:** 35 pts (35% de la nota)
- Correctitud Funcional: 10 pts
- Complejidad Temporal: 12 pts
- Complejidad Espacial: 5 pts
- Optimizaciones: 8 pts

---

## 🎯 Requisitos Funcionales

### 1. ¿Cuándo es Mutante?

✅ **ES MUTANTE** si encuentra **2 o más** secuencias de 4 letras iguales.

❌ **NO ES MUTANTE** si encuentra **0 o 1** secuencia.

### 2. Direcciones a Buscar

Debe buscar secuencias en **4 direcciones**:

1. **Horizontal (→)**: De izquierda a derecha
2. **Vertical (↓)**: De arriba hacia abajo
3. **Diagonal Descendente (↘)**: De esquina superior izquierda a inferior derecha
4. **Diagonal Ascendente (↗)**: De esquina inferior izquierda a superior derecha

### 3. Ejemplos Visuales

#### Ejemplo 1: Mutante ✅
```
A T G C G A
C A G T G C
T T A T G T
A G A A G G  ← Diagonal ↘: A-A-A-A
C C C C T A  ← Horizontal: C-C-C-C
T C A C T G

Secuencias encontradas: 2
Resultado: ES MUTANTE ✅
```

#### Ejemplo 2: NO Mutante ❌
```
A T G C G A
C A G T G C
T T A T T T  ← Solo una secuencia: T-T-T-T
A G A C G G
G C G T C A
T C A C T G

Secuencias encontradas: 1
Resultado: NO ES MUTANTE ❌
```

---

## 📐 Estructura del Algoritmo

### Pseudocódigo Completo

```
FUNCIÓN isMutant(dna):
    1. Validar que dna sea válido (NxN, solo A/T/C/G, mínimo 4x4)
    2. Convertir String[] a char[][] para acceso rápido
    3. sequenceCount = 0
    4. PARA cada posición (row, col) en la matriz:
        a. SI hay espacio para horizontal → buscar 4 iguales
           SI encontró → sequenceCount++, si sequenceCount > 1 → RETORNAR true
        b. SI hay espacio para vertical → buscar 4 iguales
           SI encontró → sequenceCount++, si sequenceCount > 1 → RETORNAR true
        c. SI hay espacio para diagonal ↘ → buscar 4 iguales
           SI encontró → sequenceCount++, si sequenceCount > 1 → RETORNAR true
        d. SI hay espacio para diagonal ↗ → buscar 4 iguales
           SI encontró → sequenceCount++, si sequenceCount > 1 → RETORNAR true
    5. RETORNAR false (solo encontró 0 o 1 secuencia)
```

---

## 🔧 Implementación Paso a Paso

### Paso 1: Constantes y Validación Inicial

```java
private static final int SEQUENCE_LENGTH = 4;
private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');

public boolean isMutant(String[] dna) {
    // Validación básica
    if (dna == null || dna.length == 0) {
        return false;
    }
    
    final int n = dna.length;
    
    // Validar tamaño mínimo (4x4)
    if (n < SEQUENCE_LENGTH) {
        return false;
    }
    
    // Validar que sea matriz cuadrada y caracteres válidos
    for (String row : dna) {
        if (row == null || row.length() != n) {
            return false;  // No es cuadrada
        }
        
        for (char c : row.toCharArray()) {
            if (!VALID_BASES.contains(c)) {
                return false;  // Carácter inválido
            }
        }
    }
    
    // Continuar con el algoritmo...
}
```

**Nota:** La validación principal ya está en `ValidDnaSequenceValidator`, pero el algoritmo debe ser robusto.

### Paso 2: Conversión a Matriz de Caracteres

```java
// Convertir String[] a char[][] para acceso O(1)
char[][] matrix = new char[n][];
for (int i = 0; i < n; i++) {
    matrix[i] = dna[i].toCharArray();
}
```

**¿Por qué?** 
- `matrix[row][col]` es más rápido que `dna[row].charAt(col)`
- Acceso directo sin overhead de validación
- **+2 pts bonus** en Complejidad Espacial

### Paso 3: Búsqueda de Secuencias (Loop Principal)

```java
int sequenceCount = 0;

// Single Pass: Solo 2 loops anidados (2.0 pts)
for (int row = 0; row < n; row++) {
    for (int col = 0; col < n; col++) {
        
        // 1. Horizontal (→)
        if (col <= n - SEQUENCE_LENGTH) {
            if (checkHorizontal(matrix, row, col)) {
                sequenceCount++;
                // Early Termination (2.4 pts) - CRÍTICO
                if (sequenceCount > 1) return true;
            }
        }
        
        // 2. Vertical (↓)
        if (row <= n - SEQUENCE_LENGTH) {
            if (checkVertical(matrix, row, col)) {
                sequenceCount++;
                if (sequenceCount > 1) return true;
            }
        }
        
        // 3. Diagonal Descendente (↘)
        if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
            if (checkDiagonalDescending(matrix, row, col)) {
                sequenceCount++;
                if (sequenceCount > 1) return true;
            }
        }
        
        // 4. Diagonal Ascendente (↗)
        if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
            if (checkDiagonalAscending(matrix, row, col)) {
                sequenceCount++;
                if (sequenceCount > 1) return true;
            }
        }
    }
}

return false;  // Solo encontró 0 o 1 secuencia
```

### Paso 4: Métodos Auxiliares de Verificación

#### 4.1 Horizontal (→)

```java
private boolean checkHorizontal(char[][] matrix, int row, int col) {
    final char base = matrix[row][col];
    // Direct Comparison (1.2 pts) - Sin loops
    return matrix[row][col + 1] == base &&
           matrix[row][col + 2] == base &&
           matrix[row][col + 3] == base;
}
```

**Ejemplo visual:**
```
Posición (row=4, col=0):
[C][C][C][C] T A
 ↑
 Empezar aquí

Verificar: C == C == C == C → SÍ ✅
```

#### 4.2 Vertical (↓)

```java
private boolean checkVertical(char[][] matrix, int row, int col) {
    final char base = matrix[row][col];
    // Direct Comparison (1.2 pts)
    return matrix[row + 1][col] == base &&
           matrix[row + 2][col] == base &&
           matrix[row + 3][col] == base;
}
```

**Ejemplo visual:**
```
Columna 0, desde row=1:
[A] ← row=1
[A] ← row=2
[A] ← row=3
[A] ← row=4

Verificar: A == A == A == A → SÍ ✅
```

#### 4.3 Diagonal Descendente (↘)

```java
private boolean checkDiagonalDescending(char[][] matrix, int row, int col) {
    final char base = matrix[row][col];
    // Direct Comparison (1.2 pts)
    return matrix[row + 1][col + 1] == base &&
           matrix[row + 2][col + 2] == base &&
           matrix[row + 3][col + 3] == base;
}
```

**Ejemplo visual:**
```
    0   1   2   3
  ┌───┬───┬───┬───┐
0 │[A]│ T │ G │ C │
  ├───┼───┼───┼───┤
1 │ C │[A]│ G │ T │
  ├───┼───┼───┼───┤
2 │ T │ T │[A]│ T │
  ├───┼───┼───┼───┤
3 │ A │ G │ A │[A]│
  └───┴───┴───┴───┘

(0,0) → (1,1) → (2,2) → (3,3)
  A   →   A   →   A   →   A  ✅
```

#### 4.4 Diagonal Ascendente (↗)

```java
private boolean checkDiagonalAscending(char[][] matrix, int row, int col) {
    final char base = matrix[row][col];
    // Direct Comparison (1.2 pts)
    return matrix[row - 1][col + 1] == base &&
           matrix[row - 2][col + 2] == base &&
           matrix[row - 3][col + 3] == base;
}
```

**Ejemplo visual:**
```
    0   1   2   3
  ┌───┬───┬───┬───┐
0 │ A │ T │ G │[C]│
  ├───┼───┼───┼───┤
1 │ C │ A │[C]│ T │
  ├───┼───┼───┼───┤
2 │ T │[C]│ A │ T │
  ├───┼───┼───┼───┤
3 │[C]│ A │ A │ A │
  └───┴───┴───┴───┘

(3,0) → (2,1) → (1,2) → (0,3)
  C   →   C   →   C   →   C  ✅
```

---

## ⚡ Optimizaciones Críticas (8 pts)

### 1. Early Termination (2.4 pts) - CRÍTICO ⚠️

**Patrón:**
```java
if (sequenceCount > 1) return true;
```

**¿Por qué es crítico?**
- Ahorra hasta 70% del tiempo de ejecución
- Si encuentra 2 secuencias, no necesita seguir buscando
- **DEBE estar después de cada incremento de sequenceCount**

**Ejemplo de impacto:**
```
Matriz 100x100 = 10,000 celdas

Sin early termination:
- Siempre recorre las 10,000 celdas
- Tiempo: ~100ms

Con early termination:
- Encuentra 2 secuencias en las primeras 500 celdas
- Para de buscar inmediatamente
- Tiempo: ~5ms
- Mejora: 20x más rápido ⚡
```

### 2. Single Pass (2.0 pts)

**Patrón:**
```java
// ✅ CORRECTO: Solo 2 loops anidados
for (int row = 0; row < n; row++) {
    for (int col = 0; col < n; col++) {
        // Verificar todas las direcciones aquí
    }
}

// ❌ INCORRECTO: Múltiples passes
for (int row = 0; row < n; row++) {
    for (int col = 0; col < n; col++) {
        checkHorizontal(...);
    }
}
for (int row = 0; row < n; row++) {
    for (int col = 0; col < n; col++) {
        checkVertical(...);
    }
}
```

### 3. Boundary Checking (1.6 pts)

**Patrón:**
```java
// ✅ CORRECTO: Verificar límites antes de buscar
if (col <= n - SEQUENCE_LENGTH) {
    checkHorizontal(...);
}

// ❌ INCORRECTO: Buscar sin verificar límites
checkHorizontal(...);  // Puede causar ArrayIndexOutOfBoundsException
```

**Límites por dirección:**
- **Horizontal:** `col <= n - SEQUENCE_LENGTH`
- **Vertical:** `row <= n - SEQUENCE_LENGTH`
- **Diagonal ↘:** `row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH`
- **Diagonal ↗:** `row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH`

### 4. Direct Comparison (1.2 pts)

**Patrón:**
```java
// ✅ CORRECTO: Comparación directa sin loops
return matrix[row][col + 1] == base &&
       matrix[row][col + 2] == base &&
       matrix[row][col + 3] == base;

// ❌ INCORRECTO: Usar loop innecesario
for (int i = 1; i < 4; i++) {
    if (matrix[row][col + i] != base) return false;
}
return true;
```

### 5. Validation Set O(1) (0.8 pts)

**Patrón:**
```java
// ✅ CORRECTO: Set para validación O(1)
private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');

if (!VALID_BASES.contains(c)) {
    return false;
}

// ❌ INCORRECTO: Validación O(4) con múltiples ifs
if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
    return false;
}
```

---

## 🚫 Errores Comunes a Evitar

### 1. NO usar estructuras auxiliares (-3 pts)

```java
// ❌ INCORRECTO: NO usar ArrayList, HashMap, List, Set, Map
List<Integer> positions = new ArrayList<>();
Set<String> sequences = new HashSet<>();
Map<String, Integer> counts = new HashMap<>();

// ✅ CORRECTO: Solo variables primitivas y char[][]
int sequenceCount = 0;
char[][] matrix = ...;
```

### 2. NO olvidar Early Termination

```java
// ❌ INCORRECTO: Incrementar sin verificar
sequenceCount++;
// Continúa buscando aunque ya encontró 2

// ✅ CORRECTO: Verificar después de cada incremento
sequenceCount++;
if (sequenceCount > 1) return true;
```

### 3. NO usar loops innecesarios

```java
// ❌ INCORRECTO: Loop para comparar 4 caracteres
for (int i = 1; i < 4; i++) {
    if (matrix[row][col + i] != base) return false;
}

// ✅ CORRECTO: Comparación directa
return matrix[row][col + 1] == base &&
       matrix[row][col + 2] == base &&
       matrix[row][col + 3] == base;
```

### 4. NO olvidar Boundary Checking

```java
// ❌ INCORRECTO: Puede causar ArrayIndexOutOfBoundsException
checkHorizontal(matrix, row, col);  // Si col = n-1, col+3 está fuera

// ✅ CORRECTO: Verificar límites primero
if (col <= n - SEQUENCE_LENGTH) {
    checkHorizontal(matrix, row, col);
}
```

---

## 📊 Benchmarks de Rendimiento (12 pts)

### Tiempos Objetivo

| Tamaño | Óptimo | Aceptable | Puntos |
|--------|--------|-----------|--------|
| 6x6 | ≤ 1 ms | ≤ 5 ms | 2.4 pts |
| 100x100 | ≤ 20 ms | ≤ 100 ms | 3.6 pts |
| 1000x1000 | ≤ 500 ms | ≤ 5000 ms | 3.6 pts |
| Early Term. | Código presente | Verificación | 2.4 pts |

### Cómo Verificar Rendimiento

```java
@Test
void testPerformance_6x6() {
    String[] dna = generateDna(6);
    
    long start = System.nanoTime();
    boolean result = detector.isMutant(dna);
    long end = System.nanoTime();
    
    long durationMs = (end - start) / 1_000_000;
    assertTrue(durationMs <= 5, "6x6 debe ser ≤ 5ms, fue: " + durationMs + "ms");
}
```

---

## 📝 Implementación Completa de Referencia

```java
package com.utn.DetectorDeMutantes.service;

import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class MutantDetector {
    
    private static final int SEQUENCE_LENGTH = 4;
    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');
    
    public boolean isMutant(String[] dna) {
        // Validación inicial
        if (dna == null || dna.length == 0) {
            return false;
        }
        
        final int n = dna.length;
        
        if (n < SEQUENCE_LENGTH) {
            return false;
        }
        
        // Validar matriz cuadrada y caracteres válidos
        for (String row : dna) {
            if (row == null || row.length() != n) {
                return false;
            }
            
            for (char c : row.toCharArray()) {
                if (!VALID_BASES.contains(c)) {
                    return false;
                }
            }
        }
        
        // Convertir a matriz de caracteres
        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }
        
        // Búsqueda de secuencias
        int sequenceCount = 0;
        
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                
                // Horizontal (→)
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;  // Early termination
                    }
                }
                
                // Vertical (↓)
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
                
                // Diagonal Descendente (↘)
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalDescending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
                
                // Diagonal Ascendente (↗)
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalAscending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
            }
        }
        
        return false;  // Solo encontró 0 o 1 secuencia
    }
    
    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row][col + 1] == base &&
               matrix[row][col + 2] == base &&
               matrix[row][col + 3] == base;
    }
    
    private boolean checkVertical(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col] == base &&
               matrix[row + 2][col] == base &&
               matrix[row + 3][col] == base;
    }
    
    private boolean checkDiagonalDescending(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col + 1] == base &&
               matrix[row + 2][col + 2] == base &&
               matrix[row + 3][col + 3] == base;
    }
    
    private boolean checkDiagonalAscending(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row - 1][col + 1] == base &&
               matrix[row - 2][col + 2] == base &&
               matrix[row - 3][col + 3] == base;
    }
}
```

---

## ✅ Checklist Final

Antes de considerar el algoritmo completo, verifica:

- [ ] ✅ Validación de entrada (null, vacío, tamaño mínimo)
- [ ] ✅ Validación de matriz cuadrada
- [ ] ✅ Validación de caracteres válidos (A, T, C, G)
- [ ] ✅ Conversión a `char[][]` con `toCharArray()`
- [ ] ✅ Búsqueda en 4 direcciones (horizontal, vertical, 2 diagonales)
- [ ] ✅ Early termination después de cada incremento (`if (sequenceCount > 1) return true`)
- [ ] ✅ Boundary checking antes de cada verificación
- [ ] ✅ Comparación directa sin loops en métodos auxiliares
- [ ] ✅ Set para validación O(1)
- [ ] ✅ Solo 2 loops anidados (single pass)
- [ ] ✅ NO usar ArrayList, HashMap, List, Set, Map dentro de `isMutant()`
- [ ] ✅ Retorna `true` si encuentra 2+ secuencias
- [ ] ✅ Retorna `false` si encuentra 0 o 1 secuencia

---

## 🎯 Puntos Obtenidos por Implementación

| Aspecto | Puntos | Estado |
|---------|--------|--------|
| Correctitud Funcional (tests) | 10 pts | Después de tests |
| Complejidad Temporal (rendimiento) | 12 pts | Verificar benchmarks |
| Complejidad Espacial (sin estructuras auxiliares) | 5 pts | ✅ Si no usas ArrayList/HashMap |
| Early Termination | 2.4 pts | ✅ Si implementas `if (sequenceCount > 1) return true` |
| Single Pass | 2.0 pts | ✅ Si solo usas 2 loops anidados |
| Boundary Checking | 1.6 pts | ✅ Si verificas límites antes de buscar |
| Direct Comparison | 1.2 pts | ✅ Si comparas directamente sin loops |
| Validation Set O(1) | 0.8 pts | ✅ Si usas `Set.of('A','T','C','G')` |

**Total Máximo:** 35 pts

---

## 📚 Referencias

- **RÚBRICAS:** `zarchivossCONSIGNAS/RESUMEN_RUBRICAS.md` (líneas 28-144)
- **CONSIGNAS:** `zarchivossCONSIGNAS/CONSIGNAS` (líneas 1055-1280)
- **README:** Explicación detallada del algoritmo

---

**¡Buena suerte con la implementación! 🚀**

