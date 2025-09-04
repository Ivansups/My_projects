'use client';

import { useSession } from "next-auth/react";
export default function ProfilePage() {

    const { data: session, status } = useSession();
    const user = session?.user;

    if (status === "loading") {
        return <div className="min-h-screen bg-gradient-to-br from-pink-500 via-purple-600 to-blue-600">
            <div className="flex flex-col items-center justify-center h-screen">
                <h1 className="text-2xl font-bold">Loading...</h1>
            </div>
        </div>;
    }

    if (status === "unauthenticated") {
        return <div className="min-h-screen bg-gradient-to-br from-pink-500 via-purple-600 to-blue-600">
            <div className="flex flex-col items-center justify-center h-screen">
                <h1 className="text-2xl font-bold">Unauthenticated</h1>
            </div>
        </div>;
    }


    return (
        <div className="min-h-screen bg-gradient-to-br from-pink-500 via-purple-600 to-blue-600">
            <div className="flex flex-col items-center justify-center h-screen">
                <h1 className="text-2xl font-bold">Profile</h1>
                <p className="text-lg">Email: {user?.email}</p>
                <p className="text-lg">Name: {user?.name}</p>
            </div>
        </div>
    );
}