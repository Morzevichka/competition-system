import { defineStore } from 'pinia';

interface User {
  id: number
  email: string
  first_name: string
  last_name: string
  login: string
  roles: string[]
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as User | null,
    isLoggedIn: false
  }),

  getters: {
    hasRole: (state) => (role: string) => {
      return state.user?.roles?.includes(role) ?? false
    },
    
    isAdmin: (state) => state.user?.roles?.includes('ADMIN') ?? false,
    isJudge: (state) => state.user?.roles?.includes('JUDGE') ?? false,
    isStreamer: (state) => state.user?.roles?.includes('STREAMER') ?? false,
    
    userFullName: (state) => {
      if (!state.user) return ''
      return `${state.user.first_name} ${state.user.last_name}`
    }
  },

  actions: {
    setUser(data: User | null) {
      this.user = data
      this.isLoggedIn = !!data
    },

    clearAuth() {
      this.user = null
      this.isLoggedIn = false
    }
  }
})