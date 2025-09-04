# 🎯 Задание 1: useState - управление состоянием

## 📚 **Теория (70%):**

### **Что такое useState?**

**useState** - это хук для управления состоянием в функциональных компонентах React. Состояние - это данные, которые могут изменяться во времени и влияют на рендеринг компонента.

### **Основные принципы:**
- 🔄 **Реактивность** - изменения состояния автоматически обновляют UI
- 🎯 **Локальность** - состояние принадлежит конкретному компоненту
- ⚡ **Производительность** - React оптимизирует обновления
- 🧠 **Предсказуемость** - состояние изменяется только через setState

### **Синтаксис:**
```tsx
const [state, setState] = useState(initialValue);
```

### **Параметры:**
- `state` - текущее значение состояния
- `setState` - функция для обновления состояния
- `initialValue` - начальное значение (вычисляется только при первом рендере)

### **Типы состояния:**

#### **1. Примитивные типы:**
```tsx
// Число
const [count, setCount] = useState(0);

// Строка
const [name, setName] = useState('');

// Булево значение
const [isVisible, setIsVisible] = useState(false);

// null/undefined
const [data, setData] = useState(null);
```

#### **2. Объекты:**
```tsx
// Простой объект
const [user, setUser] = useState({ 
  name: '', 
  email: '', 
  age: 0 
});

// Обновление объекта (ВАЖНО!)
setUser(prev => ({
  ...prev,
  name: 'Иван'
}));
```

#### **3. Массивы:**
```tsx
// Массив
const [items, setItems] = useState([]);

// Добавление элемента
setItems(prev => [...prev, newItem]);

// Удаление элемента
setItems(prev => prev.filter(item => item.id !== idToRemove));
```

### **Функциональные обновления:**

#### **Когда использовать:**
- Когда новое значение зависит от предыдущего
- Для предотвращения проблем с замыканиями
- При работе с асинхронными операциями

#### **Примеры:**
```tsx
// ❌ Проблема с замыканием
const increment = () => {
  setCount(count + 1); // count может быть устаревшим
};

// ✅ Функциональное обновление
const increment = () => {
  setCount(prev => prev + 1); // всегда актуальное значение
};

// Сложные вычисления
const updateScore = () => {
  setScore(prev => {
    const newScore = prev + points;
    return newScore > 100 ? 100 : newScore;
  });
};
```

### **Продвинутые паттерны:**

#### **1. Ленивая инициализация:**
```tsx
// Вычисление начального значения только при первом рендере
const [expensiveValue, setExpensiveValue] = useState(() => {
  return calculateExpensiveValue();
});
```

#### **2. Множественное состояние:**
```tsx
function Form() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [age, setAge] = useState(0);
  
  // Или объединить в один объект
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    age: 0
  });
}
```

#### **3. Условная инициализация:**
```tsx
function Component({ initialCount }) {
  const [count, setCount] = useState(() => {
    // Логика инициализации
    return initialCount || 0;
  });
}
```

### **Правила использования useState:**

#### **✅ Правильно:**
```tsx
// 1. Всегда используй setState для обновления
setCount(count + 1);

// 2. Используй функциональные обновления когда нужно
setCount(prev => prev + 1);

// 3. Обновляй объекты правильно
setUser(prev => ({ ...prev, name: 'Новое имя' }));

// 4. Обновляй массивы правильно
setItems(prev => [...prev, newItem]);
```

#### **❌ Неправильно:**
```tsx
// 1. НЕ мутируй состояние напрямую
count++; // ❌
user.name = 'Новое имя'; // ❌
items.push(newItem); // ❌

// 2. НЕ используй setState в циклах без функционального обновления
for (let i = 0; i < 10; i++) {
  setCount(count + 1); // ❌ Будет только +1
}

// 3. НЕ создавай объекты в рендере
const [user, setUser] = useState({ name: '', email: '' }); // ✅
```

### **Оптимизация производительности:**

#### **1. Мемоизация с useMemo:**
```tsx
const expensiveValue = useMemo(() => {
  return calculateExpensiveValue(state);
}, [state]);
```

#### **2. Мемоизация функций с useCallback:**
```tsx
const handleClick = useCallback(() => {
  setCount(prev => prev + 1);
}, []);
```

#### **3. Разделение состояния:**
```tsx
// ❌ Плохо - все в одном состоянии
const [state, setState] = useState({
  count: 0,
  name: '',
  isVisible: false
});

// ✅ Хорошо - разделенное состояние
const [count, setCount] = useState(0);
const [name, setName] = useState('');
const [isVisible, setIsVisible] = useState(false);
```

### **Практические примеры:**

#### **Пример 1: Счетчик с историей**
```tsx
function CounterWithHistory() {
  const [count, setCount] = useState(0);
  const [history, setHistory] = useState([]);

  const increment = () => {
    setCount(prev => {
      const newCount = prev + 1;
      setHistory(prevHistory => [...prevHistory, newCount]);
      return newCount;
    });
  };

  return (
    <div>
      <div>Счетчик: {count}</div>
      <button onClick={increment}>+</button>
      <div>История: {history.join(', ')}</div>
    </div>
  );
}
```

#### **Пример 2: Форма с валидацией**
```tsx
function Form() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    errors: {}
  });

  const updateField = (field, value) => {
    setFormData(prev => ({
      ...prev,
      [field]: value,
      errors: { ...prev.errors, [field]: null }
    }));
  };

  const validateForm = () => {
    const errors = {};
    if (!formData.name) errors.name = 'Имя обязательно';
    if (!formData.email) errors.email = 'Email обязателен';
    
    setFormData(prev => ({ ...prev, errors }));
    return Object.keys(errors).length === 0;
  };

  return (
    <form>
      <input 
        value={formData.name}
        onChange={(e) => updateField('name', e.target.value)}
        placeholder="Имя"
      />
      {formData.errors.name && <span>{formData.errors.name}</span>}
      
      <input 
        value={formData.email}
        onChange={(e) => updateField('email', e.target.value)}
        placeholder="Email"
      />
      {formData.errors.email && <span>{formData.errors.email}</span>}
      
      <button onClick={validateForm}>Отправить</button>
    </form>
  );
}
```

#### **Пример 3: Список с фильтрацией**
```tsx
function TodoList() {
  const [todos, setTodos] = useState([]);
  const [filter, setFilter] = useState('all');

  const addTodo = (text) => {
    setTodos(prev => [...prev, {
      id: Date.now(),
      text,
      completed: false
    }]);
  };

  const toggleTodo = (id) => {
    setTodos(prev => prev.map(todo =>
      todo.id === id ? { ...todo, completed: !todo.completed } : todo
    ));
  };

  const filteredTodos = todos.filter(todo => {
    if (filter === 'completed') return todo.completed;
    if (filter === 'active') return !todo.completed;
    return true;
  });

  return (
    <div>
      <input onKeyPress={(e) => {
        if (e.key === 'Enter') addTodo(e.target.value);
      }} />
      
      <select value={filter} onChange={(e) => setFilter(e.target.value)}>
        <option value="all">Все</option>
        <option value="active">Активные</option>
        <option value="completed">Завершенные</option>
      </select>
      
      {filteredTodos.map(todo => (
        <div key={todo.id} onClick={() => toggleTodo(todo.id)}>
          {todo.text} {todo.completed ? '✅' : '⏳'}
        </div>
      ))}
    </div>
  );
}
```

---

## 🎯 **Задание:**

Создай компонент **Counter** со следующими возможностями:

1. **Отображение счетчика** (начальное значение: 0)
2. **Кнопка "+"** для увеличения на 1
3. **Кнопка "-"** для уменьшения на 1
4. **Кнопка "Сброс"** для возврата к 0
5. **Кнопка "Увеличить на 5"** для увеличения на 5

### **Требования:**
- Используй только useState
- Добавь красивые стили с Tailwind
- Сделай кнопки интерактивными

---

## 🚀 **Начни писать код!**

Создай файл `Counter.tsx` и реализуй функциональность.

**Подсказка:** Начни с `const [count, setCount] = useState(0);`

---

## ✅ **Что ты изучишь:**
- Как объявлять состояние
- Как обновлять состояние
- Как использовать состояние в JSX
- Как создавать интерактивные компоненты
