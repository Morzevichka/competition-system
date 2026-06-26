<script setup lang="ts">
import { reactive, watch } from 'vue';
import DropoutMenu from '~/components/DropoutMenu.vue';
import { useMetaApi } from '~/composables/api/useMetaApi';
import { useUserApi } from '~/composables/api/useUserApi';
import { useErrorHandler } from '~/composables/useErrorHandler';
import { useAuthStore } from '~/store/auth';
import type { UpdateUserRequest, User } from '~/types/user';

const { user } = defineProps<{
  user: User
}>();

const emit = defineEmits<{
  refresh: [],
  close: []
}>();

const userApi = useUserApi();
const metaApi = useMetaApi();
const authStore = useAuthStore();

const errorHandler = useErrorHandler();

const isEditing = ref<boolean>(false);
const error = ref<string>('');

const roles = ref<string[]>([]);

const formUserMapper = (u: User): UpdateUserRequest => ({
  email: u.email,
  login: u.login,
  first_name: u.first_name,
  last_name: u.last_name,
  role: u.role
});

const editForm = reactive<UpdateUserRequest>(formUserMapper(user));

watch(() => user, (newUser) => {
  if (newUser) {
    Object.assign(editForm, formUserMapper(user));
    isEditing.value = false;
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

const fetchRoles = async () => {
  try {
    roles.value = (await metaApi.roles()).items;
  } catch (e: any) {
    error.value = errorHandler.handle(e);
  }
};

const handleUpdateUser = async () => {
  try {
    await userApi.put(user.id, { ...editForm });
    isEditing.value = false;
    emit('refresh');
  } catch (e: any) {
    error.value = errorHandler.handle(e);
  }
};

const cancelEditing = () => {
  Object.assign(editForm, formUserMapper(user));
  isEditing.value = false;
};

const handleDeleteUser = async () => {
  try {
    await userApi.delete(user.id);
    isEditing.value = false;
    emit('refresh');
  } catch (e: any) {
    error.value = errorHandler.handle(e);
  }
};

onMounted(() => {
  fetchRoles();
});
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
            {{ user.email || '—' }}
          </h2>
        </div>

        <p class="text-on-surface-variant text-3">
          {{ user?.last_name + ' ' + user?.first_name }}
        </p>
      </div>

      <div class="flex flex-col gap-4">
        <div class="p-3 rounded-lg bg-surface-container-high border border-outline-variant/30">
          <div class="text-3 text-on-surface-variant">ID</div>
          <div class="text-4 font-bold">{{ user?.id }}</div>
        </div>

        <div class="flex gap-4">
          <div class="flex flex-1 flex-col p-3 rounded-lg bg-surface-container-high border border-outline-variant/30">
            <div class="text-3 text-on-surface-variant">Login</div>
            <div class="text-4 font-bold">{{ user?.login }}</div>
          </div>

          <div class="flex flex-1 flex-col p-3 rounded-lg bg-surface-container-high border border-outline-variant/30">
            <div class="text-3 text-on-surface-variant">Role</div>
            <div class="text-4 font-bold">{{ user?.role }}</div>
          </div>
        </div>


      </div>

      <div class="flex w-full gap-2">
        <button @click="isEditing = true"
          class="bg-primary-container text-on-primary p-2 w-full rounded-lg hover:bg-primary-hover-container">
          Редактировать
        </button>
        <button @click="handleDeleteUser" :disabled="authStore.user?.email === user.email"
          class="bg-error-container text-on-error-container border border-outline-variant/40 py-2 px-3 rounded-lg hover:bg-error/90 disabled:cursor-not-allowed disabled:opacity-50">
          <Icon name="material-symbols:delete" />
        </button>
      </div>
    </template>

    <template v-else>
      <div class="space-y-4 relative">
        <h3 class="text-lg font-semibold text-primary">Редактирование пользователя</h3>

        <div v-if="error" class="text-sm text-error">
          {{ error }}
        </div>

        <div class="space-y-2">
          <label class="text-sm text-on-surface-variant">Почта</label>
          <input v-model="editForm.email" type="text" placeholder="example@example.com" class="w-full rounded-lg bg-surface-container border border-outline-variant/40
                   text-on-surface placeholder:text-on-surface-variant/50
                   py-2 px-4 focus:outline-none focus:ring-1 focus:ring-primary-container" />
        </div>

        <div class="space-y-2">
          <label class="text-sm text-on-surface-variant">Логин</label>
          <input v-model="editForm.login" type="text" placeholder="Login" class="w-full rounded-lg bg-surface-container border border-outline-variant/40
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
          <label class="text-sm text-on-surface-variant">Роль</label>
          <DropoutMenu v-model="editForm.role" :items="roles" placeholder="Выберите роль" />
        </div>
      </div>

      <div class="flex w-full gap-2">
        <button @click="handleUpdateUser"
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