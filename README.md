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

### Estructura detallada del backend

```text
backend/src/main/java/com/floresdelvalle/floristeria/
├── FloristeriaApplication.java       # Punto de entrada y datos iniciales
├── configuracion/
│   ├── ClienteConverter.java         # Convierte IDs en clientes
│   └── PedidoConverter.java          # Convierte IDs en pedidos
├── controlador/                      # Rutas web y preparación de vistas
│   ├── DashboardController.java
│   ├── ClienteController.java
│   ├── FlorController.java
│   ├── PedidoController.java
│   ├── ConductorController.java
│   ├── EntregaController.java
│   ├── FacturaController.java
│   └── ReporteController.java
├── modelo/                           # Entidades JPA del negocio
│   ├── Flor.java
│   ├── Cliente.java
│   ├── Pedido.java
│   ├── DetallePedido.java
│   ├── Conductor.java
│   ├── Entrega.java
│   ├── Factura.java
│   └── Pago.java
├── repositorio/                      # Acceso a PostgreSQL con Spring Data
└── servicio/                         # Reglas de negocio y transacciones

backend/src/main/resources/
├── application.properties            # Configuración de Spring y PostgreSQL
├── static/
│   ├── css/                          # estilos.css y style.css
│   ├── img/                          # Imágenes de la interfaz
│   └── js/                            # JavaScript del frontend
└── templates/                        # Vistas Thymeleaf
	 ├── dashboard.html
	 ├── clientes/
	 ├── flores/
	 ├── pedidos/
	 ├── entregas/
	 ├── facturas/
	 ├── reportes/
	 └── fragments/                    # Header, navegación y footer
```

### Flujo de información

```text
Navegador
	|
	v
Controlador Spring MVC -> Servicio -> Repository -> PostgreSQL
		  |                    |
		  v                    v
	Thymeleaf             Reglas de negocio
```

Las vistas conservan la identidad visual de Flores del Valle y los controladores conectan cada formulario con las operaciones del backend.

## Historial de trabajo

### Base del proyecto

- Se organizó el PMV con Spring Boot, Maven, Spring MVC, Thymeleaf, JPA y PostgreSQL.
- Se implementaron los módulos de inventario, clientes, pedidos, entregas, facturación y reportes.
- Se agregaron validaciones de formularios, estados del negocio y relaciones entre las entidades.

### Clientes y pedidos

- Se agregaron los clientes iniciales `Masculino`, `Femenina` y `Otro`.
- Se incorporó búsqueda escalable de clientes para evitar cargar cantidades masivas de opciones en el navegador.
- Se corrigió la vista de detalle y edición de pedidos para conservar los datos seleccionados.
- Se eliminaron imágenes inexistentes que mostraban texto alternativo extraño en pedidos.

### Entregas

- Se agregaron las opciones de conductor `Domicilio vehiculo terrestre`, `Domicilio vehiculo acuatico` y `Domicilio vehiculo aereo`.
- Se mantuvo el formulario de programación de entregas sin modificar su diseño visual.

### Facturación y pagos

- Cada factura muestra el número de pedido y el cliente asociado.
- Se habilitaron las acciones Ver, Editar y Eliminar desde la lista de facturas.
- Al guardar una factura nueva se registra automáticamente el pago total y la factura queda como `PAGADA`.
- Se corrigió la persistencia de pagos y el cálculo del dinero recibido acumulado por factura.
- El dashboard suma los pagos recibidos con estado `PAGADO` o `REGISTRADO` en el indicador de ventas.
- Se evitó facturar dos veces el mismo pedido y se mejoró la carga de los formularios de edición.

### Correcciones de interfaz

- Se conservaron los colores, textos, clases CSS y estructura visual original.
- Se retiraron imágenes inexistentes de formularios y detalles para evitar textos alternativos visibles.
- Se validaron las rutas principales con la aplicación ejecutándose en `http://localhost:8080`.

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