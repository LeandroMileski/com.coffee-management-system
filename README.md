# Java Coffee Management System

The **Java Coffee Management System** is a Spring Boot-based microservices application designed to manage coffee shop operations. It consists of three core services: **Main API**, **Email Service**, and **SMS Service**. The Main API handles core business logic (e.g., user authentication, order management), while the Email and SMS Services manage notifications via Spring Mail and AWS SNS, respectively. RabbitMQ enables asynchronous communication between services, and PostgreSQL is used for data persistence. This project leverages GitHub Projects for task tracking, making development more engaging and rewarding—every closed issue feels like a victory!

## Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Setup Instructions](#setup-instructions)
- [Configuration](#configuration)
- [Milestones](#milestones)
- [Contributing](#contributing)
- [License](#license)

## Project Overview

This project aims to build a scalable, secure, and maintainable coffee shop management system using a microservices architecture. Key features include:

- JWT-based authentication for secure access to endpoints.
- CRUD operations for managing orders and users.
- Asynchronous notifications via Email and SMS Services using RabbitMQ.
- PostgreSQL for data storage with Flyway/Liquibase for migrations.
- Comprehensive testing (unit, integration, and end-to-end) with JaCoCo for coverage reports.
- Production-ready deployment with Docker and secure configurations.

The project is organized into milestones tracked via GitHub Projects, ensuring structured progress and clear task management.

## Tech Stack

- **Backend**: Spring Boot, Spring Security, Spring Data JPA, Spring Mail, Spring AMQP
- **Database**: PostgreSQL (production/dev), H2 (testing)
- **Messaging**: RabbitMQ
- **Notifications**: AWS SNS (SMS), Spring Mail (Email)
- **Build Tool**: Maven
- **Testing**: JUnit, Testcontainers, JaCoCo
- **Database Migrations**: Flyway or Liquibase
- **Documentation**: Springdoc OpenAPI/Swagger
- **Containerization**: Docker
- **Version Control**: Git, GitHub

## Setup Instructions

### Prerequisites

- **Java**: 17 or higher
- **Maven**: 3.8.0 or higher
- **PostgreSQL**: 15 or higher
- **RabbitMQ**: 3.10 or higher
- **AWS Account**: For SNS (SMS Service)
- **Docker**: For containerized deployment (optional)
- **Git**: For cloning the repository

### Steps

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/java-coffee-management-system.git
   cd java-coffee-management-system
   ```

2. **Set Up Environment Variables**:
   - Copy the `application-prod.yml.example` files for each service (Main API, Email Service, SMS Service) to `application-prod.yml`.
   - Add sensitive configuration details (e.g., `DB_PASSWORD`, `JWT_SECRET`, `AWS_ACCESS_KEY`, `AWS_SECRET_KEY`) in a `.env` file or as environment variables.
   - Example `.env`:
     ```bash
     DB_PASSWORD=your_db_password
     JWT_SECRET=your_jwt_secret
     AWS_ACCESS_KEY=your_aws_access_key
     AWS_SECRET_KEY=your_aws_secret_key
     SPRING_MAIL_PASSWORD=your_email_password
     ```

3. **Install Dependencies**:
   ```bash
   mvn clean install
   ```

4. **Set Up PostgreSQL**:
   - Create a database named `coffee_shop`.
   - Update `application-dev.yml` and `application-prod.yml` with your database credentials.

5. **Set Up RabbitMQ**:
   - Install and run RabbitMQ locally or use a cloud instance.
   - Configure connection details in `application-dev.yml` and `application-prod.yml`.

6. **Run the Application**:
   - Start each service in development mode:
     ```bash
     mvn spring-boot:run -pl main-api -Dspring.profiles.active=dev
     mvn spring-boot:run -pl email-service -Dspring.profiles.active=dev
     mvn spring-boot:run -pl sms-service -Dspring.profiles.active=dev
     ```
   - Alternatively, use Docker for production:
     ```bash
     docker-compose up --build
     ```

7. **Test the Application**:
   - Use Postman or `curl` to test endpoints (e.g., `/api/auth/login`, `/api/orders`).
   - Example `curl` for login:
     ```bash
     curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"username":"user","password":"pass"}'
     ```

## Configuration

The project uses environment-specific configuration files located in `src/main/resources`:

- `application-dev.yml`: Development environment settings.
- `application-prod.yml`: Production environment settings (use `.env` or environment variables for sensitive data).
- `application-test.yml`: Testing environment with H2 database.

Key configurations include:

- **JWT**: Configured via `JwtProperties.java` (e.g., `jwt.secret`, `jwt.expiration`).
- **Database**: PostgreSQL for dev/prod, H2 for tests.
- **RabbitMQ**: Queues (`emailQueue`, `smsQueue`) configured in `RabbitMQConfig.java`.
- **Spring Mail**: Email settings for Email Service.
- **AWS SNS**: SNS settings for SMS Service.

Example `application-prod.yml.example`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST:localhost}:5432/coffee_shop
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  mail:
    host: smtp.example.com
    port: 587
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
  rabbitmq:
    host: ${RABBITMQ_HOST:localhost}
    port: 5672
    username: ${RABBITMQ_USERNAME}
    password: ${RABBITMQ_PASSWORD}
aws:
  sns:
    access-key: ${AWS_ACCESS_KEY}
    secret-key: ${AWS_SECRET_KEY}
    region: us-east-1
jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000
```

**Note**: Ensure `.gitignore` excludes `application-prod.yml` and `.env` to prevent committing sensitive data.

## Milestones

The project is organized into the following milestones, tracked via GitHub Projects. Each milestone includes tasks, due dates, and status updates. Closing each issue feels like a rewarding victory, keeping development engaging!

### Milestone 1: Project Setup and Initial Configuration

**Description**: Set up the project structure, configure environment-specific settings, and implement configuration validation for all microservices.

**Tasks**:
- [ ] Create Maven multi-module project structure for Main API, Email Service, and SMS Service.
- [ ] Add environment-specific configuration files (`application-dev.yml`, `application-prod.yml`, `application-test.yml`) for each service.
- [ ] Implement configuration validation with `@ConfigurationProperties` for `jwt`, `spring.datasource`, `spring.mail`, `aws.sns`, and `spring.rabbitmq` (e.g., `JwtProperties.java`).
- [ ] Set up `.gitignore` to exclude sensitive files (e.g., `application-prod.yml`, `.env`).
- [x] Create `README.md` with project overview, setup instructions, and configuration details.
- [ ] Add `application-prod.yml.example` templates for each service to guide production setup.

**Due Date**: July 16, 2025  
**Status**: Partially complete (JWT configs, `.gitignore`, and `README.md` done).

### Milestone 2: JWT Authentication and Endpoint Security

**Description**: Implement JWT-based authentication in the Main API, secure endpoints with Spring Security, and test authentication flows.

**Tasks**:
- [ ] Create `JwtUtil.java` for generating and validating JWT tokens using `JwtProperties`.
- [ ] Implement `AuthService.java` and `AuthController.java` for user login (`/api/auth/login`).
- [ ] Configure Spring Security (`SecurityConfig.java`) to protect endpoints and validate JWT tokens.
- [ ] Create `JwtAuthenticationFilter.java` to process JWT tokens in requests.
- [ ] Implement `CustomUserDetailsService.java` for user authentication (initially with mock users, later with database).
- [ ] Add sample secured endpoint (e.g., `/api/orders`) to test authentication.
- [ ] Write unit tests for JWT generation, validation, and authentication (`JwtUtilTest.java`).
- [ ] Test login and secured endpoints with Postman or `curl` in `dev` environment.

**Due Date**: July 23, 2025  
**Status**: Not started.

### Milestone 3: Inter-Service Communication with RabbitMQ

**Description**: Set up RabbitMQ for communication between Main API, Email Service, and SMS Service to trigger notifications.

**Tasks**:
- [ ] Configure RabbitMQ queues (`emailQueue`, `smsQueue`) in all services (`RabbitMQConfig.java`).
- [ ] Create `MessageProducer.java` in Main API to send email and SMS messages.
- [ ] Implement `EmailConsumer.java` in Email Service to process email messages using Spring Mail.
- [ ] Implement `SmsConsumer.java` in SMS Service to process SMS messages using AWS SNS.
- [ ] Update `pom.xml` in each service to include `spring-boot-starter-amqp`, `spring-boot-starter-mail`, and `aws-java-sdk-sns`.
- [ ] Test message flow by sending notifications from Main API to Email and SMS Services in `dev` environment.
- [ ] Write integration tests to verify RabbitMQ message delivery and processing.

**Due Date**: July 30, 2025  
**Status**: Not started.

### Milestone 4: Database Integration and Business Logic

**Description**: Integrate PostgreSQL for Main API, implement core business logic (e.g., orders, users), and ensure data persistence.

**Tasks**:
- [ ] Set up JPA entities and repositories for users and orders in Main API.
- [ ] Configure PostgreSQL in `application-dev.yml` and `application-prod.yml`, and H2 in `application-test.yml`.
- [ ] Implement `OrderService.java` and `OrderController.java` for CRUD operations on orders.
- [ ] Update `CustomUserDetailsService.java` to fetch users from the database.
- [ ] Add database migration scripts using Flyway or Liquibase.
- [ ] Write unit and integration tests for database operations (`OrderServiceTest.java`).
- [ ] Test CRUD endpoints in `dev` environment with JWT authentication.

**Due Date**: August 6, 2025  
**Status**: Not started.

### Milestone 5: Testing and Validation

**Description**: Ensure all microservices are thoroughly tested, including unit, integration, and end-to-end tests.

**Tasks**:
- [ ] Write unit tests for configuration validation in all services (`ConfigurationValidationTest.java`).
- [ ] Create integration tests for JWT authentication and secured endpoints.
- [ ] Test inter-service communication (Main API → Email/SMS Services) with mock and real RabbitMQ setups.
- [ ] Verify configuration validation failures (e.g., empty `jwt.secret`) in `test` and `prod` profiles.
- [ ] Set up test coverage reports using JaCoCo.
- [ ] Run end-to-end tests for user login, order creation, and notification delivery.

**Due Date**: August 13, 2025  
**Status**: Not started.

### Milestone 6: Production Deployment Preparation

**Description**: Prepare the microservices for production deployment, ensuring secure configurations and logging.

**Tasks**:
- [ ] Verify `application-prod.yml` uses environment variables for sensitive data (e.g., `DB_PASSWORD`, `JWT_SECRET`).
- [ ] Set up production logging in `application-prod.yml` (e.g., `/var/log/coffee-shop/`).
- [ ] Create Dockerfiles for each service (Main API, Email Service, SMS Service).
- [ ] Test production setup locally using `prod` profile:
  ```bash
  java -jar main-api.jar --spring.profiles.active=prod
  ```
- [ ] Document production deployment steps in `README.md`.
- [ ] Ensure `.gitignore` prevents committing sensitive files (e.g., `.env`, `application-prod.yml`).

**Due Date**: August 20, 2025  
**Status**: Not started.

### Milestone 7: Documentation and Final Polish

**Description**: Complete documentation, add final tests, and prepare the project for release.

**Tasks**:
- [ ] Update `README.md` with detailed setup, testing, and deployment instructions.
- [ ] Add API documentation using Springdoc OpenAPI or Swagger.
- [ ] Create a `CONTRIBUTING.md` file for contributor guidelines.
- [ ] Add a `LICENSE` file (e.g., MIT License).
- [ ] Perform final code review and cleanup (e.g., remove unused code, format code).
- [ ] Tag a release on GitHub (e.g., `v1.0.0`).

**Due Date**: August 27, 2025  
**Status**: Not started.

## Contributing

Contributions are welcome! Please read the [CONTRIBUTING.md](CONTRIBUTING.md) file for guidelines on how to contribute to this project. You can start by picking an issue from the GitHub Projects board, assigning it to yourself, and submitting a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

