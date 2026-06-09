<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const props = defineProps({
    open: Boolean
})

const route = useRoute()
const isHovered = ref(false)

const isExpanded = computed(() => {
    return props.open || isHovered.value
})

const competitionMatch = computed(() => {
    return route.path.match(/^\/competitions\/(\d+)/)
})

const isCompetition = computed(() => {
    return !!competitionMatch.value
})
</script>

<template>
    <aside class="fixed left-0 top-12 h-[calc(100vh-3rem)]
         bg-background border-r border-t border-outline-variant/40
         transition-all duration-300 ease-in-out z-50 overflow-hidden" :class="[
            isExpanded ? 'sm:w-52' : 'sm:w-12',
            open
                ? 'w-full translate-x-0'
                : 'w-full -translate-x-full sm:translate-x-0'
        ]" @mouseenter="isHovered = true" @mouseleave="isHovered = false">
        <div class="flex flex-col py-6 gap-6" :class="open ? 'items-start px-6' : 'items-center'"">
            <NuxtLink to="/" class="group flex items-center gap-3 h-[16px]">
                <Icon name="icons8:finish-flag" 
                    class="text-on-surface-variant group-hover:text-primary-container" />
                <span v-if="isExpanded" 
                    class="text-on-surface-variant group-hover:text-primary-container">
                    Соревнования
                </span>
            </NuxtLink>

            <NuxtLink to="/" class="group flex items-center gap-3 h-[16px]">
                <Icon name="boxicons:helmet-filled"
                    class="text-on-surface-variant group-hover:text-primary-container" />
                <span v-if="isExpanded"
                    class="text-on-surface-variant whitespace-nowrap group-hover:text-primary-container">
                    Пилоты
                </span>
            </NuxtLink>

            <div v-if="isCompetition" class="flex-col flex gap-6" :class="open ? 'items-start' : 'items-center'"">
                <div class=" h-px bg-outline-variant/50 mx-2" :class="isExpanded ? 'w-24' : 'w-8'" />

                <NuxtLink to="/" class="group flex items-center gap-3 h-[16px]">
                    <Icon name="ic:round-people" class="text-on-surface-variant group-hover:text-primary-container" />
                    <span v-if="isExpanded"
                        class="text-on-surface-variant whitespace-nowrap group-hover:text-primary-container">
                        Участники
                    </span>
                </NuxtLink>

                <NuxtLink to="/" class="group flex items-center gap-3 h-[16px]">
                    <Icon name="grommet-icons:scorecard"
                        class="text-on-surface-variant group-hover:text-primary-container" />
                    <span v-if="isExpanded"
                        class="text-on-surface-variant whitespace-nowrap group-hover:text-primary-container">
                        Квалификация
                    </span>
                </NuxtLink>

                <NuxtLink to="/" class="group flex items-center gap-3 h-[16px]">
                    <Icon name="hugeicons:traffic-jam-01"
                        class="text-on-surface-variant group-hover:text-primary-container" />
                    <span v-if="isExpanded"
                        class="text-on-surface-variant whitespace-nowrap group-hover:text-primary-container">
                        Парныецц
                    </span>
                </NuxtLink>

                <NuxtLink to="/" class="group flex items-center gap-3 h-[16px]">
                    <Icon name="carbon:result" class="text-on-surface-variant group-hover:text-primary-container" />
                    <span v-if="isExpanded"
                        class="text-on-surface-variant whitespace-nowrap group-hover:text-primary-container">
                        Результаты
                    </span>
                </NuxtLink>

                <div class=" h-px bg-outline-variant/50 mx-2" :class="isExpanded ? 'w-24' : 'w-8'" />

                <NuxtLink to="/" class="group flex items-center gap-3 h-[16px]">
                    <Icon name="mingcute:whistle-fill" class="text-on-surface-variant group-hover:text-primary-container" />
                    <span v-if="isExpanded"
                        class="text-on-surface-variant whitespace-nowrap group-hover:text-primary-container">
                        Судейство
                    </span>
                </NuxtLink>

                <NuxtLink to="/" class="group flex items-center gap-3 h-[16px]">
                    <Icon name="eos-icons:admin" class="text-on-surface-variant group-hover:text-primary-container" />
                    <span v-if="isExpanded"
                        class="text-on-surface-variant whitespace-nowrap group-hover:text-primary-container">
                        Админ Панель
                    </span>
                </NuxtLink>

                <NuxtLink to="/" class="group flex items-center gap-3 h-[16px]">
                    <Icon name="mdi:twitch" class="text-on-surface-variant group-hover:text-primary-container" />
                    <span v-if="isExpanded"
                        class="text-on-surface-variant whitespace-nowrap group-hover:text-primary-container">
                        Трансляция
                    </span>
                </NuxtLink>
            </div>

            <div v-if="true" class="flex-col flex gap-6" :class="open ? 'items-start' : 'items-center'"">
                <div class=" h-px bg-outline-variant/50 mx-2" :class="isExpanded ? 'w-24' : 'w-8'" />
                <NuxtLink to="/" class="group flex items-center gap-3 h-[16px]">
                    <Icon name="mdi:users" class="text-on-surface-variant group-hover:text-primary-container" />
                    <span v-if="isExpanded"
                        class="text-on-surface-variant whitespace-nowrap group-hover:text-primary-container">
                        Пользователи
                    </span>
                </NuxtLink>
                <NuxtLink to="/" class="group flex items-center gap-3 h-[16px]">
                    <Icon name="mdi:car-outline" class="text-on-surface-variant group-hover:text-primary-container" />
                    <span v-if="isExpanded"
                        class="text-on-surface-variant whitespace-nowrap group-hover:text-primary-container">
                        Машины
                    </span>
                </NuxtLink>
            </div>
        </div>
    </aside>
</template>