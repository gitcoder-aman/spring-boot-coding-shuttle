FROM maven:3.9.16-eclipse-temurin-21

WORKDIR /app

COPY ./target/product-service-0.0.1-SNAPSHOT.jar .

EXPOSE 8080
CMD ["java","-jar","product-service-0.0.1-SNAPSHOT.jar"]