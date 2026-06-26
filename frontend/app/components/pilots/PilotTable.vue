<script setup lang="ts">
import PilotRow from './PilotRow.vue';
import type { Pilot } from '~/types/pilot';
import { useModal } from '~/composables/useModal';
import CreatePilotModal from './CreatePilotModal.vue';

const props = defineProps<{
    pilots: Pilot[]
    page: number
    total: number
    totalPages: number
    size: number
}>()

const emit = defineEmits<{
    selected: [pilot: Pilot]
    refresh: []
    pageChange: [page: number]
}>()

const modal = useModal();

const visiblePages = computed(() => {
  const total = props.totalPages || 1;
  const current = props.page || 1;
  const maxVisible = 5;

  let start = Math.max(1, current - Math.floor(maxVisible / 2));
  let end = start + maxVisible - 1;

  if (end > total) {
    end = total;
    start = Math.max(1, end - maxVisible + 1);
  }

  return Array.from({ length: end - start + 1 }, (_, i) => start + i);
});
</script>

<template>
    <div class="space-y-4">
        <div
            class="bg-surface-container border border-outline-variant/50 p-4 rounded-lg flex items-center justify-between w-full">
            <span class="text-primary text-4 font-bold uppercase">Пилоты</span>
            <div class="flex gap-2">
                <button
                    class="bg-primary-container text-on-primary hover:bg-primary-hover-container hover:text-on-primary p-2 rounded-md text-on-primary-container "
                    @click="modal.open()">
                    Создать Пилота
                </button>
                <button
                    class="flex items-center p-3 rounded-md text-on-tertiary-container border border-on-tertiary text-on-tertiary hover:bg-tertiary-hover-container"
                    @click="emit('refresh')">
                    <Icon name="mdi:reload" />
                </button>
            </div>
        </div>

        <div class="bg-surface-container border border-outline-variant/50 rounded-lg shadow-1">
            <div class="p-4 border-b border-surface-container-low flex items-center justify-between">
                <h3 class="font-bold text-4 text-primary">Список пилотов</h3>
            </div>

            <div class="overflow-x-auto">
                <table class="flex flex-col">

                    <thead>
                        <tr class="flex w-full bg-surface-container-low text-[11px] uppercase text-on-surface-variant border-b border-outline-variant/50">
                            <th
                                class="px-4 py-1 flex flex-1 justify-start">
                                Id
                            </th>
                            <th
                                class="px-4 py-1 flex flex-1 justify-center">
                                Ник
                            </th>
                            <th
                                class="px-4 py-1 flex flex-1 justify-center">
                                Фамилия Имя
                            </th>
                            <th
                                class="px-4 py-1 flex flex-1 justify-end">
                                Telegram
                            </th>
                        </tr>
                    </thead>

                    <tbody class="divide-y divide-surface-container-low">
                        <PilotRow v-for="pilot in pilots" :key="pilot.id" :pilot="pilot"
                            @click="emit('selected', pilot)" />
                    </tbody>
                </table>
            </div>
        </div>
        <div
            class="bg-surface-container border border-outline-variant/50 rounded-lg shadow-1 p-4 flex items-center justify-between">
            <span class="text-sm text-on-surface-variant">
                Показано {{ page === 1 ? page : (page - 1) * 20 }}-{{ page * 20 - 1 > total ? total : page * 20 - 1 }} из {{ total }} пилотов
            </span>

            <div class="flex items-center gap-2">
                <button :disabled="page === 1" class="w-8 h-8 flex items-center justify-center rounded text-on-tertiary"
                    @click="emit('pageChange', page - 1)">
                    <Icon name="ant-design:left-outlined" />
                </button>

                <button v-for="p in visiblePages" :key="p" class="w-8 h-8 flex items-center justify-center rounded" :class="p === page
                    ? 'border border-on-tertiary text-on-tertiary'
                    : 'hover:bg-surface-container-high'" @click="emit('pageChange', p)">
                    {{ p }}
                </button>

                <button :disabled="page >= totalPages" class="w-8 h-8 flex items-center justify-center rounded text-on-tertiary"
                    @click="emit('pageChange', page + 1)">
                    <Icon name="ant-design:right-outlined" />
                </button>
            </div>
        </div>
    </div>
    <CreatePilotModal v-if="modal.isOpen" @refresh="emit('refresh')" />
</template>