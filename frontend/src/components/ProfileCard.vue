<template>
  <div class="profile-card">
    <ProfileHeader :name="name" :location="location" :time="time" />

    <Tabs :tabs="tabOptions" v-model="activeTab" />

    <component
        :is="tabComponent"
        v-bind="tabProps"
        @edit="openEditModal"
        @save="saveChanges"
        @cancel="closeModal"
    />

    <EditModal
        v-if="isModalOpen"
        :field="modalField"
        :value="editValue"
        :fieldLabel="modalFieldLabel"
        :today="today"
        @save="saveEdit"
        @close="closeModal"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import ProfileHeader from './ProfileHeader.vue'
import Tabs from './Tabs.vue'
import AboutTab from './AboutTab.vue'
import WorkingHoursTab from './WorkingHoursTab.vue'
import PreferencesTab from './PreferencesTab.vue'
import EditModal from './EditModal.vue'

const tabOptions = ['About', 'Working hours', 'Breaks', 'Preferences', 'Privacy']
const activeTab = ref('About')

const name = 'Michael'
const location = 'South Africa'
const time = '10:20 PM'
const today = 'Monday'

const phone = ref('')
const email = 'michael@example.com'
const todayHours = ref({ enabled: true, start: '09:00', end: '17:00' })
const bookingLink = ref('https://booking.example.com/michael')

const workingDays = ref([
  { name: 'Monday', enabled: true, start: '09:00', end: '17:00' },
  { name: 'Tuesday', enabled: true, start: '09:00', end: '17:00' },
  { name: 'Wednesday', enabled: true, start: '09:00', end: '17:00' },
  { name: 'Thursday', enabled: true, start: '09:00', end: '17:00' },
  { name: 'Friday', enabled: false, start: '', end: '' }
])

const preferences = ref({
  location: 'South Africa',
  canTravel: true,
  lastLogin: '4 hours ago',
  registered: 'Jul 2023',
  totalTeachingExp: '2 yrs',
  teachesOnline: true,
  onlineTeachingExp: '1 yr',
  teachesAtStudentsHome: false,
  homeworkHelp: true,
  gender: 'Male',
  worksAs: 'Tutor',
  speaks: 'English',
  feeRange: {
    local: 'R80–R150/hr',
    usd: '$5–$10/hr'
  }
})

// Modal logic
const isModalOpen = ref(false)
const modalField = ref('')
const modalFieldLabel = computed(() => {
  const map = {
    phone: 'Phone Number',
    email: 'Email Address',
    hours: 'Working Hours',
    link: 'Booking Link'
  }
  return map[modalField.value] || modalField.value
})
const editValue = ref(null)

function openEditModal(field) {
  modalField.value = field
  if (field === 'phone') editValue.value = phone.value
  else if (field === 'link') editValue.value = bookingLink.value
  else if (field === 'hours') editValue.value = { ...todayHours.value }
  isModalOpen.value = true
}

function saveEdit(newValue) {
  if (modalField.value === 'phone') phone.value = newValue
  else if (modalField.value === 'link') bookingLink.value = newValue
  else if (modalField.value === 'hours') todayHours.value = newValue
  isModalOpen.value = false
}

function closeModal() {
  isModalOpen.value = false
}

function saveChanges() {
  // Optional: save to server
  closeModal()
}

const tabComponent = computed(() => {
  switch (activeTab.value) {
    case 'About': return AboutTab
    case 'Working hours': return WorkingHoursTab
    case 'Preferences': return PreferencesTab
    default: return AboutTab
  }
})

const tabProps = computed(() => {
  switch (activeTab.value) {
    case 'About':
      return {
        phone: phone.value,
        email,
        todayHours: todayHours.value,
        bookingLink: bookingLink.value
      }
    case 'Working hours':
      return { days: workingDays.value }
    case 'Preferences':
      return { prefs: preferences.value }
    default:
      return {}
  }
})
</script>

<style scoped>
.profile-card {
  padding: 1.5rem;
  border-radius: 1rem;
  background: white;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
}
</style>
