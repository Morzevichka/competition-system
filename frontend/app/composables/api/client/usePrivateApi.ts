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

                await refreshPromise;
            } catch (refreshError) {
                refreshPromise = null;
                useAuthStore().clearAuth();
                navigateTo('/login');
                throw refreshError;
            }
            return await client<T>(url, options);
        }
    };

    const get = <T>(url: string, options?: {}) => request<T>(url, { method: 'GET', ...options });
    const post = <T>(url: string, body: any, options?: {}) => request<T>(url, { method: 'POST', body, ...options });
    const put = <T>(url: string, body: any, options?: {}) => request<T>(url, { method: 'PUT', body, ...options });
    const patch = <T>(url: string, body: any, options?: {}) => request<T>(url, { method: 'PATCH', body, ...options });
    const del = <T>(url: string, options?: {}) => request<T>(url, { method: 'DELETE', ...options});

    return { get, post, put, patch, delete: del };
};