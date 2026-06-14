<script setup>
import { Menu, MenuButton, MenuItems, MenuItem } from '@headlessui/vue'
import { useAuthStore } from '~/store/auth'
import { useAuth } from '@/composables/useAuth';

const authStore = useAuthStore();
const auth = useAuth();

defineProps({
    open: Boolean
})

const emit = defineEmits(['toggle-sidebar']);

const logout = () => {
    auth.logout();
};
</script>

<template>
    <nav class="fixed top-0 w-full z-40 border-b border-outline-variant/40 bg-surface">

        <div class="flex h-12 px-6 items-center justify-between">

            <div class="flex items-center gap-3">
                <button @click="$emit('toggle-sidebar')" class="sm:hidden flex">
                    <Icon name="material-symbols:menu-rounded" />
                </button>

                <NuxtLink to="/">
                    <span class="font-display text-xl font-black italic">
                        ZM<span class="text-primary-container">DS</span>
                    </span>
                </NuxtLink>
            </div>

            <div v-if="!authStore.isLoggedIn" class="flex gap-3">
                <NuxtLink to="/register" class="btn">Register</NuxtLink>
                <NuxtLink to="/login" class="btn-primary">Login</NuxtLink>
            </div>

            <Menu v-else as="div" class="relative">

                <!-- BUTTON -->
                <MenuButton class="w-8 h-8 bg-surface-container-high rounded-full flex items-center justify-center">
                    <span class="text-sm text-on-surface">
                        {{ authStore.user?.email?.charAt(0).toUpperCase() || '?' }}
                    </span>
                </MenuButton>

                <MenuItems class="absolute right-0 mt-2 w-40 bg-surface-container
                 border border-outline-variant/40 rounded-lg shadow-lg overflow-hidden z-50">

                    <MenuItem v-slot="{ active }">
                        <NuxtLink to="/account" class="block px-4 py-2 text-sm"
                            :class="active ? 'bg-surface-container-low' : ''">
                            Аккаунт
                        </NuxtLink>
                    </MenuItem>

                    <MenuItem v-slot="{ active }">
                        <button @click="logout" class="w-full text-left px-4 py-2 text-sm text-red-400"
                            :class="active ? 'bg-surface-container-low' : ''">
                            Выйти
                        </button>
                    </MenuItem>

                </MenuItems>
            </Menu>
        </div>
    </nav>
</template>