FROM openjdk:17-alpine

RUN mkdir /app

COPY target/song-service-1.0.0-SNAPSHOT.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar","/app/spring-boot-application.jar"]