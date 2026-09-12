# Inventory Service

Microservicio Java 21 + Spring Boot para administrar productos e inventario.

## Endpoints
- GET `/api/products`
- GET `/api/products/{id}`
- POST `/api/products`
- PUT `/api/products/{id}`
- DELETE `/api/products/{id}`
- GET `/actuator/health`

## Ejemplo
```json
{"name":"Laptop Lenovo","category":"Laptops","price":2499.90,"stock":10}
```

Puerto local: `8081`.
