#!/bin/bash

# Скрипт для запуска Main.cpp

echo "=== Запуск программы числового ряда ==="
echo ""

# Проверяем, существует ли папка build
if [ ! -d "build" ]; then
    echo "Создаем папку build и собираем проект..."
    mkdir -p build
    cd build
    cmake ..
    make
    cd ..
    echo "Проект собран успешно!"
    echo ""
fi

# Запускаем программу
echo "Запускаем Main (Числовой ряд)..."
cd build && ./main
