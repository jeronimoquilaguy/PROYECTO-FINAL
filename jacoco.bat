@echo off
REM ANALISIS DINAMICO sin Maven: corre las pruebas con el agente de JaCoCo y arma el reporte HTML.
REM Necesita en lib\: junit-platform-console-standalone-1.10.2.jar, jacocoagent.jar y jacococli.jar
set JUNIT=lib\junit-platform-console-standalone-1.10.2.jar
if not exist out mkdir out
if not exist out-test mkdir out-test
dir /s /b src\*.java > fuentes.txt
javac -encoding UTF-8 -g -d out @fuentes.txt
dir /s /b test\*.java > pruebas.txt
javac -encoding UTF-8 -cp "out;%JUNIT%" -d out-test @pruebas.txt
del fuentes.txt pruebas.txt
if exist jacoco.exec del jacoco.exec
java -Djava.awt.headless=true -javaagent:lib\jacocoagent.jar=destfile=jacoco.exec -jar "%JUNIT%" execute --class-path "out-test;out" --scan-class-path
java -jar lib\jacococli.jar report jacoco.exec --classfiles out --sourcefiles src --html reporte-jacoco --csv reporte-jacoco\jacoco.csv --name DOPOSoccer
echo Abra reporte-jacoco\index.html
