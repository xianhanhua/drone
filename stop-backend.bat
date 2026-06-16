@echo off
rem File purpose: stop the backend process listening on port 8091.
setlocal

echo Looking for backend process on port 8091...
for /f "tokens=5" %%P in ('netstat -ano ^| findstr ":8091" ^| findstr "LISTENING"') do (
  echo Stopping process %%P on port 8091...
  taskkill /PID %%P /F
)

echo Done.
echo.
pause
