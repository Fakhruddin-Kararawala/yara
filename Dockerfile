FROM openjdk:11
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-Djava.security.egd=/dev/./urandom", "-Dspring.profiles.active=docker", "-jar", "app.jar"]

 