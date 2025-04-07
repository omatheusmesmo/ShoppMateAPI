# ShoppMate - Your Smart Shopping List Manager

ShoppMate is a RESTful API designed to help users manage their shopping lists efficiently. It allows users to create, update, and delete shopping lists, add items to lists, manage user permissions for lists, manage categories and units, and more. The API also includes user authentication and authorization features.

## Table of Contents

* [Features](#features)
* [Technologies Used](#technologies-used)
* [Getting Started](#getting-started)
    * [Prerequisites](#prerequisites)
    * [Installation](#installation)
    * [Environment Variables](#environment-variables)
    * [Running the Application](#running-the-application)
* [API Endpoints](#api-endpoints)
    * [Authentication (/auth)](#authentication-auth)
    * [Shopping Lists (/shopplists)](#shopping-lists-shopplists)
    * [Shopping List Items (/shopplist/{shopplistId}/items)](#shopping-list-items-shopplistshopplistiditems)
    * [Shopping List User Permissions (/shopplist/{shopplistId}/permissions)](#shopping-list-user-permissions-shopplistshopplistidpermissions)
    * [Categories (/category)](#categories-category)
    * [Units (/unit)](#units-unit)
    * [Items (/item)](#items-item)
* [Testing](#testing)
* [Project Architecture](#architecture)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)

## Features

* **Shopping List Management:**
    * Create, read, update, and delete shopping lists.
* **Item Management:**
    * Add, read, update, and delete items within a shopping list.
* **User Permission Management:**
    * Grant, read, update, and revoke user permissions for specific shopping lists.
* **Category Management:**
    * Create, read, update, and delete item categories.
* **Unit Management:**
    * Create, read, update, and delete units of measurement.
* **RESTful API:**
    * Well-defined RESTful endpoints for easy integration.
* **Error Handling:**
    * Centralized error handling for common exceptions.
* **Database Integration:**
    * Uses PostgreSQL for persistent data storage.
* **Auditing:**
    * Tracks creation and modification timestamps for entities.
* **Code Quality:**
    * Includes unit tests for core services.
* **Authentication and Authorization:**
    * Secure user authentication and authorization using JWT (JSON Web Tokens).
* **API Documentation:**
    * Integrated with SpringDoc and Swagger for clear and interactive API documentation.
* **Database Versioning:**
    * Uses Flyway for database migrations, ensuring consistency and ease of updates.

## Technologies Used

* Java: Core programming language.
* Spring Boot: Framework for building the application.
* Spring Web: For creating RESTful web services.
* Spring Data JPA: For database access and management.
* PostgreSQL: Relational database for data storage.
* Lombok: For reducing boilerplate code.
* JUnit 5: For unit testing.
* Mockito: For mocking dependencies in tests.
* Maven: For project management and dependency resolution.
* Swagger (SpringDoc): For API documentation.
* Spring Security: For authentication and authorization.
* OAuth2: For authentication and authorization.
* Nimbus-JOSE-JWT: For JWT handling.
* Flyway: For database migration.
* BCrypt: For password encoding.

## Getting Started

### Prerequisites

* Java Development Kit (JDK): Version 17 or higher.
* Maven: For building and managing the project.
* PostgreSQL: A running PostgreSQL database instance.
* Git: For version control.

### Installation

1.  Clone the repository:

    ```bash
    git clone https://github.com/omatheusmesmo/ShoppMateAPI.git
    cd ShoppMateAPI
    ```

2.  Configure the Database:
    * **Create a database in your PostgreSQL instance.**
    * Update the `application.properties` file with your database credentials (see [Environment Variables](#environment-variables)).

3.  Build the project:

    ```bash
    mvn clean install
    ```

### Environment Variables

You need to set the following environment variables in your `application.properties` file (or as system environment variables):

```properties
# Database Configurations
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password

# JWT Secret Key
secret.key.jwt=your_secret_key_here

# Flyway
spring.flyway.enabled=true
```

* `spring.datasource.url`: The JDBC URL for your PostgreSQL database.
* `spring.datasource.username`: Your PostgreSQL database username.
* `spring.datasource.password`: Your PostgreSQL database password.
* `secret.key.jwt`: A secret key used for JWT token generation.
* `spring.flyway.enabled`: Enable or disable flyway.

### Running the Application

1.  Navigate to the project root directory.
2.  Run the application:

    ```bash
    mvn spring-boot:run
    ```

    Or, if you prefer to run the JAR file directly:

    ```bash
    java -jar target/shoppmate-0.0.1-SNAPSHOT.jar
    ```

The application will start on `http://localhost:8080`.

## API Endpoints

### Authentication (/auth)

* **POST /auth/register/userDetailsService:** Register a new user.

  ```json
  {
  "email": "user@example.com",
  "fullName": "User Name",
  "password": "password"
  }
    ```

* **POST /auth/login:** User login.

  ```json
  {
  "email": "user@example.com",
  "password": "password"
  }
    ```

* **GET /auth/hello:** Test endpoint.

### Shopping Lists (/shopplists)

* **GET /shopplists:** Get all shopping lists.
* **POST /shopplists:** Create a new shopping list.

  ```json
  {
  "name": "My Shopping List",
  "owner": { "id": 1 }
  }
    ```

* **DELETE /shopplists/{id}:** Delete a shopping list by ID.
* **PUT /shopplists/{id}:** Update a shopping list.

  ```json
  {
  "id": 1,
  "name": "Updated Shopping List",
  "owner": { "id": 1 }
  }
    ```

### Shopping List Items (/shopplist/{shopplistId}/items)

* **GET /shopplist/{shopplistId}/items:** Get all items for a specific shopping list.
* **GET /shopplist/{shopplistId}/items/{id}:** Get a specific item for a specific shopping list.
* **POST /shopplist/{shopplistId}/items:** Add an item to a shopping list.

  ```json
  {
  "item": { "id": 1 },
  "quantity": 2,
  "shoppList": { "id": 1 }
  }
    ```

* **DELETE /shopplist/{shopplistId}/items/{id}:** Delete an item from a shopping list.
* **PUT /shopplist/{shopplistId}/items/{id}:** Update an item in a shopping list.

  ```json
  {
  "id": 1,
  "item": { "id": 1 },
  "quantity": 3,
  "shoppList": { "id": 1 }
  }
    ```

### Shopping List User Permissions (/shopplist/{shopplistId}/permissions)

* **GET /shopplist/{shopplistId}/permissions:** Get all permissions for a specific shopping list.
* **GET /shopplist/{shopplistId}/permissions/{id}:** Get a specific permission for a specific shopping list.
* **POST /shopplist/{shopplistId}/permissions:** Add a user permission to a shopping list.

  ```json
  {
  "user": { "id": 1 },
  "shoppList": { "id": 1 }
  }
    ```

* **DELETE /shopplist/{shopplistId}/permissions/{id}:** Delete a user permission from a shopping list.
* **PUT /shopplist/{shopplistId}/permissions/{id}:** Update a user permission in a shopping list.

  ```json
  {
  "id": 1,
  "user": { "id": 1 },
  "shoppList": { "id": 1 }
  }
    ```

### Categories (/category)

* **GET /category:** Get all categories.
* **POST /category:** Add a new category.

  ```json
  {
  "name": "Category Name"
  }
    ```

* **DELETE /category/{id}:** Delete a category by ID.
* **PUT /category:** Update a category.

  ```json
  {
  "id": 1,
  "name": "Category Name"
  }
    ```

### Units (/unit)

* **GET /unit:** Get all units.
* **POST /unit:** Add a new unit.

  ```json
  {
  "name": "Unit Name",
  "symbol": "un"
  }
    ```

* **DELETE /unit/{id}:** Delete a unit by ID.
* **PUT /unit:** Update a unit.

  ```json
  {
  "id": 1,
  "name": "Unit Name",
  "symbol": "un"
  }
    ```

### Items (/item)

* **GET /item:** Get all items.
* **POST /item:** Add a new item.

  ```json
  {
  "name": "Item Name",
  "unit": { "id": 1 },
  "category": { "id": 1 }
  }
    ```

* **DELETE /item/{id}:** Delete an item by ID.
* **PUT /item:** Update an item.

  ```json
  {
  "id": 1,
  "name": "Item Name",
  "unit": { "id": 1 },
  "category": { "id": 1 }
  }
    ```

## Testing

To run the unit tests:

```bash
mvn test
```

## Architecture

ShoppMate API adopts a domain-driven architecture, where the code is organized around the main business areas. Each domain (such as Authentication, Categories, Items, Shopping Lists, Units, and Users) has its own internal structure, following a pattern that includes:

```
├── auth/        (Authentication Domain)
│   ├── configs/
│   ├── controller/ (Responsible for receiving and responding to authentication-related requests)
│   ├── service/    (Contains the business logic for authentication)
│   └── ...
├── category/    (Categories Domain)
│   ├── controller/ (Responsible for category requests)
│   ├── entity/     (Represents the category entity in the domain)
│   ├── repository/ (Responsible for persisting category data)
│   └── service/    (Business logic for categories)
├── item/        (Items Domain)
│   ├── controller/
│   ├── entity/
│   ├── repository/
│   └── service/
├── list/        (Shopping Lists Domain)
│   ├── controller/
│   ├── entity/
│   ├── repository/
│   └── service/
├── unit/        (Units Domain)
│   ├── controller/
│   ├── entity/
│   ├── repository/
│   └── service/
├── user/        (Users Domain)
│   ├── controller/
│   ├── entity/
│   ├── repository/
│   └── service/
├── shared/      (Concepts shared between domains)
│   └── domain/
├── utils/       (General utility classes)
└── ShoppMateApplication.java (Spring Boot application entry point)
```

## Contributing

Contributions are welcome! Please follow these steps:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Make your changes and commit them.
4.  Push your changes to your fork.
5.  Submit a pull request.

## License

This project is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
Public License. See LICENSE.md

## Contact

If you have any questions or suggestions, feel free to contact me at matheus.6148@gmail.com
