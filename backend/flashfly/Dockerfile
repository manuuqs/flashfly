# backend/Dockerfile - multi-stage build (Maven build + runtime)
### Stage 1: build
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom y wrapper primero para cachear dependencias
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN mvn -q -N dependency:go-offline

# Copiar el resto del proyecto
COPY src ./src

# Build (skip tests para acelerar; elimina -DskipTests para ejecutar tests)
RUN mvn -q clean package -DskipTests

### Stage 2: runtime
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copiar jar generado
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
