# 🎴 Cards Domain

Libreria para gestión de tarjetas generales.

## 🚀 Quick Start

### Ejecución Local

```bash
# Compilar
mvn clean package

# Ejecutar
mvn spring-boot:run
```

### Docker

```bash
# Build
docker build -t health-tools/cards-service:latest .

# Run
docker-compose up -d
```

## 📡 API Endpoints

Base URL: `http://localhost:8083`

### Health Check
```bash
GET /api/v1/cards/health
```

### Tarjetas

#### Crear tarjeta
```bash
POST /api/v1/cards
Content-Type: application/json

{
  "type": "BIOMAGNETIC_PAIR",
  "displayName": "Par Magnético Ejemplo",
  "description": "Descripción de ejemplo",
  "imageUrl": "https://example.com/image.jpg",
  "details": {
    "pairIds": ["par1", "par2"],
    "classification": "Viral",
    "itemIndex": 1
  }
}
```

#### Obtener tarjeta
```bash
GET /api/v1/cards/{id}
```

#### Listar tarjetas
```bash
# Por tipo
GET /api/v1/cards?type=BIOMAGNETIC_PAIR

# Por búsqueda
GET /api/v1/cards?search=viral
```

#### Actualizar tarjeta
```bash
PUT /api/v1/cards/{id}
Content-Type: application/json

{
  "displayName": "Nombre actualizado",
  "description": "Nueva descripción",
  "imageUrl": "https://example.com/new-image.jpg",
  "details": {...}
}
```

#### Eliminar tarjeta
```bash
DELETE /api/v1/cards/{id}
```

#### Contar tarjetas
```bash
GET /api/v1/cards/count
```

## 🏗️ Arquitectura

```
cards-service/
├── domain/              # Lógica de negocio pura
│   ├── model/          # Agregados, Value Objects
│   ├── service/        # Servicios de dominio
│   └── port/           # Puertos (interfaces)
├── application/        # Casos de uso
│   └── service/        # Servicios de aplicación
├── infrastructure/     # Adaptadores
│   ├── persistence/    # JPA, repositorios
│   ├── rest/           # Controllers, DTOs
│   └── config/         # Configuración Spring
└── CardsServiceApplication.java
```

## 🔧 Configuración

### application.yml
```yaml
server:
  port: 8083

spring:
  datasource:
    url: jdbc:h2:mem:cardsdb
  h2:
    console:
      enabled: true
      path: /h2-console
```

### Profiles

- **dev**: Desarrollo local (H2 console habilitado, logs verbose)
- **prod**: Producción (variables de entorno)

## 🧪 Testing

```bash
# Tests unitarios
mvn test

# Cobertura
mvn jacoco:report
```

## 🐳 Docker

```bash
# Build
mvn clean package
docker build -t health-tools/cards-service:latest .

# Run standalone
docker run -p 8083:8083 health-tools/cards-service:latest

# Docker Compose
docker-compose up -d

# Ver logs
docker-compose logs -f cards-service

# Detener
docker-compose down
```

## 🔗 Integración con otros servicios

### Desde Biomagnetic (ejemplo)

```java
@Component
public class CardsServiceClient {
    private final WebClient webClient;

    public CardsServiceClient(@Value("${cards-service.url}") String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    public Mono<List<CardDto>> getBiomagneticCards() {
        return webClient.get()
            .uri("/api/v1/cards?type=BIOMAGNETIC_PAIR")
            .retrieve()
            .bodyToFlux(CardDto.class)
            .collectList();
    }
}
```

## 📊 Tecnologías

- Java 21
- Spring Boot 3.5.7
- Spring Data JPA
- H2 Database (in-memory)
- Maven
- Docker

## 🌐 Puertos

- **8083**: API REST
- **8083/h2-console**: H2 Console (solo dev)

## 📝 Notas

- Microservicio independiente y desplegable
- Base de datos en memoria (H2) por defecto
- Arquitectura hexagonal
- Preparado para escalar horizontalmente
- Compatible con Docker y Kubernetes

---

**Versión**: 0.0.1  
**Autor**: Health Tools Team
