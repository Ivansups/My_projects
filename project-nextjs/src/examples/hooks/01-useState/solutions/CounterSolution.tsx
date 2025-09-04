// ✅ РЕШЕНИЕ: Компонент Counter с useState
// 
// Это готовое решение для сравнения с твоим кодом
// Изучи его после выполнения задания!

import { useState } from 'react';

export default function CounterSolution() {
  // useState для управления счетчиком
  const [count, setCount] = useState(0);

  // Функции для изменения счетчика
  const increment = () => {
    setCount(count + 1);
  };

  const decrement = () => {
    setCount(count - 1);
  };

  const reset = () => {
    setCount(0);
  };

  const incrementBy5 = () => {
    setCount(count + 5);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-500 to-purple-600 flex items-center justify-center p-4">
      <div className="bg-white/10 backdrop-blur-sm p-8 rounded-2xl border border-white/20 text-center">
        <h1 className="text-3xl font-bold text-white mb-8">Счетчик</h1>
        
        {/* Отображение текущего значения */}
        <div className="text-6xl font-bold text-white mb-8">
          {count}
        </div>
        
        {/* Кнопки управления */}
        <div className="grid grid-cols-2 gap-4">
          <button 
            onClick={increment}
            className="bg-green-500 hover:bg-green-600 text-white font-semibold py-3 px-6 rounded-lg transition-colors transform hover:scale-105"
          >
            +
          </button>
          
          <button 
            onClick={decrement}
            className="bg-red-500 hover:bg-red-600 text-white font-semibold py-3 px-6 rounded-lg transition-colors transform hover:scale-105"
          >
            -
          </button>
          
          <button 
            onClick={reset}
            className="bg-gray-500 hover:bg-gray-600 text-white font-semibold py-3 px-6 rounded-lg transition-colors transform hover:scale-105"
          >
            Сброс
          </button>
          
          <button 
            onClick={incrementBy5}
            className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-3 px-6 rounded-lg transition-colors transform hover:scale-105"
          >
            +5
          </button>
        </div>
      </div>
    </div>
  );
}
