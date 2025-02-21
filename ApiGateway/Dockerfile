# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/ApiGateway-0.0.1-SNAPSHOT.jar ApiGateway-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 5004

# Run the application
ENTRYPOINT ["java", "-jar", "ApiGateway-0.0.1-SNAPSHOT.jar"]
