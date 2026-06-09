<script setup>
import SideBar from '~/components/SideBar.vue'
import TopBar from '~/components/TopBar.vue'
import { ref } from 'vue'

const isSidebarOpen = ref(false)

const toggleSidebar = () => {
    isSidebarOpen.value = !isSidebarOpen.value
}
</script>

<template>
    <div class="min-h-screen bg-background text-on-background font-body text-on-surface-variant z-0">
        <TopBar :open="isSidebarOpen" @toggle-sidebar="toggleSidebar"/>
        <SideBar :open="isSidebarOpen" />

        <main class="pt-12 sm:pl-12 min-h-screen flex">
            <NuxtPage />
        </main>
    </div>
</template>