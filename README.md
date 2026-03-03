# Ejemplo-Spring-Boot

Proyecto de ejemplo de **Spring Boot** que demuestra la estructuración de una aplicación Java moderna, con buenas
prácticas, control de dependencias y ejecución como API/servicio web.


## 🚀 Tecnologías
- Java 17
- Spring Boot
- Maven
- REST APIs
- Control de versiones con Git


## 🧩 Estructura del proyecto

```

src/
├── main/
│   ├── java/        # Código fuente de la aplicación
│   └── resources/   # Configuración y recursos (application.properties)
└── test/
└── java/        

````


## ✨ Funcionalidades
- Aplicación base para construir microservicios o APIs REST
- Clase principal con método `main` para ejecución
- Configuración de Spring Boot lista para escalar
- Dependencias organizadas en `pom.xml`
  

## 🔌 Endpoints (vía Gateway)

### 👤 Usuarios (`/api/usuarios`)
- `GET /api/usuarios` — listar usuarios
- `GET /api/usuarios/{id}` — obtener usuario por id
- `POST /api/usuarios` — crear usuario
- `PUT /api/usuarios/{id}` — actualizar usuario
- `DELETE /api/usuarios/{id}` — eliminar usuario

### 🛴 Monopatines (`/api/monopatines`)
- `GET /api/monopatines` — listar monopatines
- `GET /api/monopatines/{id}` — obtener monopatín por id
- `POST /api/monopatines` — crear monopatín
- `PUT /api/monopatines/{id}` — actualizar monopatín
- `PUT /api/monopatines/{id}/estado` — actualizar estado/disponibilidad
- `DELETE /api/monopatines/{id}` — eliminar monopatín

### 🧭 Viajes (`/api/viajes`)
- `GET /api/viajes` — listar viajes
- `GET /api/viajes/{id}` — obtener viaje por id
- `POST /api/viajes` — iniciar/crear viaje
- `PUT /api/viajes/{id}` — actualizar viaje
- `PUT /api/viajes/{id}/finalizar` — finalizar viaje
- `DELETE /api/viajes/{id}` — eliminar viaje

### 🧾 Facturación (`/api/facturas`)
- `GET /api/facturas` — listar facturas
- `GET /api/facturas/{id}` — obtener factura por id
- `POST /api/facturas` — generar factura
- `GET /api/facturas/usuario/{usuarioId}` — facturas por usuario
- `DELETE /api/facturas/{id}` — eliminar factura

### 🛠️ Administración (`/api/admin`)
- `GET /api/admin/reportes` — reportes/estadísticas
- `PUT /api/admin/precios` — actualización de tarifas/precios
- `PUT /api/admin/cuentas/{id}/anular` — anulación/bloqueo de cuentas

---

## ▶️ Cómo ejecutar localmente

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/fabrilea/Ejemplo-Spring-Boot.git
   cd Ejemplo-Spring-Boot
   ```

2. Compilar y ejecutar con Maven:

   ```bash
   mvn clean package
   mvn spring-boot:run
   ```

3. Acceder desde el navegador o cliente REST:

   ```
   http://localhost:8080
   ```



## ⚙️ Construir y ejecutar el JAR

```bash
mvn clean package
java -jar target/*.jar
```


## 📌 Estado del proyecto

Este proyecto sirve como plantilla/base para APIs y servicios Spring Boot. Puede expandirse con controladores,
servicios y repositorios según las necesidades del desarrollo.


## 👨‍💻 Autor
**Felipe Egusquiza**
GitHub: [https://github.com/FelipeEgus](https://github.com/FelipeEgus)

**Fabrizio Leali**
GitHub: [https://github.com/fabrilea](https://github.com/fabrilea)

**Claudia Gogorza**
GitHub: [https://github.com/cgogorza](https://github.com/cgogorza)
