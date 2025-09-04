'use client';

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { useSession } from "next-auth/react";
import Link from "next/link";

export default function RegisterPage() {
    const router = useRouter();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState(false);
    const [loading, setLoading] = useState(false);

    const { data: session, status } = useSession();

    useEffect(() => {
        if (status === "authenticated") {
            router.push("/");
        }
    }, [status, router]);


    const handleRegister = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        
        // Валидация
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

        setLoading(true);
        setError("");
        setSuccess(false);
        
        try {
            const res = await fetch("/api/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email: email,
                    password: password,
                }),
            });
            const data = await res.json();
            if (data?.error) {
                setError("Ошибка регистрации. Возможно, пользователь уже существует");
            } else {
                setSuccess(true);
                // Перенаправляем на страницу входа после успешной регистрации
                setTimeout(() => {
                    router.push("/login");
                }, 2000);
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
                <div className="text-center mb-6">
                    <h1 className="text-2xl font-bold text-white">Регистрация</h1>
                </div>
                <form onSubmit={handleRegister} className="flex flex-col gap-4 w-80 bg-white/80 p-8 rounded-xl shadow">
                    <label className="block text-sm font-medium text-gray-700">Email</label>
                    <input
                        placeholder="Введите email"
                        className="bg-white p-2 rounded border border-gray-300"
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <label className="block text-sm font-medium text-gray-700">Пароль</label>
                    <input
                        className="bg-white p-2 rounded border border-gray-300"
                        type="password"
                        value={password}
                        placeholder="Введите пароль"
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
                            Регистрация успешна! Перенаправляем на страницу входа...
                        </div>
                    )}
                    
                    <button
                        type="submit"
                        disabled={loading}
                        className={`text-white rounded-full text-center p-2 transition ${
                            loading
                                ? 'bg-gray-400 cursor-not-allowed' 
                                : 'bg-purple-600 hover:bg-purple-700'
                        }`}
                    >
                        {loading ? 'Регистрация...' : 'Зарегистрироваться'}
                    </button>
                    
                    <div className="text-center mt-4">
                        <Link href="/login" className="text-blue-600 hover:text-blue-800 text-sm">
                            Уже есть аккаунт? Войти
                        </Link>
                    </div>
                </form>
            </div>
        </div>
    );
}