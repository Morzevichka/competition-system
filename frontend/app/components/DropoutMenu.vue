<script setup lang="ts">
import { ref, watch } from "vue";
import {
    Menu,
    MenuButton,
    MenuItem,
    MenuItems,
} from "@headlessui/vue";

const props = defineProps<{
    items: string[];
    modelValue?: string | null;
    placeholder?: string;
}>();

const emit = defineEmits<{
    (e: "update:modelValue", value: string): void;
}>();

const selected = ref<string | null>(props.modelValue ?? null);

watch(
    () => props.modelValue,
    (value) => {
        selected.value = value ?? null;
    }
);

const selectItem = (item: string) => {
    selected.value = item;
    emit("update:modelValue", item);
};
</script>

<template>
    <Menu as="div" class="relative w-full" v-slot="{ open, close }">
        <MenuButton class="w-full flex items-center justify-between
             rounded-lg
             bg-surface-container
             border border-outline-variant/40
             px-4 py-2
             text-on-surface
             focus:outline-none
             focus:ring-1
             focus:ring-primary-container
             transition-all">
            <span>
                {{ selected || placeholder }}
            </span>

            <Icon name="material-symbols:keyboard-arrow-down-rounded" class="text-2xl transition-transform duration-200"
                :class="{ 'rotate-180': open }" />
        </MenuButton>

        <Transition enter-active-class="transition duration-150 ease-out" enter-from-class="opacity-0 scale-95"
            enter-to-class="opacity-100 scale-100" leave-active-class="transition duration-100 ease-in"
            leave-from-class="opacity-100 scale-100" leave-to-class="opacity-0 scale-95">
            <MenuItems class="absolute left-0 right-0 mt-2 z-50
               rounded-lg
               overflow-auto
               bg-surface-container-high
               border border-outline-variant/40
               shadow-xl
               focus:outline-none">
                <MenuItem v-for="item in items" :key="item" v-slot="{ active }">
                    <button type="button" @click="
                        selectItem(item);
                        close();"
                        class="w-full px-4 py-3 text-left transition-colors" :class="[
                            active ? 'bg-surface-container-highest text-on-surface-variant' : '']">
                        {{ item }}
                    </button>
                </MenuItem>
            </MenuItems>
        </Transition>
    </Menu>
</template>