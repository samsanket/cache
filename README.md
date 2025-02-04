# Product Management System with Custom Caching

This project is a Spring Boot application that provides a Product Management System using PostgreSQL as the database and a custom caching system to improve performance.

## Features

- CRUD Operations for Products (Create, Read, Update, Delete)
- Custom In-Memory Cache to optimize database calls
- Automatic Cache Expiry after a set duration
- Spring Boot REST API for easy integration
- PostgreSQL Database for persistent storage

## Technologies Used

- Java 17+
- Spring Boot 3+
- Spring Data JPA
- PostgreSQL 15
- Gradle

## System Architecture

### 1. Database Layer (PostgreSQL)

The products table stores product details such as:
- `id` (Primary Key)
- `name`
- `price`

### 2. Repository Layer (Spring Data JPA)

Handles communication with the PostgreSQL database using `JpaRepository`.

### 3. Service Layer (Business Logic & Caching)

- Implements custom caching to store frequently accessed products in memory.
- Reduces database load by returning cached data when available.
- Automatically expires cache entries after a specified time.

### 4. Controller Layer (REST API)

Exposes endpoints to interact with the system:

- **GET /products/{id}** - Fetch product by ID (checks cache first, then DB)
- **GET /products** - Fetch all products
- **POST /products** - Create a new product
- **PUT /products/{id}** - Update a product
- **DELETE /products/{id}** - Remove a product

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/your-repo-url.git
cd your-repo
```    

## API Endpoints

### 1. **GET /products**
- **Description**: Fetch all products.
- **Response**:
    - **200 OK**: A list of all products in the system.
    - **Example Response**:
      ```json
      [
        {
          "id": 1,
          "name": "Product A",
          "price": 29.99
        },
        {
          "id": 2,
          "name": "Product B",
          "price": 49.99
        }
      ]
      ```

### 2. **GET /products/{id}**
- **Description**: Fetch a product by ID.
- **Parameters**:
    - `id` (path parameter): The unique ID of the product to fetch.
- **Response**:
    - **200 OK**: The product object if found.
    - **404 Not Found**: If the product with the specified ID does not exist.
    - **Example Request**:
      ```bash
      GET /products/1
      ```
    - **Example Response**:
      ```json
      {
        "id": 1,
        "name": "Product A",
        "price": 29.99
      }
      ```

### 3. **POST /products**
- **Description**: Create a new product.
- **Request Body**:
    - `name` (string): The name of the product.
    - `price` (decimal): The price of the product.
- **Response**:
    - **201 Created**: The created product with a generated ID.
    - **Example Request**:
      ```json
      {
        "name": "New Product",
        "price": 19.99
      }
      ```
    - **Example Response**:
      ```json
      {
        "id": 3,
        "name": "New Product",
        "price": 19.99
      }
      ```

### 4. **PUT /products/{id}**
- **Description**: Update an existing product by ID.
- **Parameters**:
    - `id` (path parameter): The unique ID of the product to update.
- **Request Body**:
    - `name` (string): The updated name of the product.
    - `price` (decimal): The updated price of the product.
- **Response**:
    - **200 OK**: The updated product.
    - **404 Not Found**: If the product with the specified ID does not exist.
    - **Example Request**:
      ```json
      {
        "name": "Updated Product",
        "price": 39.99
      }
      ```
    - **Example Response**:
      ```json
      {
        "id": 1,
        "name": "Updated Product",
        "price": 39.99
      }
      ```

### 5. **DELETE /products/{id}**
- **Description**: Delete a product by ID.
- **Parameters**:
    - `id` (path parameter): The unique ID of the product to delete.
- **Response**:
    - **200 OK**: A message indicating successful deletion.
    - **404 Not Found**: If the product with the specified ID does not exist.
    - **Example Request**:
      ```bash
      DELETE /products/1
      ```
    - **Example Response**:
      ```json
      {
        "message": "Product with ID 1 deleted successfully"
      }
      ```


## Benefits of Custom Caching

- 🚀 **Faster Response Times** – Frequently requested products are served instantly.
- 🔄 **Reduced Database Load** – Minimizes queries to PostgreSQL, improving scalability.
- ⚡ **Improved Performance** – Cached data retrieval is much quicker than database access.
- ⏳ **Automatic Expiry** – Ensures the cache does not store outdated data for too long.
- 🔧 **Simple & Lightweight** – No external dependencies like Redis required.

## Future Enhancements

- Implement Redis for distributed caching.
- Add Pagination & Sorting for fetching products.
