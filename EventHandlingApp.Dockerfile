FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs .
COPY ./.env .
COPY "./build/eventHandlingApp.jar" .

EXPOSE 10249

ENTRYPOINT ["java", "-jar", "eventHandlingApp.jar"]