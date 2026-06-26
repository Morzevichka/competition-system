<script setup lang="ts">
import { Dialog, DialogOverlay, DialogPanel, DialogTitle } from '@headlessui/vue';
import { useModal } from '~/composables/useModal';
import { useErrorHandler } from '~/composables/useErrorHandler';
import { useUserApi } from '~/composables/api/useUserApi';
import type { CreateUserRequest } from '~/types/user';
import { useMetaApi } from '~/composables/api/useMetaApi';

const emit = defineEmits<{
    refresh: []
}>();

const userApi = useUserApi();
const metaApi = useMetaApi();
const errorHandler = useErrorHandler();
const modal = useModal();

const loading = ref<boolean>(false);
const error = ref<string>('');
const showPassword = ref<boolean>(false);

const roles = ref<string[]>([]);

const getEmptyForm = (): CreateUserRequest => ({
    email: '',
    login: '',
    first_name: '',
    last_name: '',
    role: '',
    password: ''
});

const form = reactive<CreateUserRequest>(getEmptyForm());

const fetchRoles = async () => {
    try {
        roles.value = (await metaApi.roles()).items;
    } catch (e: any) {
        error.value = errorHandler.handle(e);
    }
}

const handleCreateUser = async () => {
    loading.value = true;

    try {
        await userApi.post({ ...form });
        emit('refresh');
        modal.close();
        Object.assign(form, getEmptyForm());
    } catch (e: any) {
        error.value = errorHandler.handle(e);
    } finally {
        loading.value = false;
    }
};

onMounted(() => {
    fetchRoles();
})
</script>

<template>
    <Dialog :open="modal.isOpen.value" @close="modal.close()" class="relative z-50 text-on-surface-variant">
        <DialogOverlay class="inset-0 fixed bg-black/50" />

        <div class="fixed inset-0 flex items-center justify-center">
            <DialogPanel
                class="relative w-full max-w-[440px] rounded-xl border border-outline-variant/40 bg-surface p-4 sm:p-8 shadow-xl space-y-4">
                <button class="absolute top-2 right-2 text-on-surface-variant hover:text-primary"
                    @click="modal.close()">
                    <Icon name="basil:cross-outline" class="w-7 h-7" />
                </button>

                <DialogTitle class="text-lg font-semibold text-primary">
                    Создать Пользователя
                </DialogTitle>

                <div v-if="error" class="text-sm text-error">
                    {{ error }}
                </div>

                <form @submit.prevent="handleCreateUser" class="space-y-3">

                    <div class="space-y-2">
                        <label class="text-sm text-on-surface-variant">
                            Email
                        </label>

                        <div class="relative group">
                            <Icon name="material-symbols:mail"
                                class="absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant group-focus-within:text-primary-container transition-colors" />

                            <input v-model="form.email" id="email" name="email" type="text"
                                placeholder="example@example.com" class="w-full rounded-lg bg-surface-container border border-outline-variant/40
                                        text-on-surface placeholder:text-on-surface-variant/50
                                        py-3 pl-10 pr-4
                                        focus:outline-none focus:ring-1 focus:ring-primary-container focus:border-primary-container
                                        transition-all" />
                        </div>
                    </div>

                    <div class="space-y-2">
                        <label class="text-sm text-on-surface-variant">
                            Логин
                        </label>

                        <div class="relative group">
                            <Icon name="material-symbols:person"
                                class="absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant group-focus-within:text-primary-container transition-colors" />

                            <input v-model="form.login" id="login" name="login" type="text" placeholder="Login" class="w-full rounded-lg bg-surface-container border border-outline-variant/40
                                        text-on-surface placeholder:text-on-surface-variant/50
                                        py-3 pl-10 pr-4
                                        focus:outline-none focus:ring-1 focus:ring-primary-container focus:border-primary-container
                                        transition-all" />
                        </div>
                    </div>

                    <div class="space-y-2">
                        <label class="text-sm text-on-surface-variant">
                            Фамилия
                        </label>

                        <div class="relative group">
                            <icon name="material-symbols:person"
                                class="absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant group-focus-within:text-primary-container transition-colors" />

                            <input v-model="form.last_name" id="lname" name="lname" type="text" placeholder="Иванов"
                                class="w-full rounded-lg bg-surface-container border border-outline-variant/40
                                        text-on-surface placeholder:text-on-surface-variant/50
                                        py-3 pl-10 pr-4
                                        focus:outline-none focus:ring-1 focus:ring-primary-container focus:border-primary-container
                                        transition-all" />
                        </div>
                    </div>

                    <div class="space-y-2">
                        <label class="text-sm text-on-surface-variant">
                            Имя
                        </label>

                        <div class="relative group">
                            <icon name="material-symbols:person"
                                class="absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant group-focus-within:text-primary-container transition-colors" />

                            <input v-model="form.first_name" id="fname" name="fname" type="text" placeholder="Иван"
                                class="w-full rounded-lg bg-surface-container border border-outline-variant/40
                                        text-on-surface placeholder:text-on-surface-variant/50
                                        py-3 pl-10 pr-4
                                        focus:outline-none focus:ring-1 focus:ring-primary-container focus:border-primary-container
                                        transition-all" />
                        </div>
                    </div>

                    <div class="space-y-2">
                        <label class="text-sm text-on-surface-variant">
                            Роль
                        </label>

                        <div class="relative group">
                            <DropoutMenu v-model="form.role" :items="roles" placeholder="Выберите роль" />
                        </div>
                    </div>

                    <div class="space-y-2">
                        <label class="text-sm text-on-surface-variant">
                            Пароль
                        </label>

                        <div class="relative group">
                            <Icon name="material-symbols:lock"
                                class="absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant group-focus-within:text-primary-container transition-colors" />

                            <input v-model="form.password" id="password" name="password" :type="showPassword ? 'text' : 'password'"
                                placeholder="••••••••" class="w-full rounded-lg bg-surface-container border border-outline-variant/40
                                        text-on-surface placeholder:text-on-surface-variant/50
                                        py-3 pl-10 pr-4
                                        focus:outline-none focus:ring-1 focus:ring-primary-container focus:border-primary-container
                                        transition-all" />

                            <button type="button" @click="showPassword = !showPassword"
                                class="absolute right-3 top-1/2 -translate-y-1/2 flex items-center text-on-surface-variant hover:text-primary-container">
                                <Icon :name="showPassword
                                    ? 'material-symbols:visibility-off'
                                    : 'material-symbols:visibility'" />
                            </button>
                        </div>


                    </div>

                    <button type="submit" :disabled="loading" class="w-full py-3 rounded-lg
                            bg-primary-container text-on-primary font-medium
                            hover:bg-primary-hover-container hover:text-on-primary transition-colors
                            disabled:opacity-60 disabled:cursor-not-allowed
                            flex items-center justify-center gap-2">
                        <span v-if="!loading">
                            Создать
                        </span>

                        <span v-else class="flex items-center gap-2">
                            <svg class="w-5 h-5 animate-spin" viewBox="0 0 24 24">
                                <circle cx="12" cy="12" r="10" fill="none" stroke="currentColor" stroke-width="4"
                                    opacity="0.25" />
                                <path d="M4 12a8 8 0 018-8" fill="none" stroke="currentColor" stroke-width="4" />
                            </svg>
                            Создание
                        </span>
                    </button>
                </form>
            </DialogPanel>
        </div>
    </Dialog>
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