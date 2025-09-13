#!/bin/bash

# Сборка и запуск проекта одной командой
cmake -S . -B build && cmake --build build && ./build/main
