FROM eclipse-temurin:17

LABEL authors="Vedha"
LABEL mentainer="vedha@gmail.com"

WORKDIR /app

COPY target/SpringBootWebFluxCurd-0.0.1-SNAPSHOT.jar /app/SpringBootWebFluxCurd.jar

ENTRYPOINT ["java", "-jar", "SpringBootWebFluxCurd.jar"]