# Base Image
FROM maven:3.9.16-eclipse-temurin-21
# Docker Hub se "maven:3.9.16-eclipse-temurin-21" image download karta hai.
# (Equivalent)
# docker pull maven:3.9.16-eclipse-temurin-21


# Set Working Directory
WORKDIR /app
# Container ke andar /app directory create karta hai (agar nahi hai)
# aur current directory ko /app bana deta hai.
# (Equivalent)
# mkdir -p /app
# cd /app


# Copy Maven Wrapper Configuration
COPY .mvn/ .mvn
# Host machine ke .mvn folder ko container ke /app/.mvn me copy karta hai.
# (Equivalent)
# cp -r .mvn /app/.mvn


# Copy Maven Wrapper Script and pom.xml
COPY mvnw pom.xml ./
# Host se mvnw aur pom.xml ko /app me copy karta hai.
# (Equivalent)
# cp mvnw /app/
# cp pom.xml /app/


# Download Dependencies
RUN ./mvnw dependency:go-offline
# Maven project ki sari dependencies download karta hai
# aur cache (.m2 repository) me store karta hai.
# Isse next build fast hoti hai.
# (Equivalent)
# ./mvnw dependency:go-offline


# Copy Source Code
COPY src ./src
# Project ka source code container me copy karta hai.
# (Equivalent)
# cp -r src /app/src


# Start Spring Boot Application
CMD ["./mvnw","spring-boot:run"]
# Jab container start hoga tab ye command execute hogi.
# (Equivalent)
# ./mvnw spring-boot:run