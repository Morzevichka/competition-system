import { useAuthStore } from "~/store/auth"

export default defineNuxtPlugin(async (nuxtApp: any) => {
  const authStore = useAuthStore(nuxtApp.$pinia)

  try {
    const user: any = await $fetch('/api/auth/me', {
      baseURL: useRuntimeConfig().public.apiBase,
      credentials: 'include'
    })

    authStore.setUser(user)
  } catch {}
})