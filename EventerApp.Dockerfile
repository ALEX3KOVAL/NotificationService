FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs .
COPY ./.env .
COPY build/resources/application.yml .
COPY build/resources/"logback-spring.xml" .
COPY build/eventerApp.jar .

EXPOSE 10249

ENTRYPOINT ["java","-jar","eventerApp.jar","--spring.config.location=/app/application.yml","--logging.config=/app/logback-spring.xml"]