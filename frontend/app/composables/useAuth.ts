import { useAuthStore } from "~/store/auth";
import { useAuthApi } from "./api/client/useAuthApi";
import type { User } from '~/types/user';
import type { Login, Register } from "~/types/auth";

export const useAuth = () => {
    const authStore = useAuthStore();
    const authApi = useAuthApi();

    const login = async (data: Login) => {
        const result: User  = await authApi.login(data);
        authStore.setUser(result);
        return result;
    };

    const register = async (data: Register) => {
        const result: User = await authApi.register(data);
        authStore.setUser(result);
        return result;
    };

    const refresh = async () => {
        try {
            await authApi.refresh();
        } catch (error) {
            authStore.clearAuth();
            throw error;
        }
    }

    const logout = async () => {
        try {
            await authApi.logout();
            
        } finally {
            authStore.clearAuth();
            await navigateTo('/login', { replace: true })
        }
    };

    return { login, register, refresh, logout };
};