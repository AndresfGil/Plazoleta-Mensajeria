# Microservicio de Mensajería - Twilio SMS

Este microservicio implementa la funcionalidad de envío de mensajes SMS utilizando Twilio, siguiendo la arquitectura hexagonal de Bancolombia.

## Funcionalidades

- Envío de mensajes SMS a través de Twilio
- Circuit breaker para manejo de fallos
- Endpoint REST para recibir solicitudes de envío
- Manejo de errores y respuestas estructuradas

## Configuración

### Variables de Entorno

Configura las siguientes variables de entorno antes de ejecutar la aplicación:

```bash
export TWILIO_ACCOUNT_SID=your_twilio_account_sid
export TWILIO_AUTH_TOKEN=your_twilio_auth_token
export TWILIO_FROM_NUMBER=+1234567890
```

### Configuración en application.yaml

Las credenciales también se pueden configurar directamente en el archivo `application.yaml`:

```yaml
twilio:
  account-sid: "your_account_sid_here"
  auth-token: "your_auth_token_here"
  from-number: "+1234567890"
```

## Uso del API

### Endpoint de Envío de Mensajes

**POST** `/api/mensaje/enviar`

#### Request Body

```json
{
  "telefono": "+573001234567",
  "mensaje": "Tu pedido está listo. Tu PIN es 8456."
}
```

#### Response Success (200)

```json
{
  "status": "success",
  "message": "Mensaje enviado exitosamente"
}
```

#### Response Error (500)

```json
{
  "status": "error",
  "message": "Error enviando mensaje: [descripción del error]"
}
```

## Ejecución

### Desarrollo

```bash
./gradlew bootRun
```

### Construcción

```bash
./gradlew build
```

### Ejecutar Tests

```bash
./gradlew test
```

## Arquitectura

El proyecto sigue la arquitectura hexagonal con las siguientes capas:

- **Domain**: Modelos y casos de uso
- **Infrastructure**: Adaptadores para Twilio y API REST
- **Application**: Configuración y punto de entrada

## Circuit Breaker

El servicio incluye un circuit breaker configurado para manejar fallos en la comunicación con Twilio:

- **Failure Rate Threshold**: 50%
- **Slow Call Duration Threshold**: 5s
- **Wait Duration in Open State**: 30s
- **Minimum Number of Calls**: 5

## Dependencias Principales

- Spring Boot 3.5.4
- Twilio SDK 9.12.0
- Resilience4j para circuit breaker
- Lombok para reducción de código boilerplate