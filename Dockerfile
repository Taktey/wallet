FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/wallet-1.0.jar wallet-1.0.jar
ENTRYPOINT ["java", "-jar", "wallet-1.0.jar"]
