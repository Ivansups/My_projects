// // 🎯 ЗАДАНИЕ: Создай компонент Counter с useState
// // 
// // Твоя задача: реализовать функциональность счетчика
// // 
// // 1. Добавь useState для счетчика
// // 2. Создай функции для изменения счетчика
// // 3. Стилизуй компонент с Tailwind
// // 
// // Начни писать код здесь:

// import { useState } from 'react';

// export default function Counter() {
//   // TODO: Добавь useState для счетчика
//   // const [count, setCount] = useState(0);

//   // TODO: Создай функции для изменения счетчика
//   // const increment = () => { ... }
//   // const decrement = () => { ... }
//   // const reset = () => { ... }
//   // const incrementBy5 = () => { ... }

//   const [count, setCount] = useState(0)
//   const increment = () => {
//     setCount(count + 1)
//   }
//   const decrement = () => {
//     setCount(count - 1)
//   }
//   const reset = () => {
//     setCount(0)
//   }
//   const incrementBy5 = () => {
//     setCount(count + 5)
//   }

//   return (
//     <div className="min-h-screen bg-gradient-to-br from-blue-500 to-purple-600 flex items-center justify-center p-4">
//       <div className="bg-white/10 backdrop-blur-sm p-8 rounded-2xl border border-white/20 text-center">
//         <h1 className="text-3xl font-bold text-white mb-8">Счетчик</h1>
        
//         {/* TODO: Отобрази текущее значение счетчика */}
//         <div className="text-6xl font-bold text-white mb-8">
//           {count}
//         </div>
        
//         {/* TODO: Создай кнопки для управления счетчиком */}
//         <div className="grid grid-cols-2 gap-4">
//           {/* Кнопка "+" */}
//           <button onClick={increment} className="bg-green-500 hover:bg-green-600 text-white font-semibold py-3 px-6 rounded-lg transition-colors">
//             +
//           </button>
          
//           {/* Кнопка "-" */}
//           <button onClick={decrement} className="bg-red-500 hover:bg-red-600 text-white font-semibold py-3 px-6 rounded-lg transition-colors">
//             -
//           </button>
          
//           {/* Кнопка "Сброс" */}
//           <button onClick={reset} className="bg-gray-500 hover:bg-gray-600 text-white font-semibold py-3 px-6 rounded-lg transition-colors">
//             Сброс
//           </button>
          
//           {/* Кнопка "+5" */}
//           <button onClick={incrementBy5} className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-3 px-6 rounded-lg transition-colors">
//             +5
//           </button>
//         </div>
//       </div>
//     </div>
//   );
// }
