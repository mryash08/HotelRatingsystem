# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/HotelService-0.0.1-SNAPSHOT.jar HotelService-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 5001

# Run the application
ENTRYPOINT ["java", "-jar", "HotelService-0.0.1-SNAPSHOT.jar"]
