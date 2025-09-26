@echo off
REM Script de despliegue local para lazcanoTransferApp en Windows
REM Proyecto JHipster con Angular + Spring Boot + MySQL

setlocal enabledelayedexpansion

set "COMMAND=%~1"
if "%COMMAND%"=="" set "COMMAND=start"

REM Configurar colores (si está disponible)
for /f "tokens=4-5 delims=. " %%i in ('ver') do set VERSION=%%i.%%j
if "%version%"=="10.0" (
    REM Windows 10/11 soporta colores ANSI
    set "RED=[91m"
    set "GREEN=[92m"
    set "YELLOW=[93m"
    set "BLUE=[94m"
    set "CYAN=[96m"
    set "WHITE=[97m"
    set "NC=[0m"
) else (
    REM Versiones anteriores sin colores
    set "RED="
    set "GREEN="
    set "YELLOW="
    set "BLUE="
    set "CYAN="
    set "WHITE="
    set "NC="
)

goto main

:print_info
    echo %BLUE%[INFO]%NC% %~1
    goto :eof

:print_success
    echo %GREEN%[SUCCESS]%NC% %~1
    goto :eof

:print_warning
    echo %YELLOW%[WARNING]%NC% %~1
    goto :eof

:print_error
    echo %RED%[ERROR]%NC% %~1
    goto :eof

:check_command
    where %~1 >nul 2>&1
    goto :eof

:check_dependencies
    call :print_info "Verificando dependencias..."
    
    set "missing_deps="
    
    call :check_command java
    if errorlevel 1 set "missing_deps=%missing_deps% Java"
    
    call :check_command node
    if errorlevel 1 set "missing_deps=%missing_deps% Node.js"
    
    call :check_command npm
    if errorlevel 1 set "missing_deps=%missing_deps% NPM"
    
    call :check_command docker
    if errorlevel 1 set "missing_deps=%missing_deps% Docker"
    
    call :check_command docker-compose
    if errorlevel 1 set "missing_deps=%missing_deps% Docker-Compose"
    
    if not "%missing_deps%"=="" (
        call :print_error "Faltan las siguientes dependencias:"
        echo %missing_deps%
        exit /b 1
    )
    
    call :print_success "Todas las dependencias están instaladas"
    goto :eof

:check_versions
    call :print_info "Verificando versiones de dependencias..."
    
    for /f "tokens=*" %%i in ('java -version 2^>^&1 ^| findstr "version"') do set "java_version=%%i"
    for /f "tokens=*" %%i in ('node --version 2^>^&1') do set "node_version=%%i"
    for /f "tokens=*" %%i in ('npm --version 2^>^&1') do set "npm_version=%%i"
    
    call :print_info "Java: %java_version%"
    call :print_info "Node.js: %node_version%"
    call :print_info "NPM: %npm_version%"
    
    REM Verificar versión mínima de Node.js (>=14.17.1)
    set "node_major=%node_version:v=%"
    for /f "tokens=1 delims=." %%a in ("%node_major%") do set "node_major=%%a"
    if %node_major% LSS 14 (
        call :print_error "Se requiere Node.js >= 14.17.1. Versión actual: %node_version%"
        exit /b 1
    )
    
    call :print_success "Versiones verificadas correctamente"
    goto :eof

:cleanup
    call :print_info "Limpiando procesos anteriores..."
    
    REM Detener contenedores de Docker si están corriendo
    docker-compose -f "src/main/docker/mysql.yml" down -v --remove-orphans >nul 2>&1
    
    REM Matar procesos en puertos específicos usando netstat y taskkill
    for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8080') do (
        if not "%%a"=="0" (
            call :print_info "Deteniendo proceso Java en puerto 8080..."
            taskkill /PID %%a /F >nul 2>&1
        )
    )
    
    for /f "tokens=5" %%a in ('netstat -aon ^| findstr :4200') do (
        if not "%%a"=="0" (
            call :print_info "Deteniendo proceso Angular en puerto 4200..."
            taskkill /PID %%a /F >nul 2>&1
        )
    )
    
    timeout /t 2 >nul
    call :print_success "Limpieza completada"
    goto :eof

:start_database
    call :print_info "Iniciando base de datos MySQL..."
    
    REM Verificar si Docker está corriendo
    docker info >nul 2>&1
    if errorlevel 1 (
        call :print_error "Docker no está corriendo. Por favor, inicia Docker Desktop"
        exit /b 1
    )
    
    REM Iniciar MySQL con docker-compose
    docker-compose -f "src/main/docker/mysql.yml" up -d
    
    call :print_info "Esperando que MySQL esté listo..."
    set /a attempts=0
    set /a max_attempts=30
    
    :wait_mysql
    if !attempts! GEQ !max_attempts! (
        call :print_error "MySQL no pudo iniciarse correctamente"
        exit /b 1
    )
    
    docker-compose -f "src/main/docker/mysql.yml" exec -T transferlazcano-mysql mysqladmin ping -h localhost --silent >nul 2>&1
    if not errorlevel 1 (
        call :print_success "MySQL está listo"
        goto :eof
    )
    
    timeout /t 2 >nul
    set /a attempts+=1
    echo|set /p="."
    goto wait_mysql

:install_node_dependencies
    call :print_info "Instalando dependencias de Node.js..."
    
    if not exist "node_modules" (
        npm install
        call :print_success "Dependencias de Node.js instaladas"
    ) else (
        call :print_info "Dependencias de Node.js ya están actualizadas"
    )
    goto :eof

:build_backend
    call :print_info "Construyendo backend Spring Boot..."
    
    REM Usar mvnw.cmd en Windows
    call mvnw.cmd clean compile -DskipTests
    
    if errorlevel 1 (
        call :print_error "Error construyendo el backend"
        exit /b 1
    )
    
    call :print_success "Backend construido exitosamente"
    goto :eof

:start_backend
    call :print_info "Iniciando backend Spring Boot..."
    
    REM Iniciar en background
    start /B "" mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev > backend.log 2>&1
    
    call :print_info "Backend iniciando..."
    
    REM Esperar que el backend esté listo
    set /a attempts=0
    set /a max_attempts=60
    
    :wait_backend
    if !attempts! GEQ !max_attempts! (
        call :print_error "Backend no pudo iniciarse correctamente"
        call :print_info "Revisa el archivo backend.log para más detalles"
        exit /b 1
    )
    
    REM Usar curl si está disponible, si no usar PowerShell
    curl -s http://localhost:8080/management/health >nul 2>&1
    if not errorlevel 1 (
        call :print_success "Backend está listo en http://localhost:8080"
        goto :eof
    )
    
    timeout /t 3 >nul
    set /a attempts+=1
    echo|set /p="."
    goto wait_backend

:start_frontend
    call :print_info "Iniciando frontend Angular en modo desarrollo..."
    
    REM Iniciar en background
    start /B "" npm run serve > frontend.log 2>&1
    
    call :print_info "Frontend iniciando..."
    
    REM Esperar que el frontend esté listo
    set /a attempts=0
    set /a max_attempts=30
    
    :wait_frontend
    if !attempts! GEQ !max_attempts! (
        call :print_error "Frontend no pudo iniciarse correctamente"
        call :print_info "Revisa el archivo frontend.log para más detalles"
        exit /b 1
    )
    
    REM Usar curl si está disponible
    curl -s http://localhost:4200 >nul 2>&1
    if not errorlevel 1 (
        call :print_success "Frontend está listo en http://localhost:4200"
        goto :eof
    )
    
    timeout /t 3 >nul
    set /a attempts+=1
    echo|set /p="."
    goto wait_frontend

:show_status
    echo.
    call :print_success "=== DESPLIEGUE LOCAL COMPLETADO ==="
    echo.
    echo %CYAN%🚀 Servicios disponibles:%NC%
    echo    • Frontend (Angular):    http://localhost:4200
    echo    • Backend (Spring Boot): http://localhost:8080
    echo    • API Docs (Swagger):    http://localhost:8080/swagger-ui/
    echo    • Health Check:          http://localhost:8080/management/health
    echo    • MySQL Database:        localhost:3306 (transferlazcano/root/sin_password)
    echo.
    echo %CYAN%📋 Comandos útiles:%NC%
    echo    • Ver logs del backend:  type backend.log
    echo    • Ver logs del frontend: type frontend.log
    echo    • Ver logs de MySQL:     docker-compose -f src/main/docker/mysql.yml logs -f
    echo    • Detener servicios:     deploy-local.bat stop
    echo.
    goto :eof

:stop_services
    call :print_info "Deteniendo todos los servicios..."
    
    REM Detener frontend
    for /f "tokens=5" %%a in ('netstat -aon ^| findstr :4200') do (
        if not "%%a"=="0" (
            call :print_info "Deteniendo frontend Angular..."
            taskkill /PID %%a /F >nul 2>&1
        )
    )
    
    REM Detener backend
    for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8080') do (
        if not "%%a"=="0" (
            call :print_info "Deteniendo backend Spring Boot..."
            taskkill /PID %%a /F >nul 2>&1
        )
    )
    
    REM Detener MySQL
    docker-compose -f "src/main/docker/mysql.yml" down -v --remove-orphans >nul 2>&1
    if not errorlevel 1 call :print_info "MySQL detenido"
    
    call :print_success "Todos los servicios han sido detenidos"
    goto :eof

:show_logs
    echo %CYAN%=== LOGS EN TIEMPO REAL ===%NC%
    echo %YELLOW%Presiona Ctrl+C para salir%NC%
    echo.
    
    if exist "backend.log" if exist "frontend.log" (
        REM Mostrar ambos logs (simplificado para CMD)
        echo Mostrando backend.log:
        type backend.log
        echo.
        echo Mostrando frontend.log:
        type frontend.log
    ) else if exist "backend.log" (
        type backend.log
    ) else if exist "frontend.log" (
        type frontend.log
    ) else (
        call :print_warning "No se encontraron archivos de log"
    )
    goto :eof

:show_help
    echo %CYAN%Script de despliegue local para lazcanoTransferApp (Windows)%NC%
    echo.
    echo %WHITE%Uso: deploy-local.bat [COMANDO]%NC%
    echo.
    echo %CYAN%Comandos disponibles:%NC%
    echo   start     - Inicia todos los servicios (por defecto)
    echo   stop      - Detiene todos los servicios
    echo   restart   - Reinicia todos los servicios
    echo   status    - Muestra el estado de los servicios
    echo   logs      - Muestra logs
    echo   clean     - Limpia procesos y contenedores
    echo   help      - Muestra esta ayuda
    echo.
    goto :eof

:main
if "%COMMAND%"=="start" goto start_command
if "%COMMAND%"=="stop" goto stop_command
if "%COMMAND%"=="restart" goto restart_command
if "%COMMAND%"=="status" goto status_command
if "%COMMAND%"=="logs" goto logs_command
if "%COMMAND%"=="clean" goto clean_command
if "%COMMAND%"=="help" goto help_command

call :print_error "Comando desconocido: %COMMAND%"
call :show_help
exit /b 1

:start_command
    call :print_info "Iniciando despliegue local de lazcanoTransferApp..."
    call :check_dependencies
    if errorlevel 1 exit /b 1
    call :check_versions
    if errorlevel 1 exit /b 1
    call :cleanup
    call :start_database
    if errorlevel 1 exit /b 1
    call :install_node_dependencies
    call :build_backend
    if errorlevel 1 exit /b 1
    call :start_backend
    if errorlevel 1 exit /b 1
    call :start_frontend
    if errorlevel 1 exit /b 1
    call :show_status
    goto end

:stop_command
    call :stop_services
    goto end

:restart_command
    call :stop_services
    timeout /t 2 >nul
    call "%~f0" start
    goto end

:status_command
    call :show_status
    goto end

:logs_command
    call :show_logs
    goto end

:clean_command
    call :cleanup
    goto end

:help_command
    call :show_help
    goto end

:end
endlocal
