#!/usr/bin/env bash
set -e
rm -rf out
mkdir -p out
javac -d out $(find src/main/java -name "*.java") $(find src/test/java -name "*.java")
java -Dfile.encoding=UTF-8 -cp out br.ufcg.hospital.app.Main
