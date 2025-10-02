📮 Zipcode Search API

A RESTful API developed in Java with Spring Boot for ZIP code queries, integrating with the ViaCEP API and storing query history in a PostgreSQL database.

## 🏗 Architecture

[Client] → [Spring Boot App] → [ViaCEP API/WireMock]
↓
[PostgreSQL] - Query History Storage


## 🚀 Features

🔍 **ZIP Code Queries** - Integration with ViaCEP API  
📊 **Query History** - Storage of all searches in PostgreSQL  
✅ **Validations** - ZIP code format and existence checking  
🐳 **Docker Ready** - Complete containerization with Docker Compose  
🎯 **Mock API** - WireMock for development and testing  
🔄 **Error Handling** - Clear error messages in English  
☁️ **AWS Ready** - Configurable for cloud deployment

## 📋 Prerequisites

- Java 17 or higher
- Docker Desktop (for containerization)
- Maven (included in wrapper)
- Postman or similar (for API testing)

## 🛠️ Configuration

### Environment Variables
| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | PostgreSQL URL | `jdbc:postgresql://postgres:5432/zipcodedb` |
| `ZIPCODE_API_URL` | ViaCEP API URL | `https://viacep.com.br` |

### Ports
| Service | Port | Description |
|---------|------|-------------|
| Application | 8080 | Main API |
| PostgreSQL | 5432 | Database |
| WireMock | 8081 | Mock API |

## 🚀 Quick Start

```bash
# Clone the repository
git clone <your-repository>
cd zipCodeSearch

# Using scripts
./start.sh  # Linux/Mac
./start.bat # Windows

# Or run manually
docker-compose up --build

📡 API Endpoints
Query ZIP Code

GET /api/ceps/{zipcode}

Example request:

bash
GET http://localhost:8080/api/ceps/01001000
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

Get Query History
http
GET /api/ceps/history
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
🐛 Error Handling
Invalid ZIP Code Format
json
{
  "error": "invalid_zipcode",
  "message": "Invalid digit quantity",
  "details": "ZIP code must contain exactly 8 numeric digits"
}
ZIP Code Not Found
json
{
  "error": "zipcode_not_found",
  "message": "ZIP code not found in database"
}
Internal Error
json
{
  "error": "internal_server_error",
  "message": "An unexpected error occurred"
}
🧪 Testing
With WireMock (Offline)
bash
# Test mock directly
curl http://localhost:8081/ws/01001000/json

# View WireMock mappings
curl http://localhost:8081/__admin/mappings

Postman Collection
Import the provided collection for full API testing.

---------------------------
#OPEN POWERSHELL AS ADMINISTRATOR

#NAVIGATE TO THE PROJECT FOLDER FOR EXAMPLE:
PS C:\WINDOWS\system32> cd "C:\Users\jason\OneDrive\Desktop\WORKSPACE\PROJETOS\DESAFIO_STEFANINI\zipCodeSearch"

#START DOCKER SERVICE
net start com.docker.service


#RUN WITH TESTS 
PS C:\Users\jason\OneDrive\Desktop\WORKSPACE\PROJETOS\DESAFIO_STEFANINI\zipCodeSearch> .\mvnw.cmd package -DskipTests   

#RUN WITHOUT TESTS - FASTER
PS C:\Users\jason\OneDrive\Desktop\WORKSPACE\PROJETOS\DESAFIO_STEFANINI\zipCodeSearch> .\mvnw.cmd package

# START EVERYTHING
docker-compose up --build

# TEST VALID ZIPCODE
curl http://localhost:8081/ws/01001000/json

# TEST ZIPCODE NOT FOUND
curl http://localhost:8081/ws/99999999/json

# TEST INVALID FORMAT
curl http://localhost:8081/ws/123/json

# TEST SERVIDOR ERROR SIMULATION
curl http://localhost:8081/ws/00000000/json

# SEE ALL THE MAPPINGS
curl http://localhost:8081/__admin/mappings

-------------------------


🐳 Useful Docker Commands
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
🔧 Technologies Used
Java 17 - Programming language

Spring Boot 3 - Main framework

PostgreSQL - Database

Docker - Containerization

WireMock - API mocking

Maven - Dependency management

JPA/Hibernate - ORM and persistence

AWS S3 - Cloud Storage (Optional)

📝 Applied Principles
SOLID Principles Implemented:
SRP: Separate classes for Client, Service, Repository

OCP: Open for extension via interfaces

LSP: Proper inheritance hierarchy

ISP: Specific interfaces for each client

DIP: Dependency injection with Spring

Additional Principles:
RESTful - API design following REST conventions

Containerization - Environment isolation with Docker

Error Handling - Robust exception handling

Logging - Operation tracking

☁️ AWS Configuration (Optional)
Set these environment variables for AWS integration:

bash
AWS_ACCESS_KEY=your_access_key
AWS_SECRET_KEY=your_secret_key
AWS_REGION=us-east-1
AWS_S3_BUCKET=zipcode-backups
👨‍💻 Author
Jason Silvestre
GitHub: https://github.com/Jason-Silvestre/zipCodeSearch

🎯 Project Status: ✅ Completed and Functional
For questions or suggestions, open an issue in the repository!