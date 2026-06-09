let apiInstance: ReturnType<typeof $fetch.create> | null = null;

export const useHttpClient = () => {
    if (apiInstance) {
        return apiInstance;
    }

    const config = useRuntimeConfig();

    apiInstance = $fetch.create({
        baseURL: config.public.apiBase,
        credentials: "include",
    });

    return apiInstance;
};