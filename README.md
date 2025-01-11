# User Service

## Overview
The User Service is a Spring Boot application that provides user management functionalities. It includes features such as user authentication, authorization, and user data management.

## Technologies Used
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- Maven

## Prerequisites
- Java 17 or higher
- Maven 3.6.0 or higher
- MySQL database

## Setup Instructions

### Database Configuration
1. Create a MySQL database.
2. Update the `application.properties` file with your database configuration:
    ```ini
    spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
    spring.datasource.username=your_database_username
    spring.datasource.password=your_database_password
    ```

### Build and Run the Application
1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/userservice.git
    cd userservice
    ```
2. Build the project using Maven:
    ```sh
    mvn clean install
    ```
3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

### Accessing the Application
- The application will be accessible at `http://localhost:8181`.

## Security Configuration
The application uses Spring Security for authentication and authorization. The security configuration is defined in the `SecurityConfiguration` class.

## Endpoints
- `/oauth2/**`: Handles OAuth2 authentication.
- `/api/**`: Handles user-related operations.

## Logging
Logging is configured in the `application.properties` file. You can adjust the logging levels as needed.

## Troubleshooting
- **502 Bad Gateway**: Ensure the application is running and accessible. Check the proxy server configuration if using one.
- **User Not Found**: Verify that the user exists in the database and the `UserDetailsService` is correctly implemented.
