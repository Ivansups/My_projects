// ✅ РЕШЕНИЕ: Компонент Timer с useEffect
// 
// Это полное решение с подробными комментариями
// Изучи его после выполнения задания!

import { useState, useEffect } from 'react';

export default function TimerSolution() {
  // Состояние для времени (в секундах)
  const [seconds, setSeconds] = useState(0);
  
  // Состояние для управления таймером
  const [isRunning, setIsRunning] = useState(false);
  
  // Состояние для паузы (дополнительная функция)
  const [isPaused, setIsPaused] = useState(false);

  // useEffect для автоматического счетчика
  useEffect(() => {
    // Если таймер не запущен или на паузе - выходим
    if (!isRunning || isPaused) return;

    // Создаем интервал, который выполняется каждую секунду
    const interval = setInterval(() => {
      // Используем функциональное обновление для безопасности
      setSeconds(prev => prev + 1);
    }, 1000);

    // Функция очистки - ОЧЕНЬ ВАЖНО!
    // Очищаем интервал при размонтировании или изменении зависимостей
    return () => {
      clearInterval(interval);
    };
  }, [isRunning, isPaused]); // Зависимости: эффект выполняется при изменении этих значений

  // useEffect для сохранения времени в localStorage
  useEffect(() => {
    // Сохраняем время при каждом изменении
    localStorage.setItem('timerTime', seconds.toString());
  }, [seconds]);

  // useEffect для загрузки сохраненного времени при монтировании
  useEffect(() => {
    const savedTime = localStorage.getItem('timerTime');
    if (savedTime) {
      setSeconds(parseInt(savedTime));
    }
  }, []); // Пустой массив - выполняется только при монтировании

  // useEffect для звукового уведомления каждую минуту
  useEffect(() => {
    if (seconds > 0 && seconds % 60 === 0) {
      // Создаем звуковое уведомление
      const audio = new Audio('data:audio/wav;base64,UklGRnoGAABXQVZFZm10IBAAAAABAAEAQB8AAEAfAAABAAgAZGF0YQoGAACBhYqFbF1fdJivrJBhNjVgodDbq2EcBj+a2/LDciUFLIHO8tiJNwgZaLvt559NEAxQp+PwtmMcBjiR1/LMeSwFJHfH8N2QQAoUXrTp66hVFApGn+DyvmwhBSuBzvLZiTYIG2m98OScTgwOUarm7blmGgU7k9n1unEiBC13yO/eizEIHWq+8+OWT');
      audio.volume = 0.3; // Уменьшаем громкость
      audio.play().catch(() => {
        // Игнорируем ошибки воспроизведения (если браузер блокирует)
      });
    }
  }, [seconds]);

  // useEffect для клавиатурных сокращений
  useEffect(() => {
    const handleKeyPress = (e) => {
      // Пробел - старт/стоп
      if (e.code === 'Space') {
        e.preventDefault();
        setIsRunning(!isRunning);
      }
      // R - сброс
      if (e.code === 'KeyR') {
        reset();
      }
      // P - пауза
      if (e.code === 'KeyP') {
        setIsPaused(!isPaused);
      }
    };

    // Добавляем слушатель событий
    window.addEventListener('keydown', handleKeyPress);
    
    // Очищаем слушатель при размонтировании
    return () => {
      window.removeEventListener('keydown', handleKeyPress);
    };
  }, [isRunning, isPaused]); // Зависимости для корректной работы

  // Функция форматирования времени
  const formatTime = (totalSeconds) => {
    const hours = Math.floor(totalSeconds / 3600);
    const minutes = Math.floor((totalSeconds % 3600) / 60);
    const seconds = totalSeconds % 60;
    
    return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
  };

  // Функции управления таймером
  const start = () => {
    setIsRunning(true);
    setIsPaused(false);
  };

  const stop = () => {
    setIsRunning(false);
    setIsPaused(false);
  };

  const pause = () => {
    setIsPaused(!isPaused);
  };

  const reset = () => {
    setSeconds(0);
    setIsRunning(false);
    setIsPaused(false);
    localStorage.removeItem('timerTime'); // Очищаем сохраненное время
  };

  // Определяем статус для отображения
  const getStatus = () => {
    if (isPaused) return 'Пауза';
    if (isRunning) return 'Работает';
    return 'Остановлен';
  };

  // Определяем цвет статуса
  const getStatusColor = () => {
    if (isPaused) return 'bg-yellow-500/20 text-yellow-300';
    if (isRunning) return 'bg-green-500/20 text-green-300';
    return 'bg-red-500/20 text-red-300';
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-green-500 to-blue-600 flex items-center justify-center p-4">
      <div className="bg-white/10 backdrop-blur-sm p-8 rounded-2xl border border-white/20 text-center max-w-md w-full">
        <h1 className="text-3xl font-bold text-white mb-8">Таймер</h1>
        
        {/* Отображение времени */}
        <div className="text-6xl font-bold text-white mb-8 font-mono">
          {formatTime(seconds)}
        </div>
        
        {/* Статус */}
        <div className="mb-8">
          <span className={`px-4 py-2 rounded-full text-sm font-medium ${getStatusColor()}`}>
            {getStatus()}
          </span>
        </div>
        
        {/* Кнопки управления */}
        <div className="grid grid-cols-2 gap-4 mb-6">
          <button 
            onClick={isRunning ? stop : start}
            className={`font-semibold py-3 px-6 rounded-lg transition-all duration-300 transform hover:scale-105 ${
              isRunning 
                ? 'bg-red-500 hover:bg-red-600 text-white' 
                : 'bg-green-500 hover:bg-green-600 text-white'
            }`}
          >
            {isRunning ? 'Стоп' : 'Старт'}
          </button>
          
          <button 
            onClick={pause}
            disabled={!isRunning}
            className="bg-yellow-500 hover:bg-yellow-600 disabled:bg-gray-500 disabled:cursor-not-allowed text-white font-semibold py-3 px-6 rounded-lg transition-all duration-300 transform hover:scale-105 disabled:transform-none"
          >
            {isPaused ? 'Продолжить' : 'Пауза'}
          </button>
          
          <button 
            onClick={reset}
            className="bg-gray-500 hover:bg-gray-600 text-white font-semibold py-3 px-6 rounded-lg transition-all duration-300 transform hover:scale-105 col-span-2"
          >
            Сброс
          </button>
        </div>

        {/* Информация о клавиатурных сокращениях */}
        <div className="text-white/60 text-sm space-y-1">
          <p>Клавиатурные сокращения:</p>
          <p>Пробел - Старт/Стоп</p>
          <p>P - Пауза</p>
          <p>R - Сброс</p>
        </div>
      </div>
    </div>
  );
}
