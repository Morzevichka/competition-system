<script setup lang="ts">
import type { Pilot } from '~/types/pilot';

const { pilot } = defineProps<{
  pilot: Pilot
}>();

const idDisplay = computed(() => {
  return pilot.id.slice(0, 6) + '...';
})

const firstLastNamesDisplay = computed(() => {
  return `${pilot.last_name} ${pilot.first_name}`;
});
</script>

<template>
  <tr class="cursor-pointer hover:bg-surface-container-high transition-colors text-on-surface-variant flex">
    <td class="p-4 flex-1 flex justify-start" >
      {{ idDisplay }}
    </td>

    <td class="p-4 flex-1 flex justify-center">
      {{ pilot.nickname }}
    </td>

    <td class="p-4 flex-1 flex justify-center">
      {{ firstLastNamesDisplay }}
    </td>

    <td class="p-4 flex-1 flex justify-end hover:text-primary">
      <a :href="`https://t.me/${pilot.tg_username}`" target="_blank" rel="noopener">
          {{ pilot.tg_username || '—' }}
      </a>
    </td>
  </tr>
</template>