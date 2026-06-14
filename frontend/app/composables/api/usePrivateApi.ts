import { useAuthStore } from '~/store/auth';
import { useHttpClient } from './useHttpClient';
import { useAuth } from '~/composables/useAuth';

let refreshPromise: Promise<any> | null = null;

export const usePrivateApi = () => {
    const client = useHttpClient();
    const auth = useAuth();

    const request = async <T>(
        url: string,
        options: any = {}
    ): Promise<T> => {
        try {
            return await client<T>(url, options);
        } catch (error: any) {
            const status = error?.status || error?.response?.status;

            if (status !== 401) {
                throw error;
            }

            try {
                if (!refreshPromise) {
                    refreshPromise = auth.refresh().finally(() => {
                        refreshPromise = null
                    });
                }

                return await client<T>(url, options);
            } catch (refreshError) {
                refreshPromise = null;

                useAuthStore().clearAuth();

                throw refreshError;
            }
        }
    };

    return { request };
};