# 📚 Course Manager (Backend)

## 📋 Overview

**Course Manager** is a web application designed to help instructors manage lesson plans for training institutes. The primary objective of this application is to provide a platform for instructors to create, manage, and share lesson plans with students. This repository contains the Spring Boot backend for the **Course Manager** application. The [frontend](https://github.com/impradp/course-manager-frontend) is hosted in a separate repository.

## ✨ Features

- **User Authentication**: Secure login and signup functionality with JWT (JSON Web Tokens).
- **Course Management**: APIs to create, update, and manage lesson plans.
- **Email Integration**: Send emails for account verification, password reset, etc.
- **File Upload**: Handle file uploads for user profile images.
- **API Documentation**: Automatically generated API documentation using SpringDoc OpenAPI (Swagger UI).
- **Database Integration**: MySQL database with Spring Data JPA for data persistence.

## 🛠️ Technologies Used

- **Spring Boot** (v3.2.3)
- **Spring Security**: For authentication and authorization.
- **Spring Data JPA**: For database interactions.
- **MySQL**: As the primary database.
- **JWT (JSON Web Tokens)**: For token-based authentication.
- **Spring Mail**: For sending emails.
- **SpringDoc OpenAPI**: For API documentation.
- **Apache Commons Lang**: For utility functions.
- **Java 21**: As the programming language.

## 🚀 Getting Started

### Prerequisites

- Java 21
- MySQL (or any compatible database)
- Gradle (for building the project)

### Installation

1. Clone the repository:

   ```bash
   git clone git@github.com:impradp/CourseManager.git
   ```
2. Navigate to the project directory:
   ```bash
    cd CourseManager
   ```
3. Configure the environment variables in ```application.properties```:
   - Update the database connection details (```spring.datasource.url```, ```spring.datasource.username```, ```spring.datasource.password```).
   - Set the JWT secret key (```jwt.secret```). 
   - Configure email settings (```spring.mail.username```,```spring.mail.password```). 
   - Set the file upload directory (```app.file.upload-dir```).
4. Build the project:
    ```bash
    ./gradlew build
   ```
5. Run the application:
    ```bash
    ./gradlew bootRun
   ```

### Running with Docker

1. Ensure Docker and Docker Compose are installed.
2. Run the following command to start the application and MySQL database:

   ```bash
   docker-compose up --build
   ```
3. The application will be available at ```http://localhost:8080```.

## 📁 Project Structure

The project is structured as follows:

- **`src/main/java/com/company/coursemanager`**: Contains the main application code.
  - **`auth`**: Authentication-related classes (e.g., JWT, security configuration).
  - **`config`**: Configuration classes (e.g., CORS, security, etc.).
  - **`email`**: Email service and related components.
  - **`file`**: File upload and management functionality.
  - **`filter`**: Custom filters (e.g., JWT filter).
  - **`users`**: User management functionality.
  - **`utils`**: Utility classes and helper functions.
  - **`CourseManagerApplication.java`**: Main application class.
- **`src/main/resources`**: Contains configuration files.
  - **`application.properties`**: Application configuration and environment variables.
- **`src/test`**: Contains unit and integration tests.

## 📚 API Documentation

The API documentation is automatically generated using SpringDoc OpenAPI. After running the application, you can access the Swagger UI at:
```bash
  http://localhost:8080/swagger-ui.html
```

## 📄 Environment Variables

The following environment variables need to be configured in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/course_manager
spring.datasource.username=root
spring.datasource.password=yourpassword
jwt.secret=yourjwtsecret
spring.mail.username=youremail@gmail.com
spring.mail.password=youremailpassword
app.file.upload-dir=D:/uploads/profile-images/
```


## 👥 Contributors

- **Pradip Puri**
  - Website: [impradp.vercel.app](https://impradp.vercel.app)
  - LinkedIn: [linkedin.com/in/impradp](https://linkedin.com/in/impradp)
  - Email: [impradp@gmail.com](mailto:impradp@gmail.com)

## 📄 License

This project is proprietary and confidential. Unauthorized copying, modification, distribution, or use of this software, via any medium, is strictly prohibited.