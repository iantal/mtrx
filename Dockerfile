FROM openjdk:15-jdk-alpine
WORKDIR /app
EXPOSE 8012
COPY build/libs/mtrx-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "mtrx-0.0.1-SNAPSHOT.jar"]