<script setup lang="ts">
import type { Pilot, UpdatePilotRequest } from '~/types/pilot';
import { reactive, watch } from 'vue';
import { usePilotApi } from '~/composables/api/usePilotApi';
import { useErrorHandler } from '~/composables/useErrorHandler';

const { pilot } = defineProps<{
  pilot: Pilot
}>();

const emit = defineEmits<{
  refresh: [],
  close: []
}>();

const pilotApi = usePilotApi();
const errorHandler = useErrorHandler();

const isEditing = ref<boolean>(false);
const error = ref<string>('');

const formPilotMapper = (p: Pilot): UpdatePilotRequest => ({
  id: p.id,
  nickname: p.nickname,
  first_name: p.first_name,
  last_name: p.last_name,
  tg_username: p.tg_username ? '@' + p.tg_username : ''
});

const editForm = reactive<UpdatePilotRequest>(formPilotMapper(pilot));

watch(() => pilot, (newPilot) => {
  if (newPilot) {
    Object.assign(editForm, formPilotMapper(newPilot));
    isEditing.value = false;
  }
});

const tgDisplay = computed({
  get: () => editForm.tg_username || '',
  set: (val: string) => {
    const cleaned = val.replace(/^@+/, '');
    editForm.tg_username = cleaned ? '@' + cleaned : '';
  }
});

const firstNameDisplay = computed({
  get: () => editForm.first_name || '',
  set: (val: string) => {
    if (!val) {
      editForm.first_name = '';
      return;
    }
    const capitalized = val.charAt(0).toUpperCase() + val.slice(1).toLowerCase();
    editForm.first_name = capitalized;
  }
});

const lastNameDisplay = computed({
  get: () => editForm.last_name || '',
  set: (val: string) => {
    if (!val) {
      editForm.last_name = '';
      return;
    }
    const capitalized = val.charAt(0).toUpperCase() + val.slice(1).toLowerCase();
    editForm.last_name = capitalized;
  }
});

const handleUpdatePilot = async () => {
  try {
    await pilotApi.put(editForm.id, {
      ...editForm,
      tg_username: editForm.tg_username?.replace(/^@/, '') || ''
    });
    isEditing.value = false;
    emit('refresh');
  } catch (e: any) {
    error.value = errorHandler.handle(e);
  }
};

const cancelEditing = () => {
  Object.assign(editForm, formPilotMapper(pilot));
  isEditing.value = false;
};

const handleDeletePilot = async () => {
  try {
    await pilotApi.delete(pilot.id);
    isEditing.value = false;
    emit('refresh');
  } catch (e: any) {
    error.value = errorHandler.handle(e);
  }
};
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

    <template v-if="!isEditing">
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

      <div class="flex w-full gap-2">
        <button @click="isEditing = true"
          class="bg-primary-container text-on-primary p-2 w-full rounded-lg hover:bg-primary-hover-container">
          Редактировать
        </button>
        <button @click="handleDeletePilot"
          class="bg-error-container text-on-error-container border border-outline-variant/40 py-2 px-3 rounded-lg hover:bg-error/90">
          <Icon name="material-symbols:delete" />
        </button>
      </div>
    </template>

    <template v-else>
      <div class="space-y-4">
        <h3 class="text-lg font-semibold text-primary">Редактирование пилота</h3>

        <div v-if="error" class="text-sm text-error">
          {{ error }}
        </div>

        <div class="space-y-2">
          <label class="text-sm text-on-surface-variant">Ник в игре</label>
          <input v-model="editForm.nickname" type="text" placeholder="Nickname" class="w-full rounded-lg bg-surface-container border border-outline-variant/40
                   text-on-surface placeholder:text-on-surface-variant/50
                   py-2 px-4 focus:outline-none focus:ring-1 focus:ring-primary-container" />
        </div>

        <div class="space-y-2">
          <label class="text-sm text-on-surface-variant">Фамилия</label>
          <input v-model="lastNameDisplay" type="text" placeholder="Иванов" class="w-full rounded-lg bg-surface-container border border-outline-variant/40
                   text-on-surface placeholder:text-on-surface-variant/50
                   py-2 px-4 focus:outline-none focus:ring-1 focus:ring-primary-container" />
        </div>

        <div class="space-y-2">
          <label class="text-sm text-on-surface-variant">Имя</label>
          <input v-model="firstNameDisplay" type="text" placeholder="Иван" class="w-full rounded-lg bg-surface-container border border-outline-variant/40
                   text-on-surface placeholder:text-on-surface-variant/50
                   py-2 px-4 focus:outline-none focus:ring-1 focus:ring-primary-container" />
        </div>

        <div class="space-y-2">
          <label class="text-sm text-on-surface-variant">Telegram</label>
          <input v-model="tgDisplay" type="text" placeholder="username" class="w-full rounded-lg bg-surface-container border border-outline-variant/40
                   text-on-surface placeholder:text-on-surface-variant/50
                   py-2 px-4 focus:outline-none focus:ring-1 focus:ring-primary-container" />
        </div>
      </div>

      <div class="flex w-full gap-2">
        <button @click="handleUpdatePilot"
          class="bg-primary-container text-on-primary hover:bg-primary-hover-container p-2 w-full rounded-lg hover:bg-primary-hover">
          Сохранить
        </button>
        <button @click="cancelEditing"
          class="text-on-tertiary border border-on-tertiary hover:bg-tertiary-hover-container p-2 rounded-lg">
          Отмена
        </button>
      </div>
    </template>
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