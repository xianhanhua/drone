@echo off
rem File purpose: start the Spring Boot backend service.
setlocal EnableDelayedExpansion

cd /d "%~dp0backend"

echo Starting backend service...
echo Project directory: %cd%
echo Backend URL after startup: http://localhost:8091/
echo.

where java >nul 2>nul
if errorlevel 1 (
  echo ERROR: Java was not found.
  echo Please install JDK 8 or JDK 11 first.
  echo.
  pause
  exit /b 1
)

where mvn >nul 2>nul
if errorlevel 1 (
  rem Use the local Maven bundled in the tools folder when system Maven is not available.
  set "LOCAL_MVN="
  for /d %%D in ("%~dp0tools\apache-maven-*") do (
    if exist "%%D\bin\mvn.cmd" set "LOCAL_MVN=%%D\bin\mvn.cmd"
  )

  if not defined LOCAL_MVN (
    echo ERROR: Maven was not found.
    echo Please keep apache-maven inside the tools folder, or install Maven and add it to PATH.
    echo.
    pause
    exit /b 1
  )

  echo Using local Maven: !LOCAL_MVN!
  call "!LOCAL_MVN!" "-Dmaven.test.skip=true" spring-boot:run
) else (
  call mvn "-Dmaven.test.skip=true" spring-boot:run
)

echo.
echo Backend process has stopped.
pause
