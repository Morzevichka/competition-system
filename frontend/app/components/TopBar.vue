<script setup>
defineProps({
    open: Boolean
})

const emit = defineEmits(['toggle-sidebar'])
</script>

<template>
    <nav class="fixed top-0 w-full z-50 border-b border-outline-variant/40 bg-surface">

        <div class="flex h-12 px-6 items-center justify-between relative">
            <div class="flex items-center gap-3">
                <button @click="emit('toggle-sidebar')" class="sm:hidden item-center flex">
                    <Icon name="material-symbols:menu-rounded" class="text-on-surface-variant text-[22px]" />
                </button>

                <span class="font-display text-xl font-black text-on-surface tracking-tighter italic">
                    ZM<span class="text-primary-container">DS</span>
                </span>
            </div>

            <div class="flex items-center gap-2 sm:gap-3">

                <NuxtLink to="/register" class="
                    h-8 px-4 flex items-center justify-center rounded-lg
                    border border-outline-variant/40 bg-surface-container-high
                    text-sm text-on-surface-variant
                    hover:bg-surface-container hover:text-on-surface">
                    Register
                </NuxtLink>

                <NuxtLink to="/login" class="
                    h-8 px-4 flex items-center justify-center rounded-lg
                    border border-outline-variant/40 bg-primary-container
                    text-sm text-on-primary-container font-medium
                    hover:bg-primary hover:text-on-primary">
                    Login
                </NuxtLink>
            </div>
        </div>
    </nav>
</template>