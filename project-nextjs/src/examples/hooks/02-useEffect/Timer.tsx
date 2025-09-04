// 🎯 ЗАДАНИЕ: Создай компонент Timer с useEffect
// 
// Твоя задача: реализовать функциональность таймера
// 
// 1. Добавь useState для времени и статуса
// 2. Создай useEffect для автоматического счетчика
// 3. Добавь функции управления таймером
// 4. Стилизуй компонент с Tailwind
// 
// Начни писать код здесь:

import { useState, useEffect } from 'react';

export default function Timer() {
  // TODO: Добавь useState для времени (секунды)
  // const [seconds, setSeconds] = useState(0);
  
  // TODO: Добавь useState для статуса (работает/остановлен)
  // const [isRunning, setIsRunning] = useState(false);

  // TODO: Создай useEffect для автоматического счетчика
  // useEffect(() => {
  //   if (!isRunning) return;
  //   
  //   const interval = setInterval(() => {
  //     setSeconds(seconds => seconds + 1);
  //   }, 1000);
  //   
  //   return () => clearInterval(interval);
  // }, [isRunning]);

  // TODO: Создай функции управления
  // const start = () => { ... }
  // const stop = () => { ... }
  // const reset = () => { ... }

  // TODO: Создай функцию форматирования времени
  // const formatTime = (totalSeconds) => {
  //   const hours = Math.floor(totalSeconds / 3600);
  //   const minutes = Math.floor((totalSeconds % 3600) / 60);
  //   const seconds = totalSeconds % 60;
  //   return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
  // };

  return (
    <div className="min-h-screen bg-gradient-to-br from-green-500 to-blue-600 flex items-center justify-center p-4">
      <div className="bg-white/10 backdrop-blur-sm p-8 rounded-2xl border border-white/20 text-center">
        <h1 className="text-3xl font-bold text-white mb-8">Таймер</h1>
        
        {/* TODO: Отобрази текущее время */}
        <div className="text-6xl font-bold text-white mb-8 font-mono">
          00:00:00
        </div>
        
        {/* TODO: Отобрази статус */}
        <div className="mb-8">
          <span className="bg-red-500/20 text-red-300 px-4 py-2 rounded-full text-sm font-medium">
            Остановлен
          </span>
        </div>
        
        {/* TODO: Создай кнопки управления */}
        <div className="grid grid-cols-3 gap-4">
          <button className="bg-green-500 hover:bg-green-600 text-white font-semibold py-3 px-6 rounded-lg transition-colors">
            Старт
          </button>
          
          <button className="bg-red-500 hover:bg-red-600 text-white font-semibold py-3 px-6 rounded-lg transition-colors">
            Стоп
          </button>
          
          <button className="bg-gray-500 hover:bg-gray-600 text-white font-semibold py-3 px-6 rounded-lg transition-colors">
            Сброс
          </button>
        </div>
      </div>
    </div>
  );
}
