import { useHttpClient } from "./useHttpClient"

export const usePublicApi = () => {
    const client = useHttpClient();

    return {
        get: <T>(url: string, options?: {}) => client<T>(url, { ...options }),
        post: <T>(url: string, body?: any, options?: {}) => client<T>(url, { method: 'POST', body: body, ...options })
    }
}