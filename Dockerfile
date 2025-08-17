# Use a base image with Java 21
FROM eclipse-temurin:21-jdk

# Create a volume for temporary files
VOLUME /tmp

# Set argument for JAR file location (from Maven)
ARG JAR_FILE=target/trier-report.jar

# Copy the built JAR into the image
COPY ${JAR_FILE} app.jar

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app.jar"]