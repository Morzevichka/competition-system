<script setup lang="ts">
import type { Pilot } from '~/types/pilot';

const { pilot } = defineProps<{
  pilot: Pilot
}>();

const emit = defineEmits<{
  refresh: [],
  close: []
}>();
</script>

<template>
  <div class="bg-surface-container border border-outline-variant/50 rounded-lg relative p-6 space-y-6">

    <div class="absolute top-4 right-4">
      <button
        class="flex items-center justify-center w-8 h-8 text-on-surface-variant hover:text-primary transition-colors"
        @click="emit('close')">
        <Icon name="basil:cross-outline" class="w-7 h-7" />
      </button>
    </div>

    <div class="space-y-1">
        <div class="flex items-center justify-between">
          <h2 class="text-6 font-bold text-primary">
            {{ pilot.nickname || '—' }}
          </h2>
        </div>

        <p class="text-on-surface-variant text-3">
          {{ pilot?.last_name + ' ' + pilot?.first_name }}
        </p>
      </div>

      <div class="flex flex-col gap-4">
        <div class="p-3 rounded-lg bg-surface-container-high border border-outline-variant/30">
          <div class="text-3 text-on-surface-variant">ID</div>
          <div class="text-4 font-bold">{{ pilot?.id }}</div>
        </div>

        <div class="p-3 rounded-lg bg-surface-container-high border border-outline-variant/30 col-span-2">
          <div class="text-3 text-on-surface-variant">Telegram</div>

          <a v-if="pilot?.tg_username" :href="`https://t.me/${pilot.tg_username.replace('@', '')}`" target="_blank"
            class="text-primary font-semibold hover:text-primary-container">
            @{{ pilot.tg_username }}
          </a>

          <span v-else class="text-on-surface-variant">—</span>
        </div>
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