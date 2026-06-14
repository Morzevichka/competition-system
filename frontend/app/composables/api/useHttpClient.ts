export const useHttpClient = () => {
    const config = useRuntimeConfig();

    return $fetch.create({
        baseURL: config.public.apiBase,
        credentials: 'include',
    });
};