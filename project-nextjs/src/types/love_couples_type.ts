export interface LoveCouples {
    id: string;
    first_user: string;
    second_user: string;
}

export interface LoveCouplesCreate {
    first_user: string;
    second_user: string;
}

export interface LoveCouplesUpdate {
    first_user?: string | null;
    second_user?: string | null;
}