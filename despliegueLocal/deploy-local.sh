#!/bin/bash

# Script de despliegue local para lazcanoTransferApp
# Proyecto JHipster con Angular + Spring Boot + MySQL

set -e  # Salir si cualquier comando falla

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Función para imprimir mensajes con colores
print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Función para verificar si un comando existe
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Función para verificar dependencias
check_dependencies() {
    print_info "Verificando dependencias..."
    
    local missing_deps=()
    
    if ! command_exists java; then
        missing_deps+=("Java")
    fi
    
    if ! command_exists node; then
        missing_deps+=("Node.js")
    fi
    
    if ! command_exists npm; then
        missing_deps+=("NPM")
    fi
    
    if ! command_exists docker; then
        missing_deps+=("Docker")
    fi
    
    if ! command_exists docker-compose; then
        missing_deps+=("Docker Compose")
    fi
    
    if [ ${#missing_deps[@]} -ne 0 ]; then
        print_error "Faltan las siguientes dependencias:"
        for dep in "${missing_deps[@]}"; do
            echo "  - $dep"
        done
        exit 1
    fi
    
    print_success "Todas las dependencias están instaladas"
}

# Función para verificar versiones
check_versions() {
    print_info "Verificando versiones de dependencias..."
    
    local java_version=$(java -version 2>&1 | head -n1 | cut -d'"' -f2)
    local node_version=$(node --version)
    local npm_version=$(npm --version)
    
    print_info "Java: $java_version"
    print_info "Node.js: $node_version"
    print_info "NPM: $npm_version"
    
    # Verificar versión mínima de Node.js (>=14.17.1)
    local node_major=$(echo $node_version | cut -d'.' -f1 | sed 's/v//')
    if [ "$node_major" -lt 14 ]; then
        print_error "Se requiere Node.js >= 14.17.1. Versión actual: $node_version"
        exit 1
    fi
    
    print_success "Versiones verificadas correctamente"
}

# Función para limpiar procesos anteriores
cleanup() {
    print_info "Limpiando procesos anteriores..."
    
    # Detener contenedores de Docker si están corriendo
    if docker-compose -f src/main/docker/mysql.yml ps -q > /dev/null 2>&1; then
        print_info "Deteniendo contenedores de base de datos..."
        docker-compose -f src/main/docker/mysql.yml down -v --remove-orphans >/dev/null 2>&1 || true
    fi
    
    # Matar procesos de Java (Spring Boot) en puerto 8080
    local java_pid=$(lsof -ti:8080 | grep -v grep || true)
    if [ ! -z "$java_pid" ]; then
        print_info "Deteniendo proceso Java en puerto 8080..."
        kill -9 $java_pid >/dev/null 2>&1 || true
        sleep 2
    fi
    
    # Matar procesos de Angular en puerto 4200
    local ng_pid=$(lsof -ti:4200 | grep -v grep || true)
    if [ ! -z "$ng_pid" ]; then
        print_info "Deteniendo proceso Angular en puerto 4200..."
        kill -9 $ng_pid >/dev/null 2>&1 || true
        sleep 2
    fi
    
    print_success "Limpieza completada"
}

# Función para iniciar la base de datos
start_database() {
    print_info "Iniciando base de datos MySQL..."
    
    # Verificar si Docker está corriendo
    if ! docker info >/dev/null 2>&1; then
        print_error "Docker no está corriendo. Por favor, inicia Docker Desktop"
        exit 1
    fi
    
    # Iniciar MySQL con docker-compose
    docker-compose -f src/main/docker/mysql.yml up -d
    
    print_info "Esperando que MySQL esté listo..."
    local attempts=0
    local max_attempts=30
    
    while [ $attempts -lt $max_attempts ]; do
        if docker-compose -f src/main/docker/mysql.yml exec -T transferlazcano-mysql mysqladmin ping -h localhost --silent >/dev/null 2>&1; then
            print_success "MySQL está listo"
            return 0
        fi
        
        sleep 2
        attempts=$((attempts + 1))
        echo -n "."
    done
    
    print_error "MySQL no pudo iniciarse correctamente"
    exit 1
}

# Función para instalar dependencias de Node.js
install_node_dependencies() {
    print_info "Instalando dependencias de Node.js..."
    
    if [ ! -d "node_modules" ] || [ "package.json" -nt "node_modules" ]; then
        npm install
        print_success "Dependencias de Node.js instaladas"
    else
        print_info "Dependencias de Node.js ya están actualizadas"
    fi
}

# Función para construir el frontend
build_frontend() {
    print_info "Construyendo frontend Angular..."
    npm run webapp:build:dev
    print_success "Frontend construido exitosamente"
}

# Función para construir el backend
build_backend() {
    print_info "Construyendo backend Spring Boot..."
    
    # Limpiar y construir
    ./mvnw clean compile -DskipTests
    
    print_success "Backend construido exitosamente"
}

# Función para iniciar el backend
start_backend() {
    print_info "Iniciando backend Spring Boot..."
    
    # Ejecutar en background
    nohup ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev > backend.log 2>&1 &
    local backend_pid=$!
    
    print_info "Backend iniciando (PID: $backend_pid)..."
    
    # Esperar que el backend esté listo
    local attempts=0
    local max_attempts=60
    
    while [ $attempts -lt $max_attempts ]; do
        if curl -s http://localhost:8080/management/health >/dev/null 2>&1; then
            print_success "Backend está listo en http://localhost:8080"
            return 0
        fi
        
        sleep 3
        attempts=$((attempts + 1))
        echo -n "."
    done
    
    print_error "Backend no pudo iniciarse correctamente"
    print_info "Revisa el archivo backend.log para más detalles"
    exit 1
}

# Función para iniciar el frontend en modo desarrollo
start_frontend() {
    print_info "Iniciando frontend Angular en modo desarrollo..."
    
    # Ejecutar en background
    nohup npm run serve > frontend.log 2>&1 &
    local frontend_pid=$!
    
    print_info "Frontend iniciando (PID: $frontend_pid)..."
    
    # Esperar que el frontend esté listo
    local attempts=0
    local max_attempts=30
    
    while [ $attempts -lt $max_attempts ]; do
        if curl -s http://localhost:4200 >/dev/null 2>&1; then
            print_success "Frontend está listo en http://localhost:4200"
            return 0
        fi
        
        sleep 3
        attempts=$((attempts + 1))
        echo -n "."
    done
    
    print_error "Frontend no pudo iniciarse correctamente"
    print_info "Revisa el archivo frontend.log para más detalles"
    exit 1
}

# Función para mostrar estado de los servicios
show_status() {
    echo ""
    print_success "=== DESPLIEGUE LOCAL COMPLETADO ==="
    echo ""
    echo "🚀 Servicios disponibles:"
    echo "   • Frontend (Angular):    http://localhost:4200"
    echo "   • Backend (Spring Boot): http://localhost:8080"
    echo "   • API Docs (Swagger):    http://localhost:8080/swagger-ui/"
    echo "   • Health Check:          http://localhost:8080/management/health"
    echo "   • MySQL Database:        localhost:3306 (transferlazcano/root/sin_password)"
    echo ""
    echo "📋 Comandos útiles:"
    echo "   • Ver logs del backend:  tail -f backend.log"
    echo "   • Ver logs del frontend: tail -f frontend.log"
    echo "   • Ver logs de MySQL:     docker-compose -f src/main/docker/mysql.yml logs -f"
    echo "   • Detener servicios:     ./deploy-local.sh stop"
    echo ""
}

# Función para detener todos los servicios
stop_services() {
    print_info "Deteniendo todos los servicios..."
    
    # Detener frontend
    local ng_pid=$(lsof -ti:4200 | grep -v grep || true)
    if [ ! -z "$ng_pid" ]; then
        print_info "Deteniendo frontend Angular..."
        kill -15 $ng_pid >/dev/null 2>&1 || true
        sleep 3
        kill -9 $ng_pid >/dev/null 2>&1 || true
    fi
    
    # Detener backend
    local java_pid=$(lsof -ti:8080 | grep -v grep || true)
    if [ ! -z "$java_pid" ]; then
        print_info "Deteniendo backend Spring Boot..."
        kill -15 $java_pid >/dev/null 2>&1 || true
        sleep 3
        kill -9 $java_pid >/dev/null 2>&1 || true
    fi
    
    # Detener MySQL
    if docker-compose -f src/main/docker/mysql.yml ps -q > /dev/null 2>&1; then
        print_info "Deteniendo MySQL..."
        docker-compose -f src/main/docker/mysql.yml down -v --remove-orphans
    fi
    
    print_success "Todos los servicios han sido detenidos"
}

# Función para mostrar logs
show_logs() {
    echo "=== LOGS EN TIEMPO REAL ==="
    echo "Presiona Ctrl+C para salir"
    echo ""
    
    if [ -f "backend.log" ] && [ -f "frontend.log" ]; then
        tail -f backend.log frontend.log
    elif [ -f "backend.log" ]; then
        tail -f backend.log
    elif [ -f "frontend.log" ]; then
        tail -f frontend.log
    else
        print_warning "No se encontraron archivos de log"
    fi
}

# Función para mostrar ayuda
show_help() {
    echo "Script de despliegue local para lazcanoTransferApp"
    echo ""
    echo "Uso: $0 [COMANDO]"
    echo ""
    echo "Comandos disponibles:"
    echo "  start     - Inicia todos los servicios (por defecto)"
    echo "  stop      - Detiene todos los servicios"
    echo "  restart   - Reinicia todos los servicios"
    echo "  status    - Muestra el estado de los servicios"
    echo "  logs      - Muestra logs en tiempo real"
    echo "  clean     - Limpia procesos y contenedores"
    echo "  help      - Muestra esta ayuda"
    echo ""
}

# Función principal
main() {
    local command=${1:-start}
    
    case $command in
        "start")
            print_info "Iniciando despliegue local de lazcanoTransferApp..."
            check_dependencies
            check_versions
            cleanup
            start_database
            install_node_dependencies
            build_backend
            start_backend
            start_frontend
            show_status
            ;;
        "stop")
            stop_services
            ;;
        "restart")
            stop_services
            sleep 2
            main start
            ;;
        "status")
            show_status
            ;;
        "logs")
            show_logs
            ;;
        "clean")
            cleanup
            ;;
        "help"|"-h"|"--help")
            show_help
            ;;
        *)
            print_error "Comando desconocido: $command"
            show_help
            exit 1
            ;;
    esac
}

# Manejar señales para limpieza
trap cleanup EXIT INT TERM

# Ejecutar función principal
main "$@"
