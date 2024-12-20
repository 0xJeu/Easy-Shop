# E-Commerce REST API

This project is a RESTful API backend for an e-commerce platform built with Spring Boot. It provides endpoints for managing products, categories, shopping carts, user profiles, and authentication.

## Features

- User authentication and authorization
- Product management
- Category management
- Shopping cart functionality
- User profile management
- Role-based access control (Admin and User roles)

## API Endpoints

### Authentication
- `POST /login` - Authenticate user and receive JWT token
- `POST /register` - Register new user account

### Products
- `GET /products` - Get all products with optional filtering
    - Query parameters:
        - `cat` - Filter by category ID
        - `minPrice` - Filter by minimum price
        - `maxPrice` - Filter by maximum price
        - `color` - Filter by color
- `GET /products/{id}` - Get product by ID
- `POST /products` - Create new product (Admin only)
- `PUT /products/{id}` - Update product (Admin only)
- `DELETE /products/{id}` - Delete product (Admin only)

### Categories
- `GET /categories` - Get all categories
- `GET /categories/{id}` - Get category by ID
- `GET /categories/{categoryId}/products` - Get all products in a category
- `POST /categories` - Create new category (Admin only)
- `PUT /categories/{id}` - Update category (Admin only)
- `DELETE /categories/{id}` - Delete category (Admin only)

### Shopping Cart
- `GET /cart` - Get current user's shopping cart
- `POST /cart/products/{id}` - Add product to cart
- `PUT /cart/products/{id}` - Update product quantity in cart
- `DELETE /cart` - Clear shopping cart

### Profile
- `GET /profile` - Get current user's profile
- `PUT /profile` - Update user profile

## Security

The API uses JWT (JSON Web Token) for authentication. Protected endpoints require a valid JWT token in the Authorization header:

```
Authorization: Bearer <token>
```

## Role-Based Access

- **Admin Role**: Has full access to all endpoints, including product and category management
- **User Role**: Can access shopping cart and profile endpoints, view products and categories
- **Public**: Can view products and categories without authentication

## Error Handling

The API uses standard HTTP status codes and includes error messages in the response:

- `200 OK` - Success
- `201 Created` - Resource created
- `400 Bad Request` - Invalid request
- `401 Unauthorized` - Authentication required
- `403 Forbidden` - Insufficient permissions
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

## Dependencies

- Spring Boot
- Spring Security
- Spring Data
- JWT Authentication
- SLF4J for logging

## Getting Started

1. Clone the repository
2. Configure your database settings in `application.properties`
3. Run the application using:
```bash
./mvnw spring-boot:run
```

## Development

The project follows a standard Spring Boot architecture:

- Controllers: Handle HTTP requests and responses
- Services: Contain business logic
- DAOs: Handle data access
- Models: Define data structures
- Security: Configure authentication and authorization