<script setup lang="ts">
import { useCarApi } from '~/composables/api/useCarApi';
import type { Car } from '~/types/car';

const { car } = defineProps<{
  car: Car
}>();

const emit = defineEmits<{
  refresh: []
}>();

const carApi = useCarApi();
const errorHandler = useErrorHandler();

const isEditing = ref(false);
const editedTitle = ref(car.title);

const idDisplay = computed(() => car.id.slice(0, 6) + '...');

const startEdit = () => {
  editedTitle.value = car.title;
  isEditing.value = true;
};

const cancel = () => {
  isEditing.value = false;
}

const save = async () => {
  try {
    await carApi.put(car.id, { title: editedTitle.value });
    isEditing.value = false;
    emit("refresh");
  } catch (e: any) {
    confirm(errorHandler.handle(e));
  }
};

const del = async () => {
  try {
    await carApi.delete(car.id);
    emit("refresh");
  } catch (e: any) {
    confirm(errorHandler.handle(e));
  }
}
</script>

<template>
  <tr class="hover:bg-surface-container-high transition-colors text-on-surface-variant flex">
    <td class="p-4 flex-1 flex justify-start">
      {{ idDisplay }}
    </td>

    <td class="p-4 flex-1 flex justify-center">
      <span v-if="!isEditing">
        {{ car.title }}
      </span>

      <input v-else v-model="editedTitle" class="px-1 rounded-lg bg-surface" />
    </td>

    <td class="px-4 flex-1 flex justify-end">
      <div class="flex items-center gap-2">

        <template v-if="!isEditing">
          <button @click="startEdit"
            class="w-9 h-9 border text-on-tertiary border-on-tertiary rounded-lg hover:bg-tertiary-hover-container flex items-center justify-center">
            <Icon name="material-symbols:edit" />
          </button>

          <button @click="del"
            class="w-9 h-9 bg-error-container text-on-error-container border border-outline-variant/40 rounded-lg hover:bg-error/90 flex items-center justify-center">
            <Icon name="material-symbols:delete" />
          </button>
        </template>

        <template v-else>
          <button @click="save"
            class="w-9 h-9 border text-on-tertiary border-on-tertiary rounded-lg hover:bg-tertiary-hover-container flex items-center justify-center">
            <Icon name="material-symbols:check" />
          </button>
          <button @click="cancel"
            class="w-9 h-9 bg-error-container text-on-error-container border border-outline-variant/40 rounded-lg hover:bg-error/90 flex items-center justify-center">
            <Icon name="material-symbols:cancel" />
          </button>
        </template>

      </div>
    </td>
  </tr>
</template>