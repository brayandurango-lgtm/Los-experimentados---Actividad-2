# Flores del Valle

## Descripción

Flores del Valle es un PMV académico para la gestión de una floristería. Permite administrar el inventario de flores, clientes, pedidos, entregas, facturación y reportes mediante una aplicación web.

## Problema

La gestión manual de inventario, pedidos, entregas y facturación puede generar pérdida de información, errores de disponibilidad y poca visibilidad sobre las ventas. El proyecto centraliza estos procesos en una aplicación web.

## Objetivo

Desarrollar una aplicación web funcional que facilite la administración de una floristería y permita consultar la información de forma organizada, segura y accesible.

## Tecnologías

- Java 21
- Spring Boot 3.x
- Maven
- Spring MVC
- Spring Web
- Spring Data JPA
- PostgreSQL
- Thymeleaf
- Jakarta Validation
- DevTools
- HTML, CSS y JavaScript

## Arquitectura MVC

El backend utiliza una arquitectura por capas:

```text
Controller -> Service -> Repository -> PostgreSQL
			 |
			 v
		 Model -> Thymeleaf -> HTML
```

Los controladores reciben solicitudes HTTP y preparan el `Model`. Los services concentran la lógica de negocio y los repositories gestionan el acceso a PostgreSQL.

## Estructura del proyecto

```text
FLORISTERIA - Actividad 2/
├── backend/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/floresdelvalle/floristeria/
│       │   ├── configuracion/
│       │   ├── controlador/
│       │   ├── modelo/
│       │   ├── repositorio/
│       │   └── servicio/
│       └── resources/
│           ├── application.properties
│           ├── static/
│           └── templates/
├── frontend/
├── .gitignore
└── README.md
```

## Modelo de datos

El modelo está compuesto por las entidades `Flor`, `Cliente`, `Pedido`, `DetallePedido`, `Conductor`, `Entrega`, `Factura` y `Pago`. Se utilizan relaciones JPA, enums para los estados y `BigDecimal` para los valores monetarios.

## Módulos

### Inventario

Registro, consulta, edición, búsqueda y desactivación de flores, incluyendo cantidad disponible, variedad, precios y estado.

### Pedidos

Creación de pedidos asociados a clientes y flores, consulta del detalle y actualización de estados.

### Entregas

Programación de entregas, asociación con pedidos y conductores, y seguimiento de estados.

### Facturación

Generación de facturas para pedidos completados, consulta de facturas y registro de pagos.

### Reportes

Consulta de cantidades de flores y pedidos, pedidos por estado, entregas por estado, facturas pendientes e ingresos.

## Configuración de PostgreSQL

La aplicación utiliza una base de datos PostgreSQL llamada `floristeria`, disponible en `localhost:5432`.

En `backend/src/main/resources/application.properties` se utiliza la siguiente configuración:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false
```

## Variables de entorno

No se almacenan contraseñas reales en el repositorio. Define las variables únicamente en el entorno local:

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/floristeria"
$env:DB_USERNAME = "usuario-local"
$env:DB_PASSWORD = "contraseña-local"
```

## Ejecución del proyecto

Requisitos: Java 21, Maven 3.9+ y PostgreSQL ejecutándose localmente.

```powershell
cd backend
mvn clean test
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`.

## Rutas principales

- `/` y `/dashboard`: panel principal.
- `/flores`: inventario de flores.
- `/clientes`: gestión de clientes.
- `/pedidos`: gestión de pedidos.
- `/entregas`: gestión de entregas.
- `/facturas`: gestión de facturación.
- `/reportes`: reportes del negocio.

## Equipo de desarrollo

# AUTORES DEL DESARROLLO ENCARGADOS DE ESTE PROYECTO

- Estudiante Brayan Alejandro Durango Urrea.
- Estudiante Esteban Murcia Prieto.
- Estudiante Claudia Liliana Cabrera Cabezas.
- Estudiante Danilo Jose Pino Ospino.
- Estudiante Luis Javier García Orozco.

Somos estudiantes con experiencia en la Institución Universitaria Digital de Antioquia.

## Evidencias del PMV

- Proyecto Spring Boot configurado con Maven.
- Conexión PostgreSQL mediante variables de entorno.
- Entidades JPA y repositories implementados.
- Services con reglas de negocio.
- Controladores Spring MVC enlazados con vistas Thymeleaf.
- Interfaz web responsive para los módulos principales.
- Validación de compilación mediante `mvn clean test`.

## El enlace que nos permite observar el proyecto

http://localhost:8080/dashboard 