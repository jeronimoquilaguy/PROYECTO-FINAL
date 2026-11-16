@echo off
REM Compila y ejecuta las pruebas de unidad (necesita lib\junit-platform-console-standalone-1.10.2.jar)
set JUNIT=lib\junit-platform-console-standalone-1.10.2.jar
if not exist out mkdir out
if not exist out-test mkdir out-test
dir /s /b src\*.java > fuentes.txt
javac -encoding UTF-8 -d out @fuentes.txt
dir /s /b test\*.java > pruebas.txt
javac -encoding UTF-8 -cp "out;%JUNIT%" -d out-test @pruebas.txt
del fuentes.txt pruebas.txt
java -Djava.awt.headless=true -jar "%JUNIT%" execute --class-path "out-test;out" --scan-class-path
