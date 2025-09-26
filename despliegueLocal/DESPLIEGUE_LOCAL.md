# Despliegue Local - lazcanoTransferApp

Este documento describe cómo desplegar la aplicación **lazcanoTransferApp** en tu entorno de desarrollo local (MacOS y Windows).

## 📋 Prerrequisitos

Antes de usar los scripts de despliegue, asegúrate de tener instalado:

- **Java 11+** (recomendado Java 17)
- **Node.js 14.17.1+** (recomendado Node.js 18+)
- **npm** (viene con Node.js)
- **Docker Desktop** (para la base de datos MySQL)
- **Docker Compose** (viene con Docker Desktop)

## 🚀 Scripts Disponibles

### MacOS/Linux

#### 1. Script Principal de Despliegue (`deploy-local.sh`)

Este es el script principal y más completo. Incluye verificaciones, limpieza automática y manejo de errores.

```bash
# Iniciar todos los servicios
./deploy-local.sh start

# Detener todos los servicios
./deploy-local.sh stop

# Reiniciar todos los servicios
./deploy-local.sh restart

# Ver estado de los servicios
./deploy-local.sh status

# Ver logs en tiempo real
./deploy-local.sh logs

# Limpiar procesos y contenedores
./deploy-local.sh clean

# Mostrar ayuda
./deploy-local.sh help
```

#### 2. Script de Inicio Rápido (`quick-start.sh`)

Script más simple para inicio rápido cuando ya tienes todo configurado:

```bash
# Inicio rápido (solo inicia servicios)
./quick-start.sh
```

### Windows

#### 1. Script Principal PowerShell (`deploy-local.ps1`)

Script completo para PowerShell con verificaciones y manejo de errores:

```powershell
# Iniciar todos los servicios
.\deploy-local.ps1 start

# Detener todos los servicios
.\deploy-local.ps1 stop

# Reiniciar todos los servicios
.\deploy-local.ps1 restart

# Ver estado de los servicios
.\deploy-local.ps1 status

# Ver logs
.\deploy-local.ps1 logs

# Limpiar procesos y contenedores
.\deploy-local.ps1 clean

# Mostrar ayuda
.\deploy-local.ps1 help
```

#### 2. Script Principal Batch (`deploy-local.bat`)

Script completo para CMD tradicional:

```cmd
# Iniciar todos los servicios
deploy-local.bat start

# Detener todos los servicios
deploy-local.bat stop

# Reiniciar todos los servicios
deploy-local.bat restart

# Ver estado de los servicios
deploy-local.bat status

# Ver logs
deploy-local.bat logs

# Limpiar procesos y contenedores
deploy-local.bat clean

# Mostrar ayuda
deploy-local.bat help
```

#### 3. Script de Inicio Rápido Windows (`quick-start.bat`)

Script simple para inicio rápido:

```cmd
# Inicio rápido (solo inicia servicios)
quick-start.bat
```

## 🌐 URLs de la Aplicación

Una vez que los servicios estén ejecutándose:

- **Frontend (Angular)**: http://localhost:4200
- **Backend (Spring Boot)**: http://localhost:8080
- **API Documentation (Swagger)**: http://localhost:8080/swagger-ui/
- **Health Check**: http://localhost:8080/management/health
- **Base de Datos MySQL**: localhost:3306
  - Base de datos: `transferlazcano`
  - Usuario: `root`
  - Contraseña: (sin contraseña)

## 📊 Servicios y Puertos

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| Angular Frontend | 4200 | Interfaz de usuario |
| Spring Boot Backend | 8080 | API REST y servicios |
| MySQL Database | 3306 | Base de datos |

## 📝 Archivos de Log

Los scripts generan archivos de log para facilitar el debugging:

- `backend.log` - Logs del servidor Spring Boot
- `frontend.log` - Logs del servidor de desarrollo Angular

```bash
# Ver logs del backend
tail -f backend.log

# Ver logs del frontend
tail -f frontend.log

# Ver logs de MySQL
docker-compose -f src/main/docker/mysql.yml logs -f
```

## 🔧 Comandos Útiles

### Verificar que los servicios están corriendo:

**MacOS/Linux:**
```bash
# Verificar puertos ocupados
lsof -i :4200  # Frontend
lsof -i :8080  # Backend
lsof -i :3306  # MySQL

# Verificar contenedores de Docker
docker ps

# Verificar estado de MySQL
docker-compose -f src/main/docker/mysql.yml ps
```

**Windows:**
```cmd
# Verificar puertos ocupados
netstat -aon | findstr :4200  # Frontend
netstat -aon | findstr :8080  # Backend
netstat -aon | findstr :3306  # MySQL

# Verificar contenedores de Docker
docker ps

# Verificar estado de MySQL
docker-compose -f src/main/docker/mysql.yml ps
```

### Comandos de desarrollo:

```bash
# Solo frontend (si el backend ya está corriendo)
npm start

# Solo backend (si la BD ya está corriendo)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Solo base de datos
docker-compose -f src/main/docker/mysql.yml up -d

# Construir para producción
npm run build
./mvnw clean package -Pprod
```

### Limpiar y reiniciar desde cero:

```bash
# Limpiar todo
./deploy-local.sh clean

# Limpiar cache de npm
npm cache clean --force

# Limpiar dependencias y reinstalar
rm -rf node_modules package-lock.json
npm install

# Limpiar compilación de Maven
./mvnw clean
```

## 🛠️ Solución de Problemas

### El script falla en la verificación de dependencias:
- Instala las dependencias faltantes (Java, Node.js, Docker)
- Verifica que Docker Desktop esté ejecutándose

### El puerto 8080 o 4200 ya está ocupado:
```bash
# Encuentra y mata el proceso
lsof -ti :8080 | xargs kill -9
lsof -ti :4200 | xargs kill -9
```

### MySQL no se conecta:
```bash
# Reiniciar MySQL
docker-compose -f src/main/docker/mysql.yml down -v
docker-compose -f src/main/docker/mysql.yml up -d

# Verificar logs de MySQL
docker-compose -f src/main/docker/mysql.yml logs
```

### Errores de compilación de Angular:
```bash
# Limpiar cache de Angular
./node_modules/.bin/ng cache clean

# Reinstalar dependencias
rm -rf node_modules package-lock.json
npm install
```

### Errores de compilación de Maven:
```bash
# Limpiar y recompilar
./mvnw clean compile

# Verificar versión de Java
java -version
```

## 📚 Scripts NPM Disponibles

El proyecto incluye varios scripts de npm que puedes usar:

```bash
# Desarrollo
npm run start          # Servidor de desarrollo
npm run build          # Build de desarrollo
npm run build:prod     # Build de producción

# Testing
npm run test           # Tests unitarios
npm run test:watch     # Tests en modo watch
npm run lint           # Linting
npm run lint:fix       # Arreglar problemas de lint

# Base de datos
npm run docker:db:up   # Iniciar MySQL
npm run docker:db:down # Detener MySQL

# Backend
npm run backend:start  # Iniciar solo backend
npm run java:jar       # Crear JAR
```

## 🔄 Flujo de Desarrollo Recomendado

1. **Primera vez**:
   ```bash
   ./deploy-local.sh start
   ```

2. **Desarrollo diario**:
   ```bash
   ./quick-start.sh
   ```

3. **Cuando termines**:
   ```bash
   ./deploy-local.sh stop
   ```

4. **Si tienes problemas**:
   ```bash
   ./deploy-local.sh clean
   ./deploy-local.sh start
   ```

## 🚀 Próximos Pasos

Después de tener la aplicación ejecutándose localmente:

1. Visita http://localhost:4200 para ver la aplicación
2. Revisa http://localhost:8080/swagger-ui/ para explorar la API
3. Verifica que todo funciona con http://localhost:8080/management/health

¡Listo para desarrollar! 🎉
