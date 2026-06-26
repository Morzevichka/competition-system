import type { Login, Register } from "~/types/auth";
import { useHttpClient } from "./useHttpClient";
import type { User } from "~/types/user";

export const useAuthApi = () => {
    const client = useHttpClient();

    return {
        login: (body: Login) =>
            client<User>('/api/auth/login', {
                method: 'POST',
                body,
            }),

        register: (body: Register) =>
            client<User>('/api/auth/register', {
                method: 'POST',
                body,
            }),

        refresh: () =>
            client('/api/auth/refresh', {
                method: 'POST',
            }),

        logout: () =>
            client('/api/auth/logout', {
                method: 'POST',
            }),
    };
};