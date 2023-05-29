FROM openjdk:17-alpine

ARG JAR_FILE=/build/libs/pet-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /pet.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/pet.jar"]