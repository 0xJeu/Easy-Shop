# E-Commerce Web Application

## Overview

This is a Spring Boot-based e-commerce web application that provides a comprehensive set of RESTful API endpoints for managing users, products, categories, and shopping carts.

## Features

### Authentication
- User registration and login
- JWT-based authentication
- Role-based access control (USER and ADMIN roles)

### Product Management
- Search products with multiple filters
- Get product details
- Add, update, and delete products (ADMIN only)

### Category Management
- List all categories
- Get category details
- Retrieve products by category
- Create, update, and delete categories (ADMIN only)

### Shopping Cart Functionality
- View shopping cart
- Add products to cart
- Clear entire cart

## API Endpoints

### Authentication Endpoints
- `POST /login`: User login
- `POST /register`: User registration

### Product Endpoints
- `GET /products`: Search products
    - Optional query parameters:
        - `cat`: Filter by category ID
        - `minPrice`: Minimum price filter
        - `maxPrice`: Maximum price filter
        - `color`: Filter by color
- `GET /products/{id}`: Get product by ID
- `POST /products`: Add a new product (ADMIN only)
- `PUT /products/{id}`: Update a product (ADMIN only)
- `DELETE /products/{id}`: Delete a product (ADMIN only)

### Category Endpoints
- `GET /categories`: List all categories
- `GET /categories/{id}`: Get category by ID
- `GET /categories/{categoryId}/products`: Get products in a specific category
- `POST /categories`: Create a new category (ADMIN only)
- `PUT /categories/{id}`: Update a category (ADMIN only)
- `DELETE /categories/{id}`: Delete a category (ADMIN only)

### Shopping Cart Endpoints
- `GET /cart`: View current user's shopping cart
- `POST /cart/products/{id}`: Add a product to cart
- `DELETE /cart`: Clear the entire shopping cart

## Security

The application implements role-based access control:
- `ROLE_ADMIN`: Full access to create, update, and delete products and categories
- `ROLE_USER`: Can manage shopping cart and view products
- `permitAll()`: Allows public access to product and category viewing

## Authentication Flow

1. Register a new user via `/register`
2. Login via `/login` to receive a JWT token
3. Include the JWT token in the `Authorization` header for subsequent requests

## Technologies Used

- Spring Boot
- Spring Security
- JWT Authentication
- RESTful API Design
- Dependency Injection

## Error Handling

The application uses standard HTTP status codes:
- 200 OK: Successful requests
- 201 CREATED: Successful resource creation
- 204 NO CONTENT: Successful deletion
- 400 BAD REQUEST: Validation errors
- 401 UNAUTHORIZED: Authentication failures
- 403 FORBIDDEN: Insufficient permissions
- 404 NOT FOUND: Resource not found
- 500 INTERNAL SERVER ERROR: Unexpected server errors

## Logging

The application uses SLF4J for logging errors and important events.

## Setup and Installation

1. Clone the repository
2. Configure database connection in `application.properties`
3. Run database migrations
4. Build the project with Maven or Gradle
5. Start the application

## Contributing

Please read CONTRIBUTING.md for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.