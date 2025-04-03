@echo off
echo Iniciando la aplicación completa...
echo.
echo Iniciando el backend...
start cmd /k "cd backend && mvn spring-boot:run"
echo.
echo Iniciando el frontend...
start cmd /k "cd frontend && npm run dev"
echo.
echo ¡Aplicación iniciada!
echo Backend en http://localhost:8080/api
echo Frontend en http://localhost:5173 