'use client';

export default function ErrorPage() {
    return (
        <div className="min-h-screen bg-gradient-to-br from-pink-500 via-purple-600 to-blue-600"    >
            <div className="flex flex-col items-center justify-center h-screen">
                <h1 className="text-2xl font-bold">Error</h1>
                <p className="text-lg">Something went wrong</p>
            </div>
        </div>
    );
}