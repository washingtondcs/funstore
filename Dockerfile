# Etapa de construção
FROM maven:3.9.4-amazoncorretto-21 AS builder

WORKDIR /app

# Copia o código fonte para o container
COPY src ./src
COPY pom.xml .

# Build da aplicação usando Maven
RUN mvn clean package -DskipTests

# Etapa de execução
FROM openjdk:21-jdk

WORKDIR /app

# Copia o JAR construído na etapa anterior para o container atual
COPY --from=builder /app/target/*.jar app.jar

# Execute a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]