import { defineStore } from 'pinia';
import type { User } from '~/types/user';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as User | null,
  }),

  getters: {
    hasRole: (state) => (role: string) => {
      return state.user?.role.includes(role) ?? false
    },
    hasAnyRole: (state) => (roles: string[]) => {
      return state.user ? roles.includes(state.user.role) : false;
    },
    
    isAdmin: (state) => state.user?.role.includes('ADMIN') ?? false,
    isJudge: (state) => state.user?.role.includes('JUDGE') ?? false,
    isStreamer: (state) => state.user?.role.includes('STREAMER') ?? false,
    hasSystemRole: (state) => state.user ? !state.user.role.includes('USER') : false,
    
    userFullName: (state) => {
      if (!state.user) return '';
      return `${state.user.first_name} ${state.user.last_name}`;
    },

    isLoggedIn: (state) => !!state.user
  },

  actions: {
    setUser(data: User | null) {
      this.user = data;
    },

    clearAuth() {
      this.user = null;
    }
  }
})