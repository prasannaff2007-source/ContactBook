@echo off
setlocal
title Contact Book - build and deploy

rem ---- Settings (change only if your folders are different) ----
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-25.0.4.101-hotspot"
set "CATALINA_HOME=C:\tomcat\apache-tomcat-10.1.61"

cd /d "%~dp0"

echo.
echo [1/4] Stopping Tomcat (if it is running)...
call "%CATALINA_HOME%\bin\shutdown.bat" >nul 2>&1
timeout /t 5 /nobreak >nul

echo [2/4] Building the project with Maven (first time needs internet)...
call mvn clean package
if errorlevel 1 (
    echo.
    echo BUILD FAILED. Read the messages above, fix the problem, then run deploy.bat again.
    pause
    exit /b 1
)

echo [3/4] Copying ContactBook.war into Tomcat...
if exist "%CATALINA_HOME%\webapps\ContactBook" rmdir /s /q "%CATALINA_HOME%\webapps\ContactBook"
if exist "%CATALINA_HOME%\webapps\ContactBook.war" del /q "%CATALINA_HOME%\webapps\ContactBook.war"
copy /y "target\ContactBook.war" "%CATALINA_HOME%\webapps\" >nul

echo [4/4] Starting Tomcat in a new window (do NOT close that window)...
call "%CATALINA_HOME%\bin\startup.bat"

echo.
echo Wait about 10 seconds, then open this in Chrome:
echo     http://localhost:8080/ContactBook/
echo.
pause
