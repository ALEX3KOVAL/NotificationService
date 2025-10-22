FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs .
COPY ./.env .
COPY settings/ .
COPY build/eventerApp.jar .
COPY templates templates

EXPOSE 10260

ENTRYPOINT ["java","-jar","eventerApp.jar","--spring.config.location=/app/application.yml","--logging.config=/app/logback-spring.xml"]
