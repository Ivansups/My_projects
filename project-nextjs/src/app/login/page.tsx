'use client';

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import {useSession} from "next-auth/react";
import {signIn} from "next-auth/react";

export default function LoginPage() {
    const router = useRouter();
    // User name and password
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    // Error and success messages
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    // Success message
    const [success, setSuccess] = useState(false);

    const { data: session, status } = useSession();

    useEffect(() => {
        if (status === "authenticated") {
            router.push("/profile");
        }
    }, [status, router]);

    const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
        if (!email || !password) {
            setError("Заполните все поля");
            return;
        }

        if (!email.includes("@")) {
            setError("Введите корректный email");
            return;
        }

        if (password.length < 6) {
            setError("Пароль должен содержать минимум 6 символов");
            return;
        }

        e.preventDefault();

        setLoading(true);
        setError("");
        setSuccess(false);
    try {
        const res = await signIn("credentials", {
            email: email,
            password: password,
            redirect: false,
        });
        if (res?.error) {
            setError("Неверный email или пароль");
        } else {
            setSuccess(true);
            router.push("/profile");
        }
    } catch (error) {
        setError("Что-то пошло не так");
    } finally {
        setLoading(false);
    }
}



    return (
    <div className="min-h-screen bg-gradient-to-br from-pink-500 via-purple-600 to-blue-600">
        <div className="flex flex-col items-center justify-center h-screen">
            <div className="text-center">
                <h1 className="text-2xl font-bold">Вход в аккаунт</h1>
            </div>
            <div className="w-full max-w-md">
                <form onSubmit={handleLogin} className="mb-4 bg-white p-4 rounded-lg">
                    <label className="bg-white block text-sm font-medium text-gray-700">Email</label>
                    <input 
                    type="email" 
                    className="w-full p-2 rounded-md rounded-full border-2 border-gray-300"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    />
                    <label className="bg-white block text-sm font-medium text-gray-700">Password</label>
                    <input 
                    type="password" 
                    className="w-full p-2 rounded-md rounded-full border-2 border-gray-300"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    />
                    
                    {/* Отображение ошибок и сообщений */}
                    {error && (
                        <div className="text-red-500 text-sm mt-2 p-2 bg-red-50 rounded">
                            {error}
                        </div>
                    )}

                    {success && (
                        <div className="text-green-500 text-sm mt-2 p-2 bg-green-50 rounded">
                            Успешный вход! Перенаправляем...
                        </div>
                    )}
                    
                    <div className="flex justify-center gap-2 mt-4">
                        <button 
                            type="submit" 
                            disabled={loading}
                            className={`text-white rounded-full text-center p-2 transition ${
                                loading 
                                    ? 'bg-gray-400 cursor-not-allowed' 
                                    : 'bg-purple-500 hover:bg-purple-600'
                            }`}
                        >
                            {loading ? 'Загрузка...' : 'Войти'}
                        </button>
                        <Link href="/register" className="bg-blue-500 text-white rounded-full text-center p-2 hover:bg-blue-600 transition">
                            Регистрация
                        </Link>
                    </div>
                </form>
            </div>
        </div>
    </div>
  )
}