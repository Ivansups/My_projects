'use client';

import Link from 'next/link';
import { useState } from 'react';

export default function HooksCourse() {
  const [currentLesson, setCurrentLesson] = useState(1);

  const lessons = [
    {
      id: 1,
      title: 'useState',
      description: 'Управление состоянием',
      difficulty: 'Легко',
      status: 'active'
    },
    {
      id: 2,
      title: 'useEffect',
      description: 'Побочные эффекты',
      difficulty: 'Средне',
      status: 'locked'
    },
    {
      id: 3,
      title: 'useRef',
      description: 'Ссылки на DOM',
      difficulty: 'Средне',
      status: 'locked'
    },
    {
      id: 4,
      title: 'useCallback',
      description: 'Мемоизация функций',
      difficulty: 'Сложно',
      status: 'locked'
    },
    {
      id: 5,
      title: 'useMemo',
      description: 'Мемоизация значений',
      difficulty: 'Сложно',
      status: 'locked'
    }
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-500 via-purple-500 to-pink-500">
      <div className="container mx-auto px-6 py-12">
        {/* Header */}
        <div className="text-center mb-12">
          <h1 className="text-5xl font-bold text-white mb-4">
            🎯 Курс по React хукам
          </h1>
          <p className="text-xl text-white/80 max-w-2xl mx-auto">
            Изучай React хуки на практике! Выполняй задания, сравнивай решения и становись лучше.
          </p>
        </div>

        {/* Уроки */}
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl mx-auto">
          {lessons.map((lesson) => (
            <div
              key={lesson.id}
              className={`bg-white/10 backdrop-blur-sm rounded-2xl border border-white/20 p-6 transition-all duration-300 ${
                lesson.status === 'active' 
                  ? 'hover:bg-white/20 hover:scale-105 cursor-pointer' 
                  : 'opacity-60 cursor-not-allowed'
              }`}
              onClick={() => lesson.status === 'active' && setCurrentLesson(lesson.id)}
            >
              <div className="text-center">
                <div className="text-3xl mb-3">
                  {lesson.status === 'active' ? '🚀' : '🔒'}
                </div>
                <h3 className="text-xl font-bold text-white mb-2">
                  {lesson.title}
                </h3>
                <p className="text-white/80 mb-3">
                  {lesson.description}
                </p>
                <div className="flex justify-between items-center">
                  <span className={`px-3 py-1 rounded-full text-xs font-medium ${
                    lesson.difficulty === 'Легко' ? 'bg-green-500/20 text-green-300' :
                    lesson.difficulty === 'Средне' ? 'bg-yellow-500/20 text-yellow-300' :
                    'bg-red-500/20 text-red-300'
                  }`}>
                    {lesson.difficulty}
                  </span>
                  {lesson.status === 'active' && (
                    <Link 
                      href={`/examples/hooks/0${lesson.id}-${lesson.title.toLowerCase()}/${lesson.title.toLowerCase()}`}
                      className="bg-white/20 hover:bg-white/30 text-white px-4 py-2 rounded-lg text-sm font-medium transition-colors"
                    >
                      Начать
                    </Link>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Навигация */}
        <div className="mt-12 text-center space-y-4">
          <h2 className="text-3xl font-bold text-white mb-6">
            🚀 Начни изучение React хуков
          </h2>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Link 
              href="/examples/hooks/lessons"
              className="bg-gradient-to-r from-pink-500 to-purple-600 text-white px-8 py-4 rounded-full text-lg font-semibold hover:from-pink-600 hover:to-purple-700 transform hover:scale-105 transition-all duration-300"
            >
              📚 Все уроки
            </Link>
            <Link 
              href="/examples/hooks/01-usestate/counter"
              className="bg-gradient-to-r from-blue-500 to-cyan-600 text-white px-8 py-4 rounded-full text-lg font-semibold hover:from-blue-600 hover:to-cyan-700 transform hover:scale-105 transition-all duration-300"
            >
              🎯 Первый урок: useState
            </Link>
          </div>
        </div>

        {/* Инструкции */}
        <div className="mt-16 bg-white/10 backdrop-blur-sm rounded-2xl border border-white/20 p-8 max-w-4xl mx-auto">
          <h3 className="text-2xl font-bold text-white mb-6 text-center">
            📖 Как работать с курсом
          </h3>
          <div className="grid md:grid-cols-2 gap-6">
            <div>
              <h4 className="text-lg font-semibold text-white mb-3">1. Изучай теорию</h4>
              <p className="text-white/80">
                Прочитай README.md файл каждого урока. Там есть теория, примеры и объяснения.
              </p>
            </div>
            <div>
              <h4 className="text-lg font-semibold text-white mb-3">2. Выполняй задание</h4>
              <p className="text-white/80">
                Открой файл с заданием и реализуй функциональность самостоятельно.
              </p>
            </div>
            <div>
              <h4 className="text-lg font-semibold text-white mb-3">3. Сравни с решением</h4>
              <p className="text-white/80">
                После выполнения посмотри на готовое решение в папке solutions.
              </p>
            </div>
            <div>
              <h4 className="text-lg font-semibold text-white mb-3">4. Улучшай код</h4>
              <p className="text-white/80">
                Попробуй улучшить свой код, добавив новые функции или стили.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
