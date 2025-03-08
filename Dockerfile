FROM openjdk:21
WORKDIR /app
COPY target/spotify-telegram-delivery-message-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]