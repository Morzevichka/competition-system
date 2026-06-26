export const useHttpClient = () => {
    const config = useRuntimeConfig();

    const event = useRequestEvent();

    return $fetch.create({
        baseURL: config.public.apiBase,
        credentials: 'include',
        headers: event?.node.req.headers as HeadersInit
    });
};