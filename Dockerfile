FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/WalletManagement-1.0-SNAPSHOT.jar WalletManagement.jar
ENTRYPOINT ["java", "-jar", "WalletManagement.jar"]