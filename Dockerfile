# Use an image with OpenJDK
FROM openjdk:22-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/inventory-0.0.1-SNAPSHOT.jar /app/inventory.jar

# Expose port for the application
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "inventory.jar"]
