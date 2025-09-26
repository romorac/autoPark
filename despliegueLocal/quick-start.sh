#!/bin/bash

# Script de inicio rápido para desarrollo local
# Para desarrolladores que ya tienen todo configurado

set -e

echo "🚀 Iniciando lazcanoTransferApp en modo desarrollo..."

# Iniciar MySQL
#echo "📊 Iniciando base de datos..."
#docker-compose -f src/main/docker/mysql.yml up -d

# Esperar un momento para que MySQL inicie
#sleep 5

# Iniciar backend en background
echo "⚙️ Iniciando backend..."
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev > backend.log 2>&1 &

# Iniciar frontend en background  
echo "🎨 Iniciando frontend..."
#npm start > frontend.log 2>&1 &

echo ""
echo "✅ Servicios iniciándose..."
echo ""
echo "🌐 URLs disponibles en unos minutos:"
echo "   • Frontend: http://localhost:4200"
echo "   • Backend:  http://localhost:8080"
echo "   • Swagger:  http://localhost:8080/swagger-ui/"
echo ""
echo "📋 Para ver logs:"
echo "   • Backend:  tail -f backend.log"
echo "   • Frontend: tail -f frontend.log"
echo ""
echo "🛑 Para detener: ./deploy-local.sh stop"
