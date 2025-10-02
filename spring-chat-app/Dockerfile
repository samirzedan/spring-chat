FROM openjdk:21-jdk
WORKDIR /app
COPY target/encurtador-url-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080
