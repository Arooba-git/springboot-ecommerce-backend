FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY pom.xml pom.xml
RUN mvn clean package -DskipTests
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/springboot-ecommerce-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "springboot-ecommerce-0.0.1-SNAPSHOT.jar"]
