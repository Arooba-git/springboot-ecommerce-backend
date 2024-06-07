FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/springboot-ecommerce-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

