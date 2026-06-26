<script setup lang="ts">
import { onClickOutside } from '@vueuse/core';
import { useErrorHandler } from '~/composables/useErrorHandler';
import { useUserApi } from '~/composables/api/useUserApi';
import type { User } from '~/types/user';
import UserTable from '~/components/admin/users/UserTable.vue';
import ActiveUser from '~/components/admin/users/ActiveUser.vue';


definePageMeta({
    middleware: ['admin-only']
});

const userApi = useUserApi();
const errorHandler = useErrorHandler();

const selectedUser = ref<User | null>(null);

const clearSelectedPilot = () => {
    selectedUser.value = null;
}

const contentRef = ref<HTMLElement | null>(null);

onClickOutside(contentRef, () => {
    selectedUser.value = null
});

const error = ref<string>('');

const users = ref<User[]>([]);

const page = ref(1);
const size = ref(20);

const total = ref(0);
const totalPages = ref(0);

const fetchUsers = async () => {
    error.value = '';

    try {
        const res = await userApi.get(page.value - 1);

        users.value = res.items;
        total.value = res.total;
        totalPages.value = res.total_pages;
        size.value = res.size;

        selectedUser.value = null;
    } catch (e: any) {
        error.value = errorHandler.handle(e);
        throw e;
    }
};
const changePage = async (newPage: number) => {
    page.value = newPage;
    await fetchUsers();
};

onMounted(async () => {
    await fetchUsers();
});
</script>

<template>
    <section class="p-4 sm:p-8 w-full flex justify-center">
        <div class="w-full">
            <div ref="contentRef" class="flex flex-col lg:flex-row gap-6">
                <div class="flex-1">
                    <UserTable :users="users" :page="page" :total="total" :total-pages="totalPages" :size="size"
                        @page-change="changePage" @refresh="fetchUsers" @selected="selectedUser = $event" />
                </div>

                <div class="transition-all duration-300 ease-out" :class="selectedUser
                    ? 'opacity-100 w-full lg:w-[clamp(360px,40vw,600px)]'
                    : 'opacity-0 w-0'
                    ">
                    <ActiveUser v-if="selectedUser" :user="selectedUser" @close="clearSelectedPilot" @refresh="fetchUsers" />
                </div>
            </div>
        </div>
    </section>
</template>