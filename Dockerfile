# Stage 1: Build
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Aguarda o PostgreSQL ficar pronto e inicia a aplicação
CMD ["sh", "-c", "echo 'Aguardando PostgreSQL...' && sleep 45 && java -jar app.jar"]