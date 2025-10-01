📮 Zipcode Search API
A RESTful API developed in Java with Spring Boot for ZIP code queries, integrating with the ViaCEP API and storing query history in a PostgreSQL database.

--------------------------------
🚀 FEATURES

🔍 ZIP Code Queries - Integration with ViaCEP API

📊 Query History - Storage of all searches performed

✅ Validations - Format verification and ZIP code existence checking

🐳 Docker Ready - Complete application containerization

🎯 Mock API - Development environment with simulated responses

🔄 Error Handling - Clear messages in English for different scenarios

--------------------------------
📋 PREREQUISITES

Java 17 or higher

Docker Desktop (for containerization)

Maven (included in wrapper)

Postman or similar (for API testing)

--------------------------------

🛠️ CONFIGURATIONS

Environment Variables
Variable	Description	Default Value
SPRING_DATASOURCE_URL	PostgreSQL URL	jdbc:postgresql://postgres:5432/zipcodedb
SPRING_DATASOURCE_USERNAME	Database user	zipuser
SPRING_DATASOURCE_PASSWORD	Database password	zippassword
ZIPCODE_API_URL	ZIP Code API URL	https://viacep.com.br
Ports
Service	Port	Description
Application	8080	Main API
PostgreSQL	5432	Database
WireMock	8081	Mock API

--------------------------------

🚀 HOW TO RUN

Docker Compose (Recommended)
bash
# Clone the repository
git clone <your-repository>
cd zipCodeSearch

# Run the startup script
./start.bat                    # Windows
# or
./start.sh                     # Linux/Mac

# Or run manually
docker-compose up --build

--------------------------------

📡 API ENDPOINTS

Query ZIP Code
GET /api/ceps/{zipcode}

--------------------------------

Example request:

GET http://localhost:8080/api/ceps/01001000

--------------------------------
Success response:

json

{
"cep": "01001-000",
"logradouro": "Praça da Sé",
"complemento": "lado ímpar",
"bairro": "Sé",
"localidade": "São Paulo",
"uf": "SP"
}
Get History
http
GET /api/ceps/history

--------------------------------
Response:

json

[
{
"id": 1,
"cep": "01001000",
"requestTime": "2024-01-15T10:30:45.123",
"responseData": "ZipcodeSearchResponse{cep='01001000', ...}"
}
]

--------------------------------
🐛 ERROR HANDLING

Invalid ZIP Code Format

json

{
"error": "invalid_zipcode",
"message": "Invalid digit quantity",
"details": "ZIP code must contain exactly 8 numeric digits"
}

--------------------------------
ZIP Code Not Found

json

{
"error": "zipcode_not_found",
"message": "ZIP code not found in database"
}

--------------------------------
Internal Error

json

{
"error": "internal_server_error",
"message": "An unexpected error occurred"
}

--------------------------------
🧪 TESTING WITH POSTMAN

Collection to Import:

json

{
"info": {
"name": "Zipcode Search API",
"description": "Collection to test the ZIP code API",
"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
},
"item": [
{
"name": "Query Valid ZIP Code",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/api/ceps/01001000",
"protocol": "http",
"host": ["localhost"],
"port": "8080",
"path": ["api", "ceps", "01001000"]
}
}
},
{
"name": "Query Invalid ZIP Code",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/api/ceps/123",
"protocol": "http",
"host": ["localhost"],
"port": "8080",
"path": ["api", "ceps", "123"]
}
}
},
{
"name": "Get History",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/api/ceps/history",
"protocol": "http",
"host": ["localhost"],
"port": "8080",
"path": ["api", "ceps", "history"]
}
}
}
]
}

--------------------------------
Postman Environment Configuration:

Variable	Initial Value	Current Value
base_url	http://localhost:8080	http://localhost:8080

--------------------------------

🐳 USEFUL DOCKER COMMANDS

bash
# Check container status
docker-compose ps

# View real-time logs
docker-compose logs -f

# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v

# Restart a specific service
docker-compose restart zipcode-app
🧪 DEVELOPMENT WITH MOCKS
For offline development, the application uses WireMock:

bash
# Access Mock API directly
curl http://localhost:8081/ws/01001000/json

# View WireMock mappings
curl http://localhost:8081/__admin/mappings

--------------------------------
🔧 TECHNOLOGIES USED

Java 17 - Programming language

Spring Boot 3 - Main framework

PostgreSQL - Database

Docker - Containerization

WireMock - API mocking

Maven - Dependency management

JPA/Hibernate - ORM and persistence

--------------------------------


📝 APPLIED PRINCIPLES


SOLID - Separation of responsibilities

RESTful - API design following REST conventions

Containerization - Environment isolation with Docker

Error Handling - Robust exception handling

Logging - Operation tracking

--------------------------------
👨‍💻 AUTHOR
Jason Silvestre - github: https://github.com/Jason-Silvestre/zipCodeSearch

--------------------------------
🎯
PROJECT STATUS: ✅ COMPLETED AND FUNCTIONAL
For questions or suggestions, open an issue in the repository!

