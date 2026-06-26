<script setup lang="ts">
import { onClickOutside } from '@vueuse/core'
import ActivePilot from '~/components/admin/pilots/ActivePilot.vue'
import PilotTable from '~/components/admin/pilots/PilotTable.vue'
import { usePilotApi } from '~/composables/api/usePilotApi'
import type { Pilot } from '~/types/pilot';
import { useErrorHandler } from '~/composables/useErrorHandler';


definePageMeta({
    middleware: ['admin-only']
});

const pilotApi = usePilotApi();
const errorHandler = useErrorHandler();

const selectedPilot = ref<Pilot | null>(null);

const clearSelectedPilot = () => {
    selectedPilot.value = null;
}

const contentRef = ref<HTMLElement | null>(null);

onClickOutside(contentRef, () => {
    selectedPilot.value = null
});

const error = ref<string>('');

const pilots = ref<Pilot[]>([]);

const page = ref(1);
const size = ref(20);

const total = ref(0);
const totalPages = ref(0);

const fetchPilots = async () => {
    error.value = '';

    try {
        const res = await pilotApi.get(page.value - 1);

        pilots.value = res.items;
        total.value = res.total;
        totalPages.value = res.total_pages;
        size.value = res.size;

        selectedPilot.value = null;
    } catch (e: any) {
        error.value = errorHandler.handle(e);
        throw e;
    }
};
const changePage = async (newPage: number) => {
    page.value = newPage;
    await fetchPilots();
};

onMounted(async () => {
    await fetchPilots();
});
</script>

<template>
    <section class="p-4 sm:p-8 w-full flex justify-center">
        <div class="w-full">
            <div ref="contentRef" class="flex flex-col lg:flex-row gap-6">
                <div class="flex-1">
                    <PilotTable :pilots="pilots" :page="page" :total="total" :total-pages="totalPages" :size="size"
                        @page-change="changePage" @refresh="fetchPilots" @selected="selectedPilot = $event" />
                </div>

                <div class="overflow-hidden transition-all duration-300 ease-out" :class="selectedPilot
                    ? 'opacity-100 w-full lg:w-[clamp(360px,40vw,600px)]'
                    : 'opacity-0 w-0'
                    ">
                    <ActivePilot v-if="selectedPilot" :pilot="selectedPilot" @close="clearSelectedPilot" @refresh="fetchPilots" />
                </div>
            </div>
        </div>
    </section>
</template>