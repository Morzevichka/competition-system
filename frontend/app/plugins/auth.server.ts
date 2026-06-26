import { appendResponseHeader } from 'h3';
import { useAuthStore } from "~/store/auth";
import type { User } from "~/types/user";

export default defineNuxtPlugin(async () => {
  const authStore = useAuthStore();

  const event = useRequestEvent();

  if (!event) {
    return;
  }

  const config = useRuntimeConfig();

  try {
    const user: User = await $fetch('/api/users/me', {
      baseURL: config.public.apiBase,
      headers: event.node.req.headers as HeadersInit
    });

    authStore.setUser(user);
    return ;
  } catch (error: any) {

    if (error?.response?.status !== 401) {
      authStore.clearAuth();
      return ;
    }

    try {
      const refreshResponse = await $fetch.raw('/api/auth/refresh', {
        method: 'POST',
        baseURL: config.public.apiBase,
        headers: event.node.req.headers as HeadersInit
      });

      const setCookies = refreshResponse.headers.getSetCookie();

      for (const cookie of setCookies) {
        appendResponseHeader(event, 'set-cookie', cookie);
      }

      const cookieHeader = setCookies
        .map(cookie => cookie.split(';')[0])
        .join('; ');

      const user: User = await $fetch('/api/users/me', {
        baseURL: config.public.apiBase,
        headers: {
          cookie: cookieHeader
        }
      });

      authStore.setUser(user);
    } catch {
      authStore.clearAuth();
      navigateTo("/login");
    }
  }
})