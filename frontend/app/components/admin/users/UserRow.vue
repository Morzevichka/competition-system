<script setup lang="ts">
import type { User } from '~/types/user';

const { user } = defineProps<{
  user: User
}>();

const idDisplay = computed(() => {
  return user.id.slice(0, 6) + '...';
})

const firstLastNamesDisplay = computed(() => {
  return `${user.last_name} ${user.first_name}`;
});
</script>

<template>
  <tr class="cursor-pointer hover:bg-surface-container-high transition-colors text-on-surface-variant flex">
    <td class="p-4 flex-1 flex justify-start" >
      {{ idDisplay }}
    </td>

    <td class="p-4 flex-1 flex justify-center">
      {{ user.email }}
    </td>

    <td class="p-4 flex-1 flex justify-center">
      {{ user.login }}
    </td>

    <td class="p-4 flex-1 flex justify-center">
      {{ firstLastNamesDisplay }}
    </td>

    <td class="p-4 flex-1 flex justify-end">
      {{ user.role }}
    </td>
  </tr>
</template>