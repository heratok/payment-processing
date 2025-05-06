@echo off
echo Iniciando la aplicación completa...
echo.
echo Iniciando el backend de pagos...
start cmd /k "cd backend_pagos && mvn spring-boot:run"
echo.
echo Iniciando el backend de notificaciones...
start cmd /k "cd backend_notificaciones && mvn spring-boot:run"
echo.
echo Iniciando el backend de reportes...
start cmd /k "cd backend_reportes && mvn spring-boot:run"
echo.
echo Iniciando el frontend...
start cmd /k "cd frontend && npm run dev"
echo.
echo ¡Aplicación iniciada!
echo Backend de pagos en http://localhost:8080/api
echo Backend de notificaciones en http://localhost:8081/api
echo Backend de notificaciones en http://localhost:8082/api
echo Frontend en http://localhost:5173