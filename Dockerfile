FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
