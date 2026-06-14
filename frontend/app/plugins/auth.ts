import { useAuthStore } from "~/store/auth"

export default defineNuxtPlugin(async (nuxtApp) => {
  const authStore = useAuthStore();

  const event = useRequestEvent();

  try {
    const user: any = await $fetch('/api/users/me', {
      baseURL: useRuntimeConfig().public.apiBase,
      credentials: 'include',
      headers: event?.node.req.headers
    });

    authStore.setUser(user);
  } catch {
    authStore.clearAuth();
  }
})