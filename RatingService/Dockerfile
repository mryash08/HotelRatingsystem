# Use OpenJDK as the base image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/RatingService-0.0.1-SNAPSHOT.jar RatingService-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 5002

# Run the application
ENTRYPOINT ["java", "-jar", "RatingService-0.0.1-SNAPSHOT.jar"]
