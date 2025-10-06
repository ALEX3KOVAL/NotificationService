FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs .
COPY ./.env .
COPY build/resources/application.yml .
COPY build/resources/"logback-spring.xml" .
COPY build/notificationService.jar .
COPY templates templates


EXPOSE 10239

ENTRYPOINT ["java","-jar","notificationService.jar","--spring.config.location=/app/application.yml","--logging.config=/app/logback-spring.xml"]