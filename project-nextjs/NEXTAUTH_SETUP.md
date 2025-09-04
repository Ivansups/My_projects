# Настройка NextAuth

## 1. Создайте файл .env.local в корне проекта:

```bash
# NextAuth
NEXTAUTH_URL=http://localhost:3000
NEXTAUTH_SECRET=your-secret-key-here-change-in-production

# Database (используем Docker)
DATABASE_URL="postgresql://postgres:password@localhost:5432/lovematch"

# OAuth providers (добавим позже)
# GOOGLE_CLIENT_ID=
# GOOGLE_CLIENT_SECRET=
# GITHUB_ID=
# GITHUB_SECRET=
```

## 2. Запустите PostgreSQL через Docker:

```bash
# Запуск базы данных
docker-compose up -d

# Проверка статуса
docker-compose ps
```

## 3. Обновите базу данных:

```bash
npx prisma db push
```

## 4. Сгенерируйте Prisma клиент:

```bash
npx prisma generate
```

## 5. Запустите проект:

```bash
npm run dev
```

## Что уже настроено:

✅ NextAuth конфигурация
✅ Prisma схема с моделями NextAuth
✅ API роуты для авторизации
✅ Типы TypeScript
✅ Провайдер в layout
✅ Docker-compose для PostgreSQL

## Решение проблем:

### Если база данных недоступна:
1. Убедитесь, что Docker запущен
2. Запустите: `docker-compose up -d`
3. Проверьте: `docker-compose ps`

### Если порт занят:
Измените порт в docker-compose.yml:
```yaml
ports:
  - "5433:5432"  # Внешний порт 5433
```
И обновите DATABASE_URL: `postgresql://postgres:password@localhost:5433/lovematch`

## Следующие шаги:

1. Добавить провайдеры авторизации (Google, GitHub, etc.)
2. Создать страницы входа и регистрации
3. Настроить middleware для защищенных роутов
4. Добавить аутентификацию в компоненты
