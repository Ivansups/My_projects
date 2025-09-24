#!/bin/bash
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
echo "Запуск программы вычисления определителя матрицы..."
echo "================================================"
echo "Входная матрица:"
cat "$SCRIPT_DIR/input.txt"
echo ""
echo "Результат:"
cd "$SCRIPT_DIR" && /opt/homebrew/bin/python3 main.py
echo ""
echo "Содержимое output.txt:"
cat "$SCRIPT_DIR/output.txt"
