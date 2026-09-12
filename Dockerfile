# Especifica la versión de la sintaxis del Dockerfile para aprovechar las mejoras de BuildKit
# syntax=docker/dockerfile:1

# ==========================================
# ETAPA 1: Construcción (Build Stage)
# ==========================================
# Se utiliza una imagen que contiene Maven y el kit de desarrollo de Java (JDK 21) sobre Alpine Linux.
# Se le asigna el nombre "build" para referenciarla más adelante.
FROM maven:3.9.11-eclipse-temurin-21-alpine AS build

# Se define el directorio de trabajo donde ocurrirá la compilación
WORKDIR /build

# Se copia únicamente el archivo pom.xml primero
COPY pom.xml .

# Se descargan las dependencias del proyecto (modo offline). 
# Al hacer esto antes de copiar el código, Docker guarda esta capa en caché. Si el pom.xml no cambia, 
# no se volverán a descargar las dependencias en futuras construcciones, ahorrando mucho tiempo.
RUN mvn -q -DskipTests dependency:go-offline

# Se copia la carpeta del código fuente al contenedor
COPY src ./src

# Se empaqueta la aplicación generando el archivo .jar (omitiendo los tests y reduciendo los logs)
RUN mvn -q -DskipTests package


# ==========================================
# ETAPA 2: Producción (Runtime Stage)
# ==========================================
# Se inicia una nueva etapa limpia usando solo el entorno de ejecución de Java (JRE 21) sobre Alpine.
# Esto hace que la imagen final sea mucho más pequeña y segura al no incluir herramientas de compilación.
FROM eclipse-temurin:21-jre-alpine

# Seguridad: Se crea un grupo ("app") y un usuario ("app") del sistema sin privilegios de administrador (root).
RUN addgroup -S app && adduser -S -G app app

# Se establece el directorio de trabajo para la aplicación
WORKDIR /app

# Se extrae el archivo .jar compilado desde la etapa anterior (build) correspondiente al servicio de inventario.
# Se copia al contenedor final, renombrándolo a "app.jar" y asignándole los permisos al usuario no privilegiado "app".
COPY --from=build --chown=app:app /build/target/inventory-service-*.jar app.jar

# Se indica a Docker que ejecute todos los comandos siguientes usando el usuario "app"
USER app

# Se expone el puerto 8081, que es donde el servicio de inventario escuchará las peticiones
EXPOSE 8081

# Se configuran variables de entorno para optimizar la máquina virtual de Java (JVM) en contenedores:
# - MaxRAMPercentage=75: Limita el uso de memoria de la app al 75% del total asignado al contenedor.
# - ExitOnOutOfMemoryError: Obliga a que el proceso muera si se queda sin memoria para que Docker pueda reiniciarlo.
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75 -XX:+ExitOnOutOfMemoryError"

# Se define el comando principal que se ejecutará al iniciar el contenedor
ENTRYPOINT ["java","-jar","app.jar"]