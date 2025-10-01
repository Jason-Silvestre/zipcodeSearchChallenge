@echo off
echo 🔨 Building application...
call mvnw.cmd clean package -DskipTests

if %errorlevel% equ 0 (
    echo ✅ Build successful!
    echo 🐳 Starting PostgreSQL and Application...
    docker-compose down
    docker-compose up --build
) else (
    echo ❌ Build failed!
    pause
)