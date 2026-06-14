<script setup>
import { ref } from "vue";
import { useAuth } from "@/composables/useAuth";

definePageMeta({
    middleware: ['not-auth']
})

const auth = useAuth();

const form = reactive({
  email: '',
  password: ''
});

const loading = ref(false);
const error = ref(null);

const showPassword = ref(false);

const handleLogin = async () => {
    loading.value = true
    error.value = null

    try {
        const payload = {
            email: form.email,
            password: form.password
        };

        await auth.login(payload);

        await navigateTo("/");
    } catch (e) {
        error.value = e.data?.message || "Something went wrong";
    } finally {
        loading.value = false;
    }
};
</script>

<template>
    <div class="w-full flex justify-center items-center">
        <div class="w-full max-w-[440px]">
            <div class="relative overflow-hidden rounded-xl border border-outline-variant/40 shadow-xlw">
                <div class="absolute top-0 left-0 right-0 h-1 bg-primary-container" />

                <div class="p-6 md:p-8 space-y-4">

                    <div class="space-y-2">
                        <h2 class="text-lg font-medium text-primary">
                            С возвращением!
                        </h2>

                        <p class="text-sm text-on-surface-variant">
                            Введите данные для входа в систему.
                        </p>
                    </div>

                    <div v-if="error" class="text-sm text-error">
                        {{ error }}
                    </div>

                    <form class="space-y-3" @submit.prevent="handleLogin">

                        <div class="space-y-2">
                            <label class="text-sm text-on-surface-variant">
                                Почта
                            </label>

                            <div class="relative group">
                                <Icon name="material-symbols:mail"
                                    class="absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant group-focus-within:text-primary-container transition-colors" />

                                <input 
                                    v-model="form.email" 
                                    id="email" 
                                    name="email" 
                                    type="email" 
                                    placeholder="name@mail.com" 
                                    class="w-full rounded-lg bg-surface-container border border-outline-variant/40
                                        text-on-surface placeholder:text-on-surface-variant/50
                                        py-3 pl-10 pr-4
                                        focus:outline-none focus:ring-1 focus:ring-primary-container focus:border-primary-container
                                        transition-all" 
                                />
                            </div>
                        </div>

                        <div class="space-y-2">
                            <div class="flex justify-between items-center">
                                <label class="text-sm text-on-surface-variant">
                                    Пароль
                                </label>

                                <NuxtLink to="#"
                                    class="text-sm text-primary hover:text-primary-container transition-colors">
                                    Забыли пароль?
                                </NuxtLink>
                            </div>

                            <div class="relative group">
                                <Icon name="material-symbols:lock"
                                    class="absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant group-focus-within:text-primary-container transition-colors" />

                                <input v-model="form.password" :type="showPassword ? 'text' : 'password'"
                                    placeholder="••••••••" class="w-full rounded-lg bg-surface-container border border-outline-variant/40
                                    text-on-surface placeholder:text-on-surface-variant/50
                                    py-3 pl-10 pr-10
                                    focus:outline-none focus:ring-1 focus:ring-primary-container focus:border-primary-container
                                    transition-all" />
                                <button type="button" @click="showPassword = !showPassword"
                                    class="absolute right-3 top-1/2 -translate-y-1/2 flex items-center text-on-surface-variant hover:text-primary-container transition-colors">
                                    <Icon :name="showPassword ? 'material-symbols:visibility-off' : 'material-symbols:visibility'" />
                                </button>
                            </div>
                        </div>

                        <button type="submit" :disabled="loading" class="w-full h-11 rounded-lg
                            bg-primary-container text-on-primary-container font-medium
                            hover:bg-primary hover:text-on-primary transition-colors
                            disabled:opacity-60 disabled:cursor-not-allowed
                            flex items-center justify-center gap-2">
                            <span v-if="!loading">
                                Войти
                            </span>

                            <span v-else class="flex items-center gap-2">
                                <svg class="w-5 h-5 animate-spin" viewBox="0 0 24 24">
                                    <circle cx="12" cy="12" r="10" fill="none" stroke="currentColor" stroke-width="4"
                                        opacity="0.25" />
                                    <path d="M4 12a8 8 0 018-8" fill="none" stroke="currentColor" stroke-width="4" />
                                </svg>
                                Вход
                            </span>
                        </button>

                    </form>
                </div>
            </div>
            <p class="mt-6 text-center text-sm text-on-surface-variant">
                Нет аккаунта?
                <NuxtLink to="/register" class="text-primary hover:text-primary-container transition-colors">
                    Регистрация
                </NuxtLink>
            </p>
        </div>
    </div>
</template>

<style scoped>
input:-webkit-autofill,
input:-webkit-autofill:hover,
input:-webkit-autofill:focus,
input:-webkit-autofill:active {
    -webkit-text-fill-color: #b9cacb;
    -webkit-box-shadow: 0 0 0 1000px #1f1e2b inset;
    box-shadow: 0 0 0 1000px #1f1e2b inset;
    transition: background-color 9999s ease-in-out 0s;
}
</style>