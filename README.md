# Registration and Login System

## Overview
This project is a Spring Boot application providing a registration and login system. It manages user roles and provides functionalities for both standard users and administrators.

### Key Features
- **User Registration**: New users can register. The first registered user is assigned the `ROLE_ADMIN` role, and subsequent users receive the `ROLE_USER` role.
- **Authentication and Authorization**: Users can log in and are assigned roles which determine their access rights.
- **Admin Functionality**: Admins can view a list of all users, edit user details, change user roles, and delete users. Admins cannot delete themselves or change their own role.
- **User Functionality**: Standard users can view the user list and edit their own details.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Technologies Used](#technologies-used)
3. [Getting Started](#getting-started)
4. [Project Structure](#project-structure)
5. [File Structure](#file-structure)
6. [Detailed Description](#detailed-description)
7. [Endpoints](#endpoints)
8. [Authors](#authors)
9. [Screenshots](#screenshots)
10. [Technical Architecture Overview](#technical-architecture-overview)

## Prerequisites

- Java Development Kit (JDK) 22
- MySQL Server
- Maven

## Technologies Used

- Java
- Spring Boot 3.3.0
- Spring Data JPA
- Spring Security
- Thymeleaf
- MySQL
- Lombok
- Bootstrap

## Getting Started

1. Clone the repository or download the source code.
2. Import the project into your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
3. Create a MySQL database named `registration_db`.
4. Update the `application.properties` file with your MySQL server credentials.
5. Run the application using the `RegistrationLoginSystemApplication` class.
6. Open a browser and navigate to [http://localhost:8080/index](http://localhost:8080/index) to access the home page.
7. Register a new user to get started. The first user registered will be assigned as `ROLE_ADMIN`.



## Project Structure

```bash
registration-login-system
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── dee
│   │   │           └── registration_login_system
│   │   │               ├── config
│   │   │               │   └── SpringSecurity.java
│   │   │               ├── controller
│   │   │               │   └── AuthController.java
│   │   │               ├── dto
│   │   │               │   └── UserDto.java
│   │   │               ├── entity
│   │   │               │   ├── Role.java
│   │   │               │   └── User.java
│   │   │               ├── repository
│   │   │               │   ├── RoleRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               ├── security
│   │   │               │   ├── CustomUserDetails.java
│   │   │               │   └── CustomUserDetailsService.java
│   │   │               ├── service
│   │   │               │   ├── UserService.java
│   │   │               │   ├── ValidationGroups.java
│   │   │               │   └── impl
│   │   │               │       └── UserServiceImpl.java
│   │   │               └── RegistrationLoginSystemApplication.java
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       │   ├── edit_user.html
│   │       │   ├── index.html
│   │       │   ├── login.html
│   │       │   ├── register.html
│   │       │   ├── users.html
│   │       │   └── view.html
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── dee
│                   └── registration_login_system
│                       └── RegistrationLoginSystemApplicationTests.java
└── pom.xml
```
### Dependencies

The `pom.xml` file includes essential Spring Boot dependencies such as:

- `spring-boot-starter-data-jpa`
- `spring-boot-starter-thymeleaf`
- `spring-boot-starter-validation`
- `spring-boot-starter-web`
- `spring-boot-starter-security`
- `thymeleaf-extras-springsecurity6`
- `mysql-connector-j`

### Configuration

- **Database**: Configured to use a MySQL database with details provided in `application.properties`.
- **Spring Security**: Configured to handle user roles and permissions as defined in the `SpringSecurity` class.

## File Structure

- src/main/java/com/dee/registration_login_system:
  - **Controllers**: Manages web requests and user interactions (`AuthController`).
  - **Entities**: Defines the data models for User and Role (`User`, `Role`).
  - **DTOs**: Data Transfer Objects used for user data (`UserDto`).
  - **Repositories**: Interfaces for data access operations (`UserRepository`, `RoleRepository`).
  - **Security**: Configuration for security and custom user details service (`SpringSecurity`, `CustomUserDetails`, `CustomUserDetailsService`).
  - **Services**: Business logic and service implementations (`UserService`, `UserServiceImpl`).


- src/main/resources/templates:
  - **Thymeleaf Templates**: HTML templates for user interface (`index.html`, `login.html`, `register.html`, `edit_user.html`, `users.html`).


## Detailed Description

### 1. Application Configuration

- **application.properties**: Contains the configuration settings for the application such as the application name, database connection properties, and logging level for Spring Security.

### 2. Security Configuration

- **SpringSecurity**:
    - Configures the security filter chain and password encoding.
    - Sets up role-based access control, specifying which endpoints are accessible to which roles.
    - Customizes login and logout processes.

### 3. Entity Definitions

- **User**: Represents the user entity with fields for ID, name, email, password, and roles.
- **Role**: Represents the role entity with fields for ID and name. Users can have multiple roles.

### 4. Data Access

- **UserRepository**: Provides CRUD operations for the User entity.
- **RoleRepository**: Provides CRUD operations for the Role entity.

### 5. Service Layer

- **UserService**: Interface defining the operations for user management.
- **UserServiceImpl**: Implementation of `UserService` handling user registration, updates, deletion, and role management.

### 6. Web Layer

- **AuthController**:
    - Manages user registration, login, and profile management.
    - Handles role assignments and user listing for admins.
    - Provides endpoints for changing user roles and viewing user details.

### 7. Custom Security Details

- **CustomUserDetails**: Implements `UserDetails` to include user-specific information like ID and full name.
- **CustomUserDetailsService**: Loads user details from the database for authentication.

### 8. HTML Templates

- **index.html**: Home page template.
- **login.html**: Login form for user authentication.
- **register.html**: Registration form for new users.
- **edit_user.html**: Form for editing user details.
- **users.html**: Admin page for listing and managing users.

## Endpoints

### Public Endpoints

- **GET /index**: Landing page.
- **GET /register**: Shows the registration form.
- **POST /register/save**: Handles new user registration.
- **GET /login**: Shows the login form.

### User Endpoints

- **GET `/users`**: Lists all users (accessible to both ADMIN and USER roles).
- **GET `/user/{userId}/edit`**: Form for editing user details (accessible to ADMIN and the specific USER).
- **POST `/user/{userId}`**: Handles updates to user details.
- **GET `/user/{userId}/view`**: Displays detailed view of a user (accessible to ADMIN).

### Admin Endpoints

- **GET `/user/{userId}/delete`**: Deletes a user (ADMIN only).
- **POST `/user/{userId}/changeRole`**: Changes the role of a user (ADMIN only).

## Authors

Dewald van den Berg - [GitHub](https://github.com/Dewald15)


## Screenshots
<div style="text-align: center;">

### 1. **index.html**

![Screenshot](src/main/resources/static/1.png)

### 2. **register.html**

![Screenshot](src/main/resources/static/2.png)

### 3. **login.html**

![Screenshot](src/main/resources/static/3.png)

### 4. **users.html (logged in with ADMIN role)**

![Screenshot](src/main/resources/static/4.png)

### 5. **edit_user.html**

![Screenshot](src/main/resources/static/5.png)

### 6. **view_user.html**

![Screenshot](src/main/resources/static/6.png)

### 7. **users.html (Change Role Dropdown)**

![Screenshot](src/main/resources/static/7.png)

### 8. **users.html (logged in with USER role)**

![Screenshot](src/main/resources/static/8.png)

### 9. **login.html (after logout)**

![Screenshot](src/main/resources/static/9.png)
</div>

# Technical Architecture Overview

## Application Lifecycle with Spring Boot

When you run your Spring Boot application, several critical steps occur in the background to initialize and configure your application components. Here's a detailed walkthrough of these operations:

1. **Application Entry Point**
- Class: `RegistrationLoginSystemApplication`
- Method: `main`
```bash
public static void main(String[] args) {
  SpringApplication.run(RegistrationLoginSystemApplication.class, args);
}
```
- Operation:
  - The `SpringApplication.run` method starts the entire Spring Boot framework.
  - It bootstraps the application, setting up the default configurations, starting the Spring context, and performing classpath scans to identify and register beans.

2. **Spring Boot Auto-Configuration**
- Operation:
  - Spring Boot uses auto-configuration to simplify the setup process. It automatically configures your application based on the dependencies present on the classpath.
  - It creates and registers default beans and configurations for the application, such as data source configuration for databases, security settings, and web server setup.

3. **Spring Application Context Initialization**
- Operation:
  - Spring Boot creates an `ApplicationContext`, which is a central container for managing beans and their lifecycles.
  - The context scans for all classes annotated with Spring stereotypes (`@Component`, `@Service`, `@Repository`, `@Controller`, etc.), and it registers them as beans.

4. **Component Scanning and Bean Creation**
- Operation:
  - Spring Boot scans the base package and its sub-packages for Spring-managed components.
  - Components are created and managed as beans within the Spring context.
  - Beans are instantiated, dependencies are injected, and configuration properties are set up.

5. **Dependency Injection**
- Operation:
  - Spring uses Dependency Injection (DI) to wire together the components of the application.
  - It resolves dependencies between beans and injects required dependencies into the components, ensuring they are ready to use.

6. **Database Initialization**
- Configuration
  - File: `application.properties`
  - Settings: 
  ```bash
  spring.datasource.url=jdbc:mysql://localhost:3306/registration_db
  spring.datasource.username=root
  spring.datasource.password=your_password
  spring.jpa.hibernate.ddl-auto=update
  ```
- Operation: 
  - Spring Boot sets up the data source and connects to the MySQL database using the provided credentials.
  - It uses JPA to map the entities (`User`, `Role`) to database tables.
  - Hibernate, as the JPA implementation, performs schema updates or validations based on the `spring.jpa.hibernate.ddl-auto` setting.

7. **Security Configuration**
- Class: `SpringSecurity`
- Operation:
  - This class configures the security filter chain, defining which endpoints require authentication and what roles can access specific resources.
  - It also sets up password encoding and custom login/logout behaviors.

8. **Request Handling and MVC**
- Thymeleaf Templates: `index.html`, `login.html`, `register.html`, `edit_user.html`, `users.html`
- Controller: `AuthController`
- Operation: 
  - Spring Boot uses the DispatcherServlet as the front controller to handle incoming HTTP requests.
  - The `AuthController` defines the endpoints and manages the interactions between the user interface and the backend.
  - Requests are mapped to the appropriate controller methods based on URL patterns.
  - Controller methods return views (Thymeleaf templates) or data, which are rendered and sent back to the client.

9. **Entity Management**
- Entities: `User`, `Role`
- Repositories: `UserRepository`, `RoleRepository`
- Operation
  - JPA repositories provide CRUD operations for managing `User` and `Role` entities.
  - They handle data access and persistence operations, leveraging Spring Data JPA to simplify database interactions.

10. **Service Layer Operations**
- Services: `UserService`, `UserServiceImpl`
- Operation: 
  - The service layer encapsulates the business logic for user management.
  - It handles user registration, updates, deletion, and role changes.
  - `UserServiceImpl` performs operations such as encoding passwords, mapping DTOs to entities, and managing user sessions.

11. **Custom User Details and Authentication**
- Security Classes: `CustomUserDetails`, `CustomUserDetailsService`
- Operation:
  - `CustomUserDetailsService` implements `UserDetailsService` to load user-specific data during authentication.
  - `CustomUserDetails` encapsulates user information such as roles and credentials, which Spring Security uses for authorization checks.

12. **Post-Initialization**
- Operation:
  - After initializing all components, Spring Boot starts the embedded web server (e.g., Tomcat) and listens for incoming HTTP requests.
  - The application is now fully initialized and ready to handle user interactions and perform its intended functions.

## Component Interactions

Here's how the major components interact during a typical request flow:

### User Registration Flow

**User Accesses Registration Page:**
- **Request:** `GET /register`
- **Controller:** `AuthController.showRegistrationForm`
- **View:** `register.html`
- **Operation:** 
  - Displays the registration form.

**User Submits Registration Form:**
- **Request:** `POST /register/save`
- **Controller:** `AuthController.registration`
- **Service:** `UserService.saveUser`
- **Repository:** `UserRepository`, `RoleRepository`
- **Operation:** 
  - Validates the user data.
  - Encodes the password.
  - Assigns a role (`ROLE_ADMIN` for the first user, `ROLE_USER` for others).
  - Saves the new user to the database.

### User Login Flow

**User Accesses Login Page:**
- **Request:** `GET /login`
- **Controller:** `AuthController.login`
- **View:** `login.html`
- **Operation:**
  - Displays the login form.

**User Submits Login Credentials:**
- **Request:** `POST /login`
- **Security Filter:** `UsernamePasswordAuthenticationFilter`
- **Service:** `CustomUserDetailsService.loadUserByUsername`
- **Operation:**
  - Authenticates the user against the database.
  - Retrieves user details and roles.
  - On success, redirects to the user list page (`/users`).

### User Management Flow (Admin)

**Admin Views User List:**
- **Request:** `GET /users`
- **Controller:** `AuthController.users`
- **Service:** `UserService.findAllUsers`, `UserService.findAllRoles`
- **View:** `users.html`
- **Operation:**
  - Fetches all users and their roles from the database.
  - Displays the user list with options to edit, view, or delete users.

**Admin Changes User Role:**
- **Request:** `POST /user/{userId}/changeRole`
- **Controller:** `AuthController.changeUserRole`
- **Service:** `UserService.changeUserRole`
- **Operation:**
  - Updates the user's roles in the database.

**Admin Deletes a User:**
- **Request:** `GET /user/{userId}/delete`
- **Controller:** `AuthController.deleteUser`
- **Service:** `UserService.deleteUser`
- **Operation:**
  - Removes the user from the database.

## Sequence Diagram

Here is a simplified sequence diagram for a typical request-response flow in the application:
```bash
User -> Browser -> Spring Boot Application -> DispatcherServlet -> Controller -> Service -> Repository -> Database
```
1. **User Interaction:** The user initiates a request through the browser (e.g., accessing the registration page).
2. **Request Handling:** The request is received by the Spring Boot application, where the `DispatcherServlet` dispatches it to the appropriate `Controller`.
3. **Controller Logic:** The controller processes the request, interacts with the `Service` layer if necessary, and returns a view or data.
4. **Service Operations:** The service layer performs business logic and may call `Repository` methods for data access.
5. **Data Access:** Repositories interact with the database to retrieve or update data.
6. **Response Generation:** The response is generated and sent back to the browser.





























