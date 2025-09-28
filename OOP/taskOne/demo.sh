#!/bin/bash

echo "🔨 Сборка и демонстрация проекта эмулятора сборщика мусора..."

# Создаем директорию для скомпилированных файлов
mkdir -p target/classes

# Перемещаем .class файлы из src/ если они есть
if find src -name "*.class" -type f | grep -q .; then
    echo "📁 Перемещаем .class файлы из src/ в target/classes/..."
    find src -name "*.class" -type f | while read file; do
        rel_path=${file#src/}
        mkdir -p "target/classes/$(dirname "$rel_path")"
        mv "$file" "target/classes/$rel_path"
    done
fi

echo "⚙️  Компиляция исходного кода..."

# Компилируем Java файлы в target/classes
javac -d target/classes src/*.java

if [ $? -eq 0 ]; then
    echo "✅ Компиляция завершена успешно!"
    echo ""
    echo "📊 Структура:"
    echo "   src/ содержит: $(find src -name "*.java" -type f | wc -l) .java файлов"
    echo "   target/classes/ содержит: $(find target/classes -name "*.class" -type f | wc -l) .class файлов"
    echo ""
    
    # Запускаем SimpleGC с таймаутом
    echo "🚀 Демонстрация SimpleGC (10 секунд)..."
    echo ""
    
    # Запускаем SimpleGC в фоне
    java -cp target/classes SimpleGC &
    GC_PID=$!
    
    # Ждем 10 секунд
    sleep 10
    
    # Останавливаем SimpleGC
    kill $GC_PID 2>/dev/null
    wait $GC_PID 2>/dev/null
    
    echo ""
    echo "🛑 Демонстрация завершена"
    
    # Очищаем .class файлы из src/ если они появились
    if find src -name "*.class" -type f | grep -q .; then
        echo "🧹 Очищаем .class файлы из src/..."
        find src -name "*.class" -type f -delete
        echo "✅ Очистка завершена"
    fi
    
    echo ""
    echo "📊 Финальная структура:"
    echo "   src/ содержит: $(find src -name "*.java" -type f | wc -l) .java файлов"
    echo "   target/classes/ содержит: $(find target/classes -name "*.class" -type f | wc -l) .class файлов"
    
else
    echo "❌ Ошибка компиляции!"
    exit 1
fi
