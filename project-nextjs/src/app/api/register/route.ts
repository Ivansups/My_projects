import { NextRequest, NextResponse } from "next/server"
import { PrismaClient } from "../../../generated/prisma"
import bcrypt from "bcryptjs"

const prisma = new PrismaClient()

export async function POST(request: NextRequest) {
    try {
        const { email, password } = await request.json()

        // Валидация
        if (!email || !password) {
            return NextResponse.json(
                { error: "Email и пароль обязательны" },
                { status: 400 }
            )
        }

        if (password.length < 6) {
            return NextResponse.json(
                { error: "Пароль должен содержать минимум 6 символов" },
                { status: 400 }
            )
        }

        // Проверяем, существует ли пользователь
        const existingUser = await prisma.user.findUnique({
            where: { email }
        })

        if (existingUser) {
            return NextResponse.json(
                { error: "Пользователь с таким email уже существует" },
                { status: 400 }
            )
        }

        // Хешируем пароль
        const hashedPassword = await bcrypt.hash(password, 12)

        // Создаем пользователя
        const user = await prisma.user.create({
            data: {
                email,
                password: hashedPassword,
                name: email.split('@')[0], // Используем часть email как имя
            }
        })

        // Убираем пароль из ответа
        const { password: _, ...userWithoutPassword } = user

        return NextResponse.json(
            { message: "Пользователь успешно создан", user: userWithoutPassword },
            { status: 201 }
        )

    } catch (error) {
        console.error("Ошибка регистрации:", error)
        return NextResponse.json(
            { error: "Внутренняя ошибка сервера" },
            { status: 500 }
        )
    }
}
