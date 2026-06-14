import { useAuthStore } from "~/store/auth"

export default defineNuxtRouteMiddleware((to) => {
    const auth = useAuthStore();

    if (!auth.isLoggedIn) {
        return navigateTo('/login');
    }
})