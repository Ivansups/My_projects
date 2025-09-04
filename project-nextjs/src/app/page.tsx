'use client';

import { useState, useEffect } from 'react';
import Image from 'next/image';
import Link from 'next/link';

export default function Home() {
  const [isVisible, setIsVisible] = useState(false);
  const [currentFeature, setCurrentFeature] = useState(0);

  // Анимация появления элементов при загрузке страницы
  useEffect(() => {
    setIsVisible(true);
  }, []);

  // Автоматическая смена особенностей сервиса
  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentFeature((prev) => (prev + 1) % 3);
    }, 3000);
    return () => clearInterval(interval);
  }, []);

  const features = [
    'Найди свою вторую половинку',
    'Безопасные знакомства онлайн',
    'Умный алгоритм подбора'
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-pink-500 via-purple-600 to-blue-600">
      {/* Header */}
      <header className="relative z-10 px-6 py-4">
        <nav className="flex items-center justify-between max-w-7xl mx-auto">
          <div className="flex items-center space-x-2">
            <div className="w-8 h-8 bg-white rounded-full flex items-center justify-center">
              <span className="text-purple-600 font-bold text-lg">♥</span>
            </div>
            <span className="text-white font-bold text-xl">LoveMatch</span>
          </div>
          
          <div className="hidden md:flex items-center space-x-6">
            <Link href="/about" className="text-white hover:text-pink-200 transition-colors">
              О нас
            </Link>
            <Link href="/how-it-works" className="text-white hover:text-pink-200 transition-colors">
              Как это работает
            </Link>
            <Link href="/login" className="bg-white text-purple-600 px-6 py-2 rounded-full font-semibold hover:bg-pink-100 transition-colors">
              Войти
            </Link>
          </div>
        </nav>
      </header>

      {/* Hero Section */}
      <main className="relative px-6 py-20">
        <div className="max-w-7xl mx-auto text-center">
          {/* Animated feature text */}
          <div className="mb-8 h-16 flex items-center justify-center">
            <h2 className={`text-2xl md:text-4xl text-white font-light transition-all duration-500 ${
              isVisible ? 'opacity-100 transform translate-y-0' : 'opacity-0 transform translate-y-4'
            }`}>
              {features[currentFeature]}
            </h2>
          </div>

          {/* Main heading */}
          <h1 className={`text-5xl md:text-7xl font-bold text-white mb-8 transition-all duration-700 delay-300 ${
            isVisible ? 'opacity-100 transform translate-y-0' : 'opacity-0 transform translate-y-8'
          }`}>
            Найди любовь
            <br />
            <span className="bg-gradient-to-r from-pink-300 to-yellow-300 bg-clip-text text-transparent">
              своей жизни
            </span>
          </h1>

          {/* Description */}
          <p className={`text-xl md:text-2xl text-white/90 mb-12 max-w-3xl mx-auto transition-all duration-700 delay-500 ${
            isVisible ? 'opacity-100 transform translate-y-0' : 'opacity-0 transform translate-y-8'
          }`}>
            Присоединяйся к тысячам счастливых пар, которые нашли друг друга благодаря нашему сервису
          </p>

          {/* CTA Buttons */}
          <div className={`flex flex-col sm:flex-row gap-4 justify-center items-center transition-all duration-700 delay-700 ${
            isVisible ? 'opacity-100 transform translate-y-0' : 'opacity-0 transform translate-y-8'
          }`}>
            <Link 
              href="/register" 
              className="group bg-gradient-to-r from-pink-500 to-purple-600 text-white px-8 py-4 rounded-full font-semibold text-lg hover:from-pink-600 hover:to-purple-700 transform hover:scale-105 transition-all duration-300 shadow-2xl hover:shadow-pink-500/25"
            >
              Начать поиск
              <span className="ml-2 group-hover:translate-x-1 transition-transform duration-300">→</span>
            </Link>
            
            <Link 
              href="/how-it-works" 
              className="bg-white/20 backdrop-blur-sm text-white px-8 py-4 rounded-full font-semibold text-lg hover:bg-white/30 transition-all duration-300 border border-white/30"
            >
              Узнать больше
            </Link>
          </div>
        </div>

        {/* Floating elements for visual appeal */}
        <div className="absolute top-20 left-10 w-20 h-20 bg-pink-400/20 rounded-full blur-xl animate-pulse"></div>
        <div className="absolute top-40 right-20 w-32 h-32 bg-purple-400/20 rounded-full blur-xl animate-pulse delay-1000"></div>
        <div className="absolute bottom-20 left-1/4 w-16 h-16 bg-blue-400/20 rounded-full blur-xl animate-pulse delay-2000"></div>
      </main>

      {/* Features Section */}
      <section className="relative px-6 py-20 bg-white/10 backdrop-blur-sm">
        <div className="max-w-7xl mx-auto">
          <h2 className="text-4xl md:text-5xl font-bold text-white text-center mb-16">
            Почему выбирают нас?
          </h2>
          
          <div className="grid md:grid-cols-3 gap-8">
            {[
              {
                icon: '🔒',
                title: 'Безопасность',
                description: 'Все профили проверяются, ваши данные защищены'
              },
              {
                icon: '🎯',
                title: 'Умный подбор',
                description: 'AI алгоритм находит идеальные совпадения'
              },
              {
                icon: '💝',
                title: 'Настоящие чувства',
                description: 'Фокус на серьезных отношениях, а не на случайных знакомствах'
              }
            ].map((feature, index) => (
              <div 
                key={index}
                className={`text-center p-8 rounded-2xl bg-white/10 backdrop-blur-sm border border-white/20 hover:bg-white/20 transition-all duration-300 transform hover:scale-105 ${
                  isVisible ? 'opacity-100 transform translate-y-0' : 'opacity-0 transform translate-y-8'
                }`}
                style={{ transitionDelay: `${800 + index * 200}ms` }}
              >
                <div className="text-4xl mb-4">{feature.icon}</div>
                <h3 className="text-xl font-semibold text-white mb-3">{feature.title}</h3>
                <p className="text-white/80">{feature.description}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="px-6 py-12 bg-black/20 backdrop-blur-sm">
        <div className="max-w-7xl mx-auto text-center">
          <div className="flex items-center justify-center space-x-2 mb-6">
            <div className="w-6 h-6 bg-white rounded-full flex items-center justify-center">
              <span className="text-purple-600 font-bold text-sm">♥</span>
            </div>
            <span className="text-white font-bold text-lg">LoveMatch</span>
          </div>
          <p className="text-white/60 mb-4">
            © 2024 LoveMatch. Все права защищены.
          </p>
          <div className="flex justify-center space-x-6 text-sm">
            <Link href="/privacy" className="text-white/60 hover:text-white transition-colors">
              Конфиденциальность
            </Link>
            <Link href="/terms" className="text-white/60 hover:text-white transition-colors">
              Условия использования
            </Link>
            <Link href="/support" className="text-white/60 hover:text-white transition-colors">
              Поддержка
            </Link>
          </div>
        </div>
      </footer>
    </div>
  );
}
