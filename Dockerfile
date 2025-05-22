# Stage 1: Build the jar
FROM maven:3.8.7-eclipse-temurin-17 as builder
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the jar
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
