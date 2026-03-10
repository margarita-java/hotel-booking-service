# Hotel Booking Service

Backend service for hotel booking system with admin panel and statistics collection.

## Technologies

- Java 17
- Spring Boot 3.1.5
- Spring Web MVC
- Spring Data JPA (PostgreSQL)
- Spring Security (Basic Auth)
- Spring Kafka
- Spring Data MongoDB
- MapStruct
- Lombok
- Maven
- Docker / Docker Compose

## Features

- User registration and authentication (BCrypt password encoding)
- Role-based access (USER / ADMIN)
- CRUD operations for hotels, rooms, users
- Hotel rating system (formula-based recalculation)
- Booking rooms with availability checks (overlapping bookings and unavailable dates)
- Filtering and pagination for hotels and rooms (using JPA Specifications)
- Statistics collection via Kafka events (user registration, booking) stored in MongoDB
- CSV export of statistics (admin only)
- Global exception handling

## Project Structure
src/main/java/com/aryutkina/hotel/hotel_booking_service/
├── config/ # Kafka configuration
├── controller/ # REST controllers
├── dto/ # Data Transfer Objects (request/response)
├── event/ # Kafka event classes
├── exception/ # Custom exceptions and global handler
├── mapper/ # MapStruct mappers
├── model/ # JPA entities and MongoDB document
├── repository/ # Spring Data repositories (JPA + MongoDB)
├── security/ # Spring Security components
├── service/ # Business logic
│ ├── kafka/ # Kafka producers/consumers
│ └── statistics/ # Statistics service
└── specification/ # JPA Specifications for filtering


## Prerequisites

- Docker and Docker Compose
- Java 17 (for local development)
- Maven (or use `./mvnw` wrapper)

## Getting Started

### 1. Clone repository
```bash
git clone https://github.com/your-username/hotel-booking-service.git
cd hotel-booking-service
```
### 2. Build the project
```bash
./mvnw clean package -DskipTests
```
### 3. Run with Docker Compose
```bash
docker-compose up -d
```

## This starts:

PostgreSQL on port 5432 (mapped to 5433 on host)

MongoDB on port 27017

Zookeeper on port 2181

Kafka on port 9092

The Spring Boot application on port 8080

### 4. Verify
Check logs:

```bash
docker logs hotel_app
```

Application should start without errors.

### API Endpoints
All endpoints (except POST /api/users) require Basic Authentication.

### Public
POST /api/users – register new user (anyone)

### Hotels
GET /api/hotels – list hotels (paginated)

GET /api/hotels/{id} – get hotel by id

POST /api/hotels – create hotel (ADMIN)

PUT /api/hotels/{id} – update hotel (ADMIN)

DELETE /api/hotels/{id} – delete hotel (ADMIN)

PUT /api/hotels/{id}/rating – rate hotel (USER/ADMIN)

GET /api/hotels/filter – filter hotels (supports pagination, sorting, filtering by name, city, address, distance)

### Rooms
GET /api/rooms/{id} – get room by id

POST /api/rooms – create room (ADMIN)

PUT /api/rooms/{id} – update room (ADMIN)

DELETE /api/rooms/{id} – delete room (ADMIN)

GET /api/rooms/filter – filter rooms (supports pagination, sorting, filtering by price, max guests, availability by date range)

### Bookings
POST /api/bookings – create booking (USER/ADMIN)

GET /api/bookings – list all bookings (ADMIN, paginated)

### Statistics (ADMIN only)
GET /api/statistics/export/csv – download statistics as CSV

## Example Requests
### Register user
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"pass123","email":"john@mail.com","role":"ROLE_USER"}'
```  
### Create hotel (admin)
```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/hotels \
  -H "Content-Type: application/json" \
  -d '{"name":"Grand Hotel","title":"Luxury stay","city":"Moscow","address":"Tverskaya 1","distanceFromCenter":0.5}'
```
### Rate hotel
```bash
curl -u john:pass123 -X PUT http://localhost:8080/api/hotels/1/rating \
  -H "Content-Type: application/json" \
  -d '{"mark":4}'
```
### Filter rooms
```bash
curl -u john:pass123 "http://localhost:8080/api/rooms/filter?hotelId=1&checkIn=2025-06-01&checkOut=2025-06-05"
```
### Download statistics (admin)
```bash
curl -u admin:admin123 -o stats.csv http://localhost:8080/api/statistics/export/csv
``` 
### Configuration
Main configuration in application.yml:

- PostgreSQL: jdbc:postgresql://postgres:5432/hotel_booking (inside Docker) or localhost:5433 from host.

- MongoDB: mongodb://mongodb:27017/hotel_statistics

- Kafka: kafka:9092

### Built With
- Spring Boot
- PostgreSQL
- MongoDB 
- Apache Kafka 
- MapStruct 
- Lombok