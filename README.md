# GLOBAL_DetectorDeMutantes_26-11

Para acceder a la BD, entrar a través del link: http://localhost:8080/h2-console, NO por la apliciación H2 de escritorio

## 🚀 Tests de Performance (Benchmarks)

El proyecto incluye tests de medición de velocidad para verificar que el algoritmo cumple con los requisitos de rendimiento especificados en las rúbricas.

### Características de los Tests de Performance

- ✅ **Múltiples ejecuciones**: Cada test ejecuta el algoritmo múltiples veces y calcula el promedio para obtener resultados más precisos y estables
- ✅ **Warmup de JVM**: Se realiza una ejecución previa para calentar la JVM y evitar mediciones frías que puedan afectar los resultados
- ✅ **Precisión decimal**: Los tiempos se muestran con precisión de 3 decimales (milisegundos) para capturar diferencias pequeñas
- ✅ **Medición exclusiva**: Solo se mide el tiempo de ejecución del método `isMutant()`, excluyendo la generación de matrices de prueba

### Benchmarks Implementados

| Tamaño | Iteraciones | Límite Aceptable | Límite Óptimo |
|--------|-------------|------------------|---------------|
| **6x6** | 1000 ejecuciones | ≤ 5ms | ≤ 1ms |
| **100x100** | 100 ejecuciones | ≤ 100ms | ≤ 20ms |
| **1000x1000** | 10 ejecuciones | ≤ 5000ms | ≤ 500ms |

### Ejecutar Tests de Performance

```bash
# Ejecutar todos los tests de performance
./gradlew test --tests MutantDetectorTest.testPerformance*

# Ejecutar test específico
./gradlew test --tests MutantDetectorTest.testPerformance_6x6
./gradlew test --tests MutantDetectorTest.testPerformance_100x100
./gradlew test --tests MutantDetectorTest.testPerformance_1000x1000
```

### Resultados Esperados

Los tests muestran en consola el tiempo promedio de ejecución:

```
Performance 6x6: 0.002ms (promedio de 1000 ejecuciones, límite: 5ms)
Performance 100x100: 0.370ms (promedio de 100 ejecuciones, límite: 100ms)
Performance 1000x1000: 11.257ms (promedio de 10 ejecuciones, límite: 5000ms)
```

### Optimizaciones Verificadas

Los tests verifican que el algoritmo implementa las siguientes optimizaciones:

- ✅ **Early Termination**: El algoritmo termina inmediatamente al encontrar 2 secuencias
- ✅ **Single Pass**: Solo recorre la matriz una vez (2 loops anidados)
- ✅ **Conversión eficiente**: Usa `char[][]` para acceso O(1)
- ✅ **Comparación directa**: Sin loops innecesarios en métodos auxiliares