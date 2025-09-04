import { PrismaClient } from "@prisma/client"
import { NextRequest, NextResponse } from "next/server"
import { validateUserData } from "@/utils/validation"

const prisma = new PrismaClient()
// Получаем всех пользователей
export async function GET(request: NextRequest) {
    try {
        const users = await prisma.user.findMany()
        return NextResponse.json(users)
    } catch (error) {
        return NextResponse.json({ message: "Error fetching users" }, { status: 500 })
    }
}


// Создаем пользователя
export async function POST(request: NextRequest) {
    try {
        const {name, email, gender, description, is_premium} = await request.json()
        const {isValid, errors} = validateUserData({name, email, gender, description, is_premium}) // Проверяем данные на валидность
        if (!isValid) {
            return NextResponse.json({ message: "Invalid data", errors }, { status: 400 })
        }
        const user = await prisma.user.create({
            data: {name, email, gender, description, is_premium}
        })
        return NextResponse.json(user)
    } catch (error) {
        return NextResponse.json({ message: "Error creating user" }, { status: 500 })
    }
}

// Обновляем пользователя
export async function PUT(request: NextRequest) {
    try {
        const {id, name, email, gender, description, is_premium} = await request.json()
        const {isValid, errors} = validateUserData({name, email, gender, description, is_premium}) // Проверяем данные на валидность
        if (!isValid) {
            return NextResponse.json({ message: "Invalid data", errors }, { status: 400 })
        }
        const user = await prisma.user.update({
            where: {id},
            data: {name, email, gender, description, is_premium}
        })
        return NextResponse.json(user)
    } catch (error) {
        return NextResponse.json({ message: "Error updating user" }, { status: 500 })
    }
}

// Удаляем пользователя
export async function DELETE(request: NextRequest) {
    try {
        const {id} = await request.json()
        const user = await prisma.user.delete({
            where: {id}
        })
        return NextResponse.json(user)
    } catch (error) {
        return NextResponse.json({ message: "Error deleting user" }, { status: 500 })
    }
}


// Получаем пользователя по id
export async function GET_BY_ID(request: NextRequest) { // используем GetByID для избежания конфликта с методом GET
    try {
        const {id} = await request.json()
        const user = await prisma.user.findUnique({
            where: {id}
        })
        return NextResponse.json(user)
    } catch (error) {
        return NextResponse.json({ message: "Error getting user by id" }, { status: 500 })
    }
}