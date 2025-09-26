@echo off
REM Script de inicio rápido para desarrollo local en Windows
REM Para desarrolladores que ya tienen todo configurado

echo 🚀 Iniciando lazcanoTransferApp en modo desarrollo...
echo.

echo 📊 Iniciando base de datos...
docker-compose -f src/main/docker/mysql.yml up -d

echo Esperando que MySQL inicie...
timeout /t 5 >nul

echo ⚙️ Iniciando backend...
start /B "" mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev > backend.log 2>&1

echo 🎨 Iniciando frontend...
start /B "" npm start > frontend.log 2>&1

echo.
echo ✅ Servicios iniciándose...
echo.
echo 🌐 URLs disponibles en unos minutos:
echo    • Frontend: http://localhost:4200
echo    • Backend:  http://localhost:8080
echo    • Swagger:  http://localhost:8080/swagger-ui/
echo.
echo 📋 Para ver logs:
echo    • Backend:  type backend.log
echo    • Frontend: type frontend.log
echo.
echo 🛑 Para detener: deploy-local.bat stop
