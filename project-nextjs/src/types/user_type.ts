export enum Gender {
    MALE = 'male',
    FEMALE = 'female',
    TIEBANAT = 'tiebanat'
}

export interface User {
    id: string;
    name: string;
    email: string;
    gender: Gender;
    description: string | null;
    is_premium: boolean;
}

export interface UserCreate {
    name: string;
    email: string;
    gender: Gender;
    description?: string | null;
    is_premium?: boolean, default: false;
}

export interface UserUpdate {
    name?: string | null;
    email?: string | null;
    gender?: Gender | null;
    description?: string | null;
    is_premium?: boolean, default: false;
}