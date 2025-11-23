# ========================================
# ETAPA 1: BUILD (Compilación)
# ========================================
# Imagen base con JDK completo para compilar el código
# Eclipse Temurin JDK incluye todas las herramientas necesarias
# Java 21 coincide con la configuración en build.gradle
# Se usa "AS build" para nombrar esta etapa y referenciarla después
FROM eclipse-temurin:21-jdk-alpine AS build

# Copiar TODO el código fuente del proyecto al contenedor
# Primer '.' = origen (directorio actual del host)
# Segundo '.' = destino (directorio de trabajo del contenedor)
COPY . .

# Dar permisos de ejecución al script gradlew (Gradle Wrapper)
# Necesario porque los permisos pueden perderse al copiar archivos
RUN chmod +x ./gradlew

# Ejecutar Gradle para compilar y generar el JAR ejecutable
# bootJar: tarea de Spring Boot que genera un "fat JAR" con todas las dependencias
# --no-daemon: no usar proceso Gradle en segundo plano (mejor para Docker)
# Resultado: build/libs/DetectorDeMutantes-0.0.1-SNAPSHOT.jar
RUN ./gradlew bootJar --no-daemon

# ========================================
# ETAPA 2: RUNTIME (Ejecución)
# ========================================
# Imagen base con SOLO el runtime de Java (sin herramientas de compilación)
# Eclipse Temurin es el sucesor oficial de OpenJDK (recomendado por Adoptium)
# Java 21 coincide con la versión de compilación
# Esto reduce el tamaño de la imagen final de ~500MB a ~200MB
FROM eclipse-temurin:21-jre-alpine

# Documentar que la aplicación escucha en el puerto 8080
# IMPORTANTE: esto NO abre el puerto, solo es documentación
# El puerto se mapea con: docker run -p 8080:8080
EXPOSE 8080

# Copiar el JAR generado en la ETAPA 1 (build) a la imagen final
# --from=build: tomar archivo de la etapa "build" anterior
# Solo se copia el JAR, NO el código fuente ni herramientas de compilación
# Esto mantiene la imagen final pequeña y segura
COPY --from=build ./build/libs/DetectorDeMutantes-0.0.1-SNAPSHOT.jar ./app.jar

# Comando que se ejecuta cuando el contenedor inicia
# ENTRYPOINT (no CMD) asegura que siempre se ejecute la aplicación
# ["java", "-jar", "app.jar"]: formato exec (preferido sobre shell)
ENTRYPOINT ["java", "-jar", "app.jar"]