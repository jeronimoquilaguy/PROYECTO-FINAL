#!/bin/sh
# Compila y ejecuta las pruebas de unidad (necesita junit-platform-console-standalone.jar)
JUNIT=${1:-/usr/share/java/junit-platform-console-standalone.jar}
mkdir -p out-test
javac -encoding UTF-8 -cp "$JUNIT" -d out-test $(find src/doposoccer/modelo test -name "*.java")
java -jar "$JUNIT" execute --class-path out-test --scan-class-path
