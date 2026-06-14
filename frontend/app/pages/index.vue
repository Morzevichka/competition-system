<script setup>
import { Dialog, DialogOverlay, DialogPanel, DialogTitle } from '@headlessui/vue'
import { useAuthStore } from '~/store/auth'
import { useModal } from '~/composables/useModal';

const modal = useModal();

const authStore = useAuthStore()
</script>

<template>
  <Dialog
    :open="modal.isOpen.value"
    @close="modal.close()"
    class="relative z-50 text-on-surface-variant"
  >
    <!-- Overlay -->
    <DialogOverlay class="inset-0 fixed bg-black/50" />

    <!-- Container -->
    <div class="fixed inset-0 flex items-center justify-center p-4">
      <DialogPanel class="min-w-[300px] rounded-lg bg-surface p-6 shadow-xl">
        <DialogTitle class="text-lg font-semibold text-primary">
          Modal window
        </DialogTitle>

        <p class="mt-2 text-sm">
          This is content over page
        </p>

        <button
          class="mt-4 rounded bg-surface-container-low px-4 py-2 text-on-surface-variant"
          @click="modal.close()"
        >
          Close
        </button>
      </DialogPanel>
    </div>
  </Dialog>

  <div class="m-4 sm:m-8">
    <button
      class="h-10 w-24 rounded bg-surface-container-low text-sm text-on-surface-variant"
      @click="modal.open()"
    >
      Open modal
    </button>

    <pre>{{ authStore.user }}</pre>
  </div>
</template>