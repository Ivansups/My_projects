'use client'

import { useSession } from "next-auth/react";
import { z } from "zod";
import { useState } from "react";

export default function TestPage() {

    const initialFormState = {
        name: "",
        email: "",
        password: "",
    }

    const [formData, setFormData] = useState(initialFormState);

    const fordDataSchema = z.object({
        name: z.string().min(3),
        email: z.string().email(),
        password: z.string().min(6),
    })

    const validate = () => {
        const result = fordDataSchema.safeParse(formData);
        if (!result.success) {
            return undefined
        }
        return null;
    }

    const handleSubmitForm = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const error = validate();
        if (error) {
            console.log(error);
        }
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    }

    return (
        <div>
            <form onSubmit={handleSubmitForm}>
                <input type="text" name="name" value={formData.name} onChange={handleChange} />
                <input type="email" name="email" value={formData.email} onChange={handleChange} />
                <input type="password" name="password" value={formData.password} onChange={handleChange} />
                <button type="submit">Submit</button>
            </form>
        </div>
    )
}