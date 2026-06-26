<script setup lang="ts">
import { onClickOutside } from '@vueuse/core'
import { useErrorHandler } from '~/composables/useErrorHandler';
import CarTable from '~/components/admin/cars/CarTable.vue';
import { useCarApi } from '~/composables/api/useCarApi';
import type { Car } from '~/types/car';

definePageMeta({
    middleware: ['admin-only']
});

const carApi = useCarApi();
const errorHandler = useErrorHandler();

const selectedCar = ref<Car | null>(null);

const contentRef = ref<HTMLElement | null>(null);

onClickOutside(contentRef, () => {
    selectedCar.value = null
});

const error = ref<string>('');

const cars = ref<Car[]>([]);

const page = ref(1);
const size = ref(20);

const total = ref(0);
const totalPages = ref(0);

const fetchCars = async () => {
    error.value = '';

    try {
        const res = await carApi.get(page.value - 1);

        cars.value = res.items;
        total.value = res.total;
        totalPages.value = res.total_pages;
        size.value = res.size;

        selectedCar.value = null;
    } catch (e: any) {
        error.value = errorHandler.handle(e);
        throw e;
    }
};
const changePage = async (newPage: number) => {
    page.value = newPage;
    await fetchCars();
};

onMounted(async () => {
    await fetchCars();
});
</script>

<template>
    <section class="p-4 sm:p-8 w-full flex justify-center">
        <div ref="contentRef" class="w-full">
            <CarTable :cars="cars" :page="page" :total="total" :total-pages="totalPages" :size="size"
                @page-change="changePage" @refresh="fetchCars" @selected="selectedCar = $event" />
        </div>
    </section>
</template>