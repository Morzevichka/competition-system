import { useAuthStore } from "~/store/auth";
import { useHttpClient } from "./useHttpClient";

let refreshPromise: Promise<any> | null = null;

export const useApiClient = () => {
    const httpClient = useHttpClient();

    const request = async (url: string, options: any = {}) => {
        try {
            return await httpClient(url, options);
        } catch (error: any) {
            const status = error?.status || error?.response?.status;

            if (status !== 401) {
                throw error;
            }

            try {
                if (!refreshPromise) {
                    refreshPromise = httpClient("/api/auth/refresh", { 
                        method: 'POST',
                    }).finally(() => {
                        refreshPromise = null;
                    });
                }

                return await httpClient(url, options);
            } catch (refreshError) {
                refreshPromise = null;

                const authStore = useAuthStore();
                authStore.clearAuth();

                throw refreshError;
            }
        }
    };

    return { request };
};