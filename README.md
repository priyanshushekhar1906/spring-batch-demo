Spring Batch Demo with Docker
A simple yet powerful Spring Boot application demonstrating core Spring Batch functionality, complete with a REST API, security, and full Docker containerization.

What This Project Demonstrates
This project implements a classic ETL (Extract, Transform, Load) batch job:

EXTRACT: Reads user data from a CSV file (sample-data.csv).

TRANSFORM: Processes each record (converts names to uppercase).

LOAD: Writes the transformed data into an H2 in-memory database.

The application is packaged as a Docker container, making it portable and easy to run anywhere.

Features
Spring Batch Job: Chunk-oriented processing for efficient data handling.

REST API: HTTP endpoint to trigger the batch job on demand.

Security: Configured with Spring Security to protect the job trigger endpoint while allowing public access to the H2 console.

Dockerized: Complete Docker setup for consistent development and deployment.

H2 Database: In-memory database for easy testing and verification of results.

Technology Stack
Java 17

Spring Boot 3.5.5

Spring Batch

Spring Security

H2 Database

Docker

Prerequisites
Before you begin, ensure you have the following installed:

JDK 17

Maven (for building the project)

Docker (for running the containerized app)

Getting Started
1. Clone the Repository
bash
git clone <your-repo-url>
cd spring-batch-demo
2. Build the Application
bash
mvn clean package
This will create a target/demo-batch-0.0.1-SNAPSHOT.jar file.

3. Build the Docker Image
From the root directory (where the Dockerfile is located), run:

bash
docker build -t spring-batch-demo .
4. Run the Docker Container
Run the container, mapping port 9090 on your host to port 8080 inside the container:

bash
docker run -d -p 9090:8080 spring-batch-demo
Using the Application
Once the container is running, you can access the following endpoints:

1. Trigger the Batch Job
Endpoint: POST http://localhost:9090/api/batch/start

Note: This endpoint is secured with Basic Authentication. Use the default Spring Security username (user) and check the container logs for the generated password.

You can use a tool like curl or Postman to send a POST request to trigger the job.

2. Check a Public Endpoint
Endpoint: GET http://localhost:9090/api/batch/public/greet
Response: A simple greeting message. (No authentication required).

3. View the H2 Database (To See Results)
Console: http://localhost:9090/h2-console

Login Details:

JDBC URL: jdbc:h2:mem:testdb

Username: sa

Password: (leave blank)

After running the batch job, you can query the USERS table to see the transformed data:

sql
SELECT * FROM USERS;
Project Structure
text
src/main/java/com/info/demo_batch/
├── DemoBatchApplication.java     # Main application class
├── BatchConfiguration.java       # Configures the Batch Job (Reader, Processor, Writer)
├── JobLauncherController.java    # REST API to trigger the job
├── SecurityConfig.java           # Configures application security
├── User.java                     # Data model
└── UserItemProcessor.java        # Business logic for data transformation
Key Files Explained
Dockerfile : The recipe for building the Docker image. Located in the project root.

application.properties : Contains configuration to allow remote access to the H2 console (spring.h2.console.settings.web-allow-others=true).

Managing the Container
Use Docker Desktop or the VS Code Docker extension to easily:

View running containers

Stop and start the application

Monitor logs

License
This project is licensed under the MIT License.

Enjoy exploring Spring Batch! Feel free to contribute or raise issues.

