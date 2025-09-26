FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/straw-wallet-api-0.0.1-SNAPSHOT.jar fluxxoapp-v1.0.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "fluxxoapp-v1.0.jar"]
