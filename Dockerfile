FROM eclipse-temurin:21-jre-alpine

LABEL maintainer="Health Tools Team"
LABEL service="cards-service"
LABEL version="0.0.1"

WORKDIR /app

# Copiar el JAR compilado
COPY target/cards-service-*.jar app.jar

# Exponer el puerto del servicio
EXPOSE 8083

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8083/api/v1/cards/health || exit 1

# Ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
