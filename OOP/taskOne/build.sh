#!/bin/bash

echo "🔨 Сборка проекта эмулятора сборщика мусора..."
echo "📁 Создание директории target/classes..."

# Создаем директорию для скомпилированных файлов
mkdir -p target/classes

echo "⚙️  Компиляция исходного кода..."

# Компилируем Java файлы в target/classes
javac -d target/classes src/*.java

if [ $? -eq 0 ]; then
    echo "✅ Компиляция завершена успешно!"
    echo ""
    echo "🚀 Доступные команды для запуска:"
    echo "   java -cp target/classes App           # Основная демонстрация"
    echo "   java -cp target/classes SimpleGC       # Простой генератор"
    echo ""
    echo "🧹 Для очистки: rm -rf target/"
else
    echo "❌ Ошибка компиляции!"
    exit 1
fi