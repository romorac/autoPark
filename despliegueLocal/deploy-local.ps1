# Script de despliegue local para lazcanoTransferApp en Windows
# Proyecto JHipster con Angular + Spring Boot + MySQL

param(
    [Parameter(Position=0)]
    [ValidateSet("start", "stop", "restart", "status", "logs", "clean", "help")]
    [string]$Command = "start"
)

# Configuración de colores
$Host.UI.RawUI.BackgroundColor = "Black"
$Host.UI.RawUI.ForegroundColor = "White"

function Write-Info {
    param([string]$Message)
    Write-Host "[INFO] $Message" -ForegroundColor Blue
}

function Write-Success {
    param([string]$Message)
    Write-Host "[SUCCESS] $Message" -ForegroundColor Green
}

function Write-Warning {
    param([string]$Message)
    Write-Host "[WARNING] $Message" -ForegroundColor Yellow
}

function Write-Error {
    param([string]$Message)
    Write-Host "[ERROR] $Message" -ForegroundColor Red
}

function Test-Command {
    param([string]$CommandName)
    return Get-Command $CommandName -ErrorAction SilentlyContinue
}

function Test-Dependencies {
    Write-Info "Verificando dependencias..."
    
    $missingDeps = @()
    
    if (!(Test-Command "java")) {
        $missingDeps += "Java"
    }
    
    if (!(Test-Command "node")) {
        $missingDeps += "Node.js"
    }
    
    if (!(Test-Command "npm")) {
        $missingDeps += "NPM"
    }
    
    if (!(Test-Command "docker")) {
        $missingDeps += "Docker"
    }
    
    if (!(Test-Command "docker-compose")) {
        $missingDeps += "Docker Compose"
    }
    
    if ($missingDeps.Count -gt 0) {
        Write-Error "Faltan las siguientes dependencias:"
        foreach ($dep in $missingDeps) {
            Write-Host "  - $dep" -ForegroundColor Red
        }
        exit 1
    }
    
    Write-Success "Todas las dependencias están instaladas"
}

function Test-Versions {
    Write-Info "Verificando versiones de dependencias..."
    
    $javaVersion = & java -version 2>&1 | Select-String "version" | Select-Object -First 1
    $nodeVersion = & node --version
    $npmVersion = & npm --version
    
    Write-Info "Java: $javaVersion"
    Write-Info "Node.js: $nodeVersion"
    Write-Info "NPM: $npmVersion"
    
    # Verificar versión mínima de Node.js (>=14.17.1)
    $nodeMajor = [int]($nodeVersion -replace "v", "" -split "\.")[0]
    if ($nodeMajor -lt 14) {
        Write-Error "Se requiere Node.js >= 14.17.1. Versión actual: $nodeVersion"
        exit 1
    }
    
    Write-Success "Versiones verificadas correctamente"
}

function Invoke-Cleanup {
    Write-Info "Limpiando procesos anteriores..."
    
    # Detener contenedores de Docker si están corriendo
    try {
        & docker-compose -f "src/main/docker/mysql.yml" down -v --remove-orphans 2>$null
    } catch {
        # Ignorar errores si no hay contenedores
    }
    
    # Matar procesos en puertos específicos
    try {
        $javaPid = Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess
        if ($javaPid) {
            Write-Info "Deteniendo proceso Java en puerto 8080..."
            Stop-Process -Id $javaPid -Force -ErrorAction SilentlyContinue
            Start-Sleep -Seconds 2
        }
    } catch {
        # Ignorar errores
    }
    
    try {
        $ngPid = Get-NetTCPConnection -LocalPort 4200 -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess
        if ($ngPid) {
            Write-Info "Deteniendo proceso Angular en puerto 4200..."
            Stop-Process -Id $ngPid -Force -ErrorAction SilentlyContinue
            Start-Sleep -Seconds 2
        }
    } catch {
        # Ignorar errores
    }
    
    Write-Success "Limpieza completada"
}

function Start-Database {
    Write-Info "Iniciando base de datos MySQL..."
    
    # Verificar si Docker está corriendo
    try {
        & docker info 2>$null | Out-Null
    } catch {
        Write-Error "Docker no está corriendo. Por favor, inicia Docker Desktop"
        exit 1
    }
    
    # Iniciar MySQL con docker-compose
    & docker-compose -f "src/main/docker/mysql.yml" up -d
    
    Write-Info "Esperando que MySQL esté listo..."
    $attempts = 0
    $maxAttempts = 30
    
    while ($attempts -lt $maxAttempts) {
        try {
            & docker-compose -f "src/main/docker/mysql.yml" exec -T transferlazcano-mysql mysqladmin ping -h localhost --silent 2>$null
            if ($LASTEXITCODE -eq 0) {
                Write-Success "MySQL está listo"
                return
            }
        } catch {
            # Continuar intentando
        }
        
        Start-Sleep -Seconds 2
        $attempts++
        Write-Host "." -NoNewline
    }
    
    Write-Error "MySQL no pudo iniciarse correctamente"
    exit 1
}

function Install-NodeDependencies {
    Write-Info "Instalando dependencias de Node.js..."
    
    if (!(Test-Path "node_modules") -or (Get-Item "package.json").LastWriteTime -gt (Get-Item "node_modules").LastWriteTime) {
        & npm install
        Write-Success "Dependencias de Node.js instaladas"
    } else {
        Write-Info "Dependencias de Node.js ya están actualizadas"
    }
}

function Build-Backend {
    Write-Info "Construyendo backend Spring Boot..."
    
    # Usar mvnw.cmd en Windows
    & .\mvnw.cmd clean compile -DskipTests
    
    if ($LASTEXITCODE -eq 0) {
        Write-Success "Backend construido exitosamente"
    } else {
        Write-Error "Error construyendo el backend"
        exit 1
    }
}

function Start-Backend {
    Write-Info "Iniciando backend Spring Boot..."
    
    # Iniciar en background usando Start-Process
    $backendProcess = Start-Process -FilePath ".\mvnw.cmd" -ArgumentList "spring-boot:run", "-Dspring-boot.run.profiles=dev" -RedirectStandardOutput "backend.log" -RedirectStandardError "backend-error.log" -PassThru -WindowStyle Hidden
    
    Write-Info "Backend iniciando (PID: $($backendProcess.Id))..."
    
    # Esperar que el backend esté listo
    $attempts = 0
    $maxAttempts = 60
    
    while ($attempts -lt $maxAttempts) {
        try {
            $response = Invoke-WebRequest -Uri "http://localhost:8080/management/health" -UseBasicParsing -TimeoutSec 3 -ErrorAction SilentlyContinue
            if ($response.StatusCode -eq 200) {
                Write-Success "Backend está listo en http://localhost:8080"
                return
            }
        } catch {
            # Continuar esperando
        }
        
        Start-Sleep -Seconds 3
        $attempts++
        Write-Host "." -NoNewline
    }
    
    Write-Error "Backend no pudo iniciarse correctamente"
    Write-Info "Revisa el archivo backend.log para más detalles"
    exit 1
}

function Start-Frontend {
    Write-Info "Iniciando frontend Angular en modo desarrollo..."
    
    # Iniciar en background
    $frontendProcess = Start-Process -FilePath "npm" -ArgumentList "run", "serve" -RedirectStandardOutput "frontend.log" -RedirectStandardError "frontend-error.log" -PassThru -WindowStyle Hidden
    
    Write-Info "Frontend iniciando (PID: $($frontendProcess.Id))..."
    
    # Esperar que el frontend esté listo
    $attempts = 0
    $maxAttempts = 30
    
    while ($attempts -lt $maxAttempts) {
        try {
            $response = Invoke-WebRequest -Uri "http://localhost:4200" -UseBasicParsing -TimeoutSec 3 -ErrorAction SilentlyContinue
            if ($response.StatusCode -eq 200) {
                Write-Success "Frontend está listo en http://localhost:4200"
                return
            }
        } catch {
            # Continuar esperando
        }
        
        Start-Sleep -Seconds 3
        $attempts++
        Write-Host "." -NoNewline
    }
    
    Write-Error "Frontend no pudo iniciarse correctamente"
    Write-Info "Revisa el archivo frontend.log para más detalles"
    exit 1
}

function Show-Status {
    Write-Host ""
    Write-Success "=== DESPLIEGUE LOCAL COMPLETADO ==="
    Write-Host ""
    Write-Host "🚀 Servicios disponibles:" -ForegroundColor Cyan
    Write-Host "   • Frontend (Angular):    http://localhost:4200" -ForegroundColor White
    Write-Host "   • Backend (Spring Boot): http://localhost:8080" -ForegroundColor White
    Write-Host "   • API Docs (Swagger):    http://localhost:8080/swagger-ui/" -ForegroundColor White
    Write-Host "   • Health Check:          http://localhost:8080/management/health" -ForegroundColor White
    Write-Host "   • MySQL Database:        localhost:3306 (transferlazcano/root/sin_password)" -ForegroundColor White
    Write-Host ""
    Write-Host "📋 Comandos útiles:" -ForegroundColor Cyan
    Write-Host "   • Ver logs del backend:  Get-Content backend.log -Wait" -ForegroundColor White
    Write-Host "   • Ver logs del frontend: Get-Content frontend.log -Wait" -ForegroundColor White
    Write-Host "   • Ver logs de MySQL:     docker-compose -f src/main/docker/mysql.yml logs -f" -ForegroundColor White
    Write-Host "   • Detener servicios:     .\deploy-local.ps1 stop" -ForegroundColor White
    Write-Host ""
}

function Stop-Services {
    Write-Info "Deteniendo todos los servicios..."
    
    # Detener frontend
    try {
        $ngPid = Get-NetTCPConnection -LocalPort 4200 -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess
        if ($ngPid) {
            Write-Info "Deteniendo frontend Angular..."
            Stop-Process -Id $ngPid -Force -ErrorAction SilentlyContinue
        }
    } catch {
        # Ignorar errores
    }
    
    # Detener backend
    try {
        $javaPid = Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess
        if ($javaPid) {
            Write-Info "Deteniendo backend Spring Boot..."
            Stop-Process -Id $javaPid -Force -ErrorAction SilentlyContinue
        }
    } catch {
        # Ignorar errores
    }
    
    # Detener MySQL
    try {
        & docker-compose -f "src/main/docker/mysql.yml" down -v --remove-orphans
        Write-Info "MySQL detenido"
    } catch {
        Write-Warning "No se pudo detener MySQL o no estaba corriendo"
    }
    
    Write-Success "Todos los servicios han sido detenidos"
}

function Show-Logs {
    Write-Host "=== LOGS EN TIEMPO REAL ===" -ForegroundColor Cyan
    Write-Host "Presiona Ctrl+C para salir" -ForegroundColor Yellow
    Write-Host ""
    
    if ((Test-Path "backend.log") -and (Test-Path "frontend.log")) {
        Get-Content "backend.log", "frontend.log" -Wait
    } elseif (Test-Path "backend.log") {
        Get-Content "backend.log" -Wait
    } elseif (Test-Path "frontend.log") {
        Get-Content "frontend.log" -Wait
    } else {
        Write-Warning "No se encontraron archivos de log"
    }
}

function Show-Help {
    Write-Host "Script de despliegue local para lazcanoTransferApp (Windows)" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Uso: .\deploy-local.ps1 [COMANDO]" -ForegroundColor White
    Write-Host ""
    Write-Host "Comandos disponibles:" -ForegroundColor Cyan
    Write-Host "  start     - Inicia todos los servicios (por defecto)" -ForegroundColor White
    Write-Host "  stop      - Detiene todos los servicios" -ForegroundColor White
    Write-Host "  restart   - Reinicia todos los servicios" -ForegroundColor White
    Write-Host "  status    - Muestra el estado de los servicios" -ForegroundColor White
    Write-Host "  logs      - Muestra logs en tiempo real" -ForegroundColor White
    Write-Host "  clean     - Limpia procesos y contenedores" -ForegroundColor White
    Write-Host "  help      - Muestra esta ayuda" -ForegroundColor White
    Write-Host ""
}

# Función principal
switch ($Command) {
    "start" {
        Write-Info "Iniciando despliegue local de lazcanoTransferApp..."
        Test-Dependencies
        Test-Versions
        Invoke-Cleanup
        Start-Database
        Install-NodeDependencies
        Build-Backend
        Start-Backend
        Start-Frontend
        Show-Status
    }
    "stop" {
        Stop-Services
    }
    "restart" {
        Stop-Services
        Start-Sleep -Seconds 2
        & $PSCommandPath start
    }
    "status" {
        Show-Status
    }
    "logs" {
        Show-Logs
    }
    "clean" {
        Invoke-Cleanup
    }
    "help" {
        Show-Help
    }
    default {
        Write-Error "Comando desconocido: $Command"
        Show-Help
        exit 1
    }
}
