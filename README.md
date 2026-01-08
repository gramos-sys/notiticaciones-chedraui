# 📦 Proyecto de Notificaciones — Java Spring Boot

  

Este proyecto es una aplicación **Spring Boot (JAR)** que consume servicios REST externos y realiza operaciones en una **Base de Datos SQL Server**.

El archivo `application.properties` no se incluye en el repositorio por razones de seguridad.

Sigue las instrucciones de este documento para configurar tu entorno correctamente.

---
# 🚀 Requerimientos

- Java JDK **1.8+**
- VS Code:
    - Extension Pack for Java (oficial)
    - Spring Boot Extension Pack (imprescindible)
    - Lombok Annotations Support
   
- Maven **3.6+**
- SQL Server (acceso a BD histórica)
- Archivo `application.properties` configurado (ver sección siguiente)

---
# 🔧 Configuraciones del Proyecto
### **1. Configurar archivo `application.properties`**

El archivo `application.properties`  **no se incluye** en el repositorio *(por razones de seguridad)*, en su lugar existe una plantilla llamada: `application.properties_env`

Editar el nombre de archivo:

`application.properties_env`
a:
`application.properties`

### **2. Editar los parámetros de conexión**

Dentro del archivo renombrado configura los valores la BD Historica, según tu entorno:

```properties
# Configuracion BD Histoica
spring.datasource.url=jdbc:sqlserver://[ip]:[puerto];databaseName=BD_Historica;encrypt=false
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
logging.level.com.miapp=INFO

# Configuracion Firebase mensajes push
firebase.credentials.path=file:C:/inetpub/google-keys/rich-service-account.json
```
⚠️ **Importante:** reemplaza `[ip]` y `[puerto]` con los valores de la BD Operativa.

---
# ▶️ Cómo ejecutar el proyecto
Una vez configurado el archivo application.properties y teniendo instalados los requerimientos, existen dos formas recomendadas para ejecutar esta aplicación Spring Boot.

### 🚀 Opción 1: Ejecutar desde VS Code (recomendado)

1.- Abre la carpeta del proyecto en VS Code.

2.- Verifica que las extensiones de Java y Spring Boot estén activas.

3.- Abre el panel lateral: ```Sprin Dashboard```

4.- VS Code detectará automáticamente tu aplicación: ```notificaciones — NotificacionesApplication```

5.- Haz clic en Run para iniciar la aplicación.

Durante la ejecución verás:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logs en la terminal

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Mensajes de Spring Boot levantando el contexto

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Errores de configuración si faltan valores en application.properties


### 🚀 Opción 2: Ejecutar desde consola con Maven
Abrir la terminal en la raiz de proyecto y ejecutar el comando: ```mvn spring-boot:run```

### 🧪 Verificar que la aplicación arrancó
Si la aplicación levantó correctamente verás algo como:
```
Started NotificacionesApplication in 5.628 seconds
Tomcat started on port(s): 8080
```
El servicio está disponible en el puerto 8080 (o el que configures).

---
# 📌 Construir el JAR ejecutable
Abrir la terminal en la raiz de proyecto y ejecutar el comando: 
```
mvn clean package
```

El JAR se generará en:
```
target/notificaciones-0.1.jar
```
Se puede ejecutar el JAR generado con la instrucción;
```
java -jar target/notificaciones-0.1.jar
```
---
# 📁 Estructura principal del proyecto
La estructura base del proyecto Spring Boot es la siguiente:

```properties

src/
 └── main/
     └── java/
         └── mx/
             └── com/
                 └── ago/
                     └── notificaciones/
                         ├── 📌 NotificacionesApplication.java      # Clase principal (arranque)
                         │
                         ├── 🗄️ connection/
                         │   └── 🔌 ConnectionDB.java               # Conexión a SQL Server
                         │
                         ├── 📂 dao/
                         │   ├── 📝 INotificacionesDao.java         # Interfaz DAO
                         │   └── 📦 impl/
                         │       └── 🧩 NotificacionesDaoImpl.java  # Implementación DAO
                         │
                         ├── 📄 data/
                         │   └── 📑 DatosNotificacion.java          # DTO de notificación
                         │
                         ├── ⚠️ exception/
                         │   ├── ❗ ApiClientException.java         # Error en API externa
                         │   └── ❗ BusinessException.java          # Error de negocio
                         │
                         ├── 🌐 restclient/                         # Cliente(s) REST externos
                         │
                         ├── 🧭 service/
                         │   ├── 📝 INotificacionesService.java     # Interfaz del servicio
                         │   ├── 📢 NotificacionMasivaService.java  # Servicio de envíos masivos
                         │   └── 📦 impl/
                         │       └── 🚀 NotificacionesService.java  # Implementación principal
                         │
                         └── 🛠️ utils/
                             ├── 🔥 FirebaseSDK.java                # Integración con Firebase
                             └── 🧩 NotificacionesApplication.java  # Configuración global



```