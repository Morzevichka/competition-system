import { useAuthStore } from "~/store/auth";
import { useAuthApi } from "./useAuthApi";

export const useAuth = () => {
    const authStore = useAuthStore();
    const authApi = useAuthApi();

    const login = async (email: string, password: string) => {
        try {
            const data: any = await authApi.login({ email, password });
            authStore.setUser(data.user || data)
        } catch (error: any) {
            return { success: false, error: error.data?.message || 'Ошибка входа' }
        }
    };

    const register = async (
        first_name: string,
        last_name: string,
        login: string,
        email: string,
        password: string,
        repeat_password: string
    ) => {
        try {
            const data: any = await authApi.register({
                first_name,
                last_name,
                login,
                email,
                password,
                repeat_password
            });

            authStore.setUser(data.user || data);
        } catch (error: any) {
            return { success: false, error: error.data?.message || 'Ошибка регистрации' }
        }
    };

    const fetchUser = async () => {
        try {
            const data: any = await authApi.me()
            authStore.setUser(data)
            return data
        } catch (error) {
            authStore.clearAuth()
            throw error
        }
    };

    const refresh = async () => {
        try {
            const data = await authApi.refresh()
            return data
        } catch (error) {
            authStore.clearAuth()
            throw error
        }
    }

    const logout = async () => {
        try {
            await authApi.logout()
        } finally {
            authStore.clearAuth()
        }
    };

    return { login, register, fetchUser, logout };
};