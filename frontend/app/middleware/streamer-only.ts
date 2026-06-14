import { useAuthStore } from "~/store/auth"

export default defineNuxtRouteMiddleware((to, from) => {
    const auth = useAuthStore();

    if (!auth.hasAnyRole(['ADMIN', 'STREAMER'])) {
        return navigateTo('/');
    }
})