FROM maven:3.8.4-openjdk-17-slim

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package
VOLUME /data

CMD ["java", "-jar", "target/casino-app-1.0-SNAPSHOT.jar"]