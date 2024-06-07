# Use a Maven image with Java 17 for the build stage
# Use a Maven image with Java 17 for the build stage
# Use a Maven image with Java 17 for the build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use an OpenJDK 17 image for the runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/springboot-ecommerce-0.0.1-SNAPSHOT.jar .
EXPOSE 8443
CMD ["java", "-jar", "springboot-ecommerce-0.0.1-SNAPSHOT.jar"]
