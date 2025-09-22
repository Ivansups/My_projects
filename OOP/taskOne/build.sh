#!/bin/bash

# Скрипт для сборки проекта эмулятора сборщика мусора Java

echo "🔨 Сборка проекта эмулятора сборщика мусора Java..."

# Создаем директорию для скомпилированных файлов
echo "📁 Создание директории build/classes..."
mkdir -p build/classes

# Компилируем исходный код
echo "⚙️  Компиляция исходного кода..."
javac -d build/classes src/*.java

if [ $? -eq 0 ]; then
    echo "✅ Компиляция завершена успешно!"
    echo ""
    echo "🚀 Доступные команды для запуска:"
    echo "   java -cp build/classes App          # Основная демонстрация"
    echo "   java -cp build/classes GCTest       # Тесты"
    echo "   java -cp build/classes GCExample    # Дополнительные примеры"
    echo ""
    echo "🧹 Для очистки: rm -rf build/"
else
    echo "❌ Ошибка компиляции!"
    exit 1
fi
