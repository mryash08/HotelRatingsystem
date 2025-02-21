# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/ServiceRegistry-0.0.1-SNAPSHOT.jar ServiceRegistry-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 5003

# Run the application
ENTRYPOINT ["java", "-jar", "ServiceRegistry-0.0.1-SNAPSHOT.jar"]
