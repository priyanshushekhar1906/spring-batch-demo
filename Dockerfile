# Stage 1: Build the application
FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar
# Expose the port your app runs on
EXPOSE 8080
# The command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Explanation:

# Multi-stage build: This is a best practice. The first stage (builder) uses Maven to build the JAR file. The second stage only contains the JRE and the final JAR file, resulting in a much smaller and more secure image.

# FROM: Specifies the base image. We use official Maven and OpenJDK images.

# WORKDIR: Sets the working directory inside the container.

# COPY: Copies files from your local machine into the image.

# RUN: Executes a command during the build process (here, it packages our app).

# EXPOSE: Informs Docker that the container listens on the specified network port at runtime.

# ENTRYPOINT: The command that runs when the container starts.