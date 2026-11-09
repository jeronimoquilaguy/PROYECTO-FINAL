#!/bin/sh
# Compila el juego y lo deja en la carpeta out/
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")
echo "Listo. Ejecute: java -cp out doposoccer.Main"
