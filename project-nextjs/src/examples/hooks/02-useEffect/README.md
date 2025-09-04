# 🎯 Задание 2: useEffect - побочные эффекты

## 📚 **Теория (70%):**

### **Что такое useEffect?**

**useEffect** - это хук для выполнения побочных эффектов в функциональных компонентах. Побочные эффекты - это операции, которые влияют на внешний мир и не связаны напрямую с рендерингом компонента.

### **Основные случаи использования:**
- 📡 Загрузка данных с сервера
- ⏰ Создание таймеров и интервалов
- 🎧 Подписка на события
- 🧹 Очистка ресурсов
- 📝 Обновление DOM напрямую

### **Синтаксис:**
```tsx
useEffect(() => {
  // Эффект - код, который выполняется
  return () => {
    // Функция очистки (опционально)
  };
}, [dependencies]); // Массив зависимостей
```

### **Типы useEffect:**

#### **1. Без зависимостей - выполняется при каждом рендере:**
```tsx
useEffect(() => {
  console.log('Компонент обновился');
  // ⚠️ Осторожно! Может вызвать бесконечный цикл
});
```

#### **2. Пустой массив - выполняется только при монтировании:**
```tsx
useEffect(() => {
  console.log('Компонент создан');
  // Идеально для инициализации
}, []);
```

#### **3. С зависимостями - выполняется при изменении зависимостей:**
```tsx
useEffect(() => {
  console.log('Count изменился:', count);
  // Выполняется только когда count меняется
}, [count]);
```

### **Функция очистки (Cleanup):**

```tsx
useEffect(() => {
  const interval = setInterval(() => {
    console.log('Тик');
  }, 1000);

  // Функция очистки
  return () => {
    clearInterval(interval); // Очищаем интервал
  };
}, []);
```

### **Практические примеры:**

#### **Пример 1: Таймер**
```tsx
function Timer() {
  const [seconds, setSeconds] = useState(0);
  const [isRunning, setIsRunning] = useState(false);

  useEffect(() => {
    if (!isRunning) return;

    const interval = setInterval(() => {
      setSeconds(prev => prev + 1);
    }, 1000);

    return () => clearInterval(interval);
  }, [isRunning]); // Зависит от isRunning

  return <div>{seconds}</div>;
}
```

#### **Пример 2: Загрузка данных**
```tsx
function UserProfile({ userId }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUser = async () => {
      setLoading(true);
      try {
        const response = await fetch(`/api/users/${userId}`);
        const userData = await response.json();
        setUser(userData);
      } catch (error) {
        console.error('Ошибка загрузки:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, [userId]); // Выполняется при изменении userId

  if (loading) return <div>Загрузка...</div>;
  return <div>{user?.name}</div>;
}
```

#### **Пример 3: Подписка на события**
```tsx
function WindowSize() {
  const [windowSize, setWindowSize] = useState({
    width: window.innerWidth,
    height: window.innerHeight
  });

  useEffect(() => {
    const handleResize = () => {
      setWindowSize({
        width: window.innerWidth,
        height: window.innerHeight
      });
    };

    window.addEventListener('resize', handleResize);
    
    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []); // Только при монтировании

  return <div>Размер: {windowSize.width}x{windowSize.height}</div>;
}
```

### **Важные правила useEffect:**

1. **Всегда указывай зависимости** - это предотвращает баги
2. **Используй функцию очистки** для таймеров, подписок, запросов
3. **Не создавай объекты/функции внутри useEffect** без зависимостей
4. **Используй ESLint правило** `exhaustive-deps` для проверки зависимостей

### **Частые ошибки:**

#### **❌ Неправильно:**
```tsx
useEffect(() => {
  const fetchData = async () => {
    const response = await fetch('/api/data');
    setData(await response.json());
  };
  fetchData();
}); // Нет зависимостей - выполняется при каждом рендере!
```

#### **✅ Правильно:**
```tsx
useEffect(() => {
  const fetchData = async () => {
    const response = await fetch('/api/data');
    setData(await response.json());
  };
  fetchData();
}, []); // Пустой массив - только при монтировании
```

### **Продвинутые паттерны:**

#### **1. Условные эффекты:**
```tsx
useEffect(() => {
  if (isLoggedIn) {
    // Загружаем данные только если пользователь авторизован
    fetchUserData();
  }
}, [isLoggedIn]);
```

#### **2. Множественные эффекты:**
```tsx
function Component() {
  // Эффект для таймера
  useEffect(() => {
    const timer = setInterval(() => {
      setTime(new Date());
    }, 1000);
    return () => clearInterval(timer);
  }, []);

  // Эффект для загрузки данных
  useEffect(() => {
    fetchData();
  }, [userId]);

  // Эффект для подписки на события
  useEffect(() => {
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);
}
```

#### **3. Кастомные хуки с useEffect:**
```tsx
function useTimer(initialTime = 0) {
  const [time, setTime] = useState(initialTime);
  const [isRunning, setIsRunning] = useState(false);

  useEffect(() => {
    if (!isRunning) return;

    const interval = setInterval(() => {
      setTime(prev => prev + 1);
    }, 1000);

    return () => clearInterval(interval);
  }, [isRunning]);

  const start = () => setIsRunning(true);
  const stop = () => setIsRunning(false);
  const reset = () => {
    setTime(initialTime);
    setIsRunning(false);
  };

  return { time, isRunning, start, stop, reset };
}
```

---

## 🎯 **Практика (30%):**

### **Задание: Создай компонент Timer**

Создай полнофункциональный таймер со следующими возможностями:

#### **Основные функции:**
1. **Отображение времени** в формате ЧЧ:ММ:СС
2. **Автоматический счетчик** каждую секунду
3. **Кнопка "Старт/Стоп"** для управления таймером
4. **Кнопка "Сброс"** для обнуления времени
5. **Отображение статуса** (работает/остановлен)

#### **Дополнительные функции:**
6. **Кнопка "Пауза"** для временной остановки
7. **Отображение миллисекунд** (опционально)
8. **Звуковое уведомление** при достижении определенного времени
9. **Сохранение времени** в localStorage

### **Технические требования:**

#### **useState для состояния:**
```tsx
const [seconds, setSeconds] = useState(0);
const [isRunning, setIsRunning] = useState(false);
const [isPaused, setIsPaused] = useState(false);
```

#### **useEffect для таймера:**
```tsx
useEffect(() => {
  if (!isRunning || isPaused) return;

  const interval = setInterval(() => {
    setSeconds(prev => prev + 1);
  }, 1000);

  return () => clearInterval(interval);
}, [isRunning, isPaused]);
```

#### **Функция форматирования времени:**
```tsx
const formatTime = (totalSeconds) => {
  const hours = Math.floor(totalSeconds / 3600);
  const minutes = Math.floor((totalSeconds % 3600) / 60);
  const seconds = totalSeconds % 60;
  
  return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
};
```

### **Пошаговый план выполнения:**

#### **Шаг 1: Базовая структура**
```tsx
function Timer() {
  const [seconds, setSeconds] = useState(0);
  const [isRunning, setIsRunning] = useState(false);

  return (
    <div>
      <div>{formatTime(seconds)}</div>
      <button onClick={() => setIsRunning(!isRunning)}>
        {isRunning ? 'Стоп' : 'Старт'}
      </button>
    </div>
  );
}
```

#### **Шаг 2: Добавь useEffect**
```tsx
useEffect(() => {
  if (!isRunning) return;

  const interval = setInterval(() => {
    setSeconds(prev => prev + 1);
  }, 1000);

  return () => clearInterval(interval);
}, [isRunning]);
```

#### **Шаг 3: Добавь функции управления**
```tsx
const start = () => setIsRunning(true);
const stop = () => setIsRunning(false);
const reset = () => {
  setSeconds(0);
  setIsRunning(false);
};
```

#### **Шаг 4: Стилизация с Tailwind**
```tsx
<div className="min-h-screen bg-gradient-to-br from-green-500 to-blue-600 flex items-center justify-center">
  <div className="bg-white/10 backdrop-blur-sm p-8 rounded-2xl border border-white/20 text-center">
    <h1 className="text-3xl font-bold text-white mb-8">Таймер</h1>
    <div className="text-6xl font-bold text-white mb-8 font-mono">
      {formatTime(seconds)}
    </div>
    {/* Кнопки */}
  </div>
</div>
```

### **Продвинутые возможности:**

#### **1. Сохранение в localStorage:**
```tsx
useEffect(() => {
  const savedTime = localStorage.getItem('timerTime');
  if (savedTime) {
    setSeconds(parseInt(savedTime));
  }
}, []);

useEffect(() => {
  localStorage.setItem('timerTime', seconds.toString());
}, [seconds]);
```

#### **2. Звуковое уведомление:**
```tsx
useEffect(() => {
  if (seconds > 0 && seconds % 60 === 0) {
    // Звук каждую минуту
    const audio = new Audio('/notification.mp3');
    audio.play();
  }
}, [seconds]);
```

#### **3. Клавиатурные сокращения:**
```tsx
useEffect(() => {
  const handleKeyPress = (e) => {
    if (e.code === 'Space') {
      e.preventDefault();
      setIsRunning(!isRunning);
    }
    if (e.code === 'KeyR') {
      reset();
    }
  };

  window.addEventListener('keydown', handleKeyPress);
  return () => window.removeEventListener('keydown', handleKeyPress);
}, [isRunning]);
```

### **Тестирование:**

#### **Проверь следующие сценарии:**
1. ✅ Таймер запускается и останавливается
2. ✅ Время отображается в правильном формате
3. ✅ Сброс работает корректно
4. ✅ Таймер очищается при размонтировании
5. ✅ Нет утечек памяти

---

## 🚀 **Начни писать код!**

Создай файл `Timer.tsx` и реализуй функциональность пошагово.

**Начни с базовой структуры, затем добавляй функции одну за другой.**

---

## ✅ **Что ты изучишь:**
- Как работает useEffect с зависимостями
- Как создавать и очищать интервалы
- Как управлять сложным состоянием
- Как форматировать время
- Как предотвращать утечки памяти
- Как создавать интерактивные компоненты
