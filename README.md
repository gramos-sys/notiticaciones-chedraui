
# 📦 Proyecto de Notificaciones — Java Spring Boot

Este proyecto es una aplicación **Spring Boot (JAR)** que consume servicios REST externos y realiza operaciones en una **Base de Datos SQL Server**.  
El archivo `application.properties` no se incluye en el repositorio por razones de seguridad.  
Sigue las instrucciones de este documento para configurar tu entorno correctamente.

---

# 🚀 Requerimientos

- Java **1.8+**
- Maven **3.6+**
- SQL Server (acceso a BD operativa)
- Archivo `application.properties` configurado (ver sección siguiente)

---

# 🔧 Configuración del archivo `application.properties`

El archivo `application.properties` **no se incluye** en el repositorio.  
En su lugar, existe una plantilla llamada:

src/main/resources/application.properties_env

 ## ✅ Pasos para configurarlo 
 ### **1. Renombrar el archivo** 
 Cambia:
 `application.properties_env`
 por: 
`application.properties`

### **2. Editar los parámetros de conexión**  
Dentro  del  archivo  renombrado  configura  los  valores  reales  según  tu entorno:  
```properties  
spring.datasource.url=jdbc:sqlserver://[ip]:[puerto];databaseName=BD_Operativa;encrypt=false  
spring.datasource.username=TU_USUARIO  
spring.datasource.password=TU_PASSWORD  
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver  
logging.level.com.miapp=INFO
```

⚠️ **Importante:** reemplaza `[ip]` y `[puerto]` con los valores de la BD Operativa.

# 📁 Estructura principal del proyecto

La estructura base del proyecto Spring Boot es la siguiente:
```properties 
src/  
├── main/  
│ ├── java/  
│ │ └── mx/com/ago/notificaciones/  
│ │ ├── dao/ # Acceso a datos (JdbcTemplate, SPs)  
│ │ ├── dao/impl/ # Implementaciones de los DAOs  
│ │ ├── service/ # Lógica de negocio  
│ │ ├── service/impl/ # Implementaciones de servicios  
│ │ ├── controller/ # Controladores internos (si aplica)  
│ │ ├── exception/ # Manejo de excepciones personalizadas  
│ │ └── config/ # Configuraciones de Spring (Beans, JDBC, etc.)  
│ │  
│ └── resources/
```