cmake_minimum_required(VERSION 3.16)
project(CppLessons)

# Устанавливаем стандарт C++
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Включаем отладочную информацию
set(CMAKE_BUILD_TYPE Debug)

# Добавляем флаги компилятора
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra -Wpedantic")

# Создаем исполняемый файл для Main.cpp
add_executable(main src/Main.cpp)

# Если есть заголовочные файлы в include
include_directories(include)
