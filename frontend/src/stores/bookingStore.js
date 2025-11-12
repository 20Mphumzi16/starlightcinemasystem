import { defineStore } from "pinia"

export const useBookingStore = defineStore("booking", {
  state: () => ({
    show: null,
    selectedSeats: [],
    total: 0
  }),

  actions: {
    setBookingData(show, selectedSeats, total) {
      this.show = show
      this.selectedSeats = selectedSeats
      this.total = total
    },
    clearBooking() {
      this.show = null
      this.selectedSeats = []
      this.total = 0
    }
  },

  persist: true // optional if using pinia-plugin-persistedstate
})
