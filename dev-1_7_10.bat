@echo off
REM ============================================================================
REM  build-and-run.bat — сборка JAR через контейнер builder и запуск dev-сервера
REM ============================================================================

REM --- 1) Сборка Docker-образа builder ---
echo [1/3] Сборка образа builder...
docker compose -f docker-compose.builder-1_7_10.yml build sidemc-builder
if ERRORLEVEL 1 (
  echo Ошибка при сборке образа builder! Прекращаем.
  exit /b 1
)

REM --- 2) Запуск builder-контейнера для сборки JAR ---
echo [2/3] Запуск builder-контейнера...
docker compose -f docker-compose.builder-1_7_10.yml run --rm sidemc-builder
if ERRORLEVEL 1 (
  echo Ошибка в процессе сборки JAR внутри контейнера! Прекращаем.
  exit /b 1
)

REM --- 3) Копирование полученного JAR и запуск dev-сервера ---
echo [3/3] Копирование JAR в dev-server\1_7_10\mods и запуск тестового сервера...
if not exist "dev-server\1_7_10\mods" (
  mkdir "dev-server\1_7_10\mods"
)

copy /Y "build\libs\SideMCShop-1.7.10-server.jar" "dev-server\1_7_10\mods\"
if ERRORLEVEL 1 (
  echo Не удалось скопировать JAR! Прекращаем.
  exit /b 1
)

cd dev-server\1_7_10
docker compose -f docker-compose.dev-server-1_7_10.yml up

exit /b 0
