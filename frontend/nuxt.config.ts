// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  modules: [
    '@nuxt/icon',
    '@nuxtjs/tailwindcss',
    '@pinia/nuxt'
  ],
  css: ['@/assets/css/main.css'],
  runtimeConfig: {
    public: {
      apiBase: "http://localhost:8080"
    }
  },
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true }
})