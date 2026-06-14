import { useHttpClient } from "./useHttpClient"

export const usePublicApi = () => {
    const client = useHttpClient();

    return {
        get: (url: string) => client(url),
        post: (url: string, body?: any) => client(url, { method: 'POST', body: body }),
    }
}