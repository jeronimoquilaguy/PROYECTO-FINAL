@echo off
REM Compila el juego y lo deja en la carpeta out
if not exist out mkdir out
dir /s /b src\*.java > fuentes.txt
javac -encoding UTF-8 -d out @fuentes.txt
del fuentes.txt
echo Listo. Ejecute: java -cp out doposoccer.Main
