FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs .
COPY ./.env .
COPY "./build/notificationService.jar" .
COPY ""

EXPOSE 10249

ENTRYPOINT ["java", "-jar", "notificationService.jar"]