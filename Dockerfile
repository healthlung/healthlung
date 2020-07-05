FROM openjdk:8-jdk-alpine
ADD healthlung-0.2.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=prod"]