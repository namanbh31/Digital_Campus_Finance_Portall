# Use Java 21 base image
FROM eclipse-temurin:21-jdk as builder

# Set working directory
WORKDIR /app

# Copy all project files into the container
COPY . .

# Build the application using Maven
RUN ./mvnw clean package -DskipTests

# Use a smaller runtime image for final container
FROM eclipse-temurin:21-jre

# Set working directory again
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the default port
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
