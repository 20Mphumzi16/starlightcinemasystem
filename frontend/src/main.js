import { createApp } from 'vue'
import App from './App.vue'
import { router } from './router/router.js'
import './styles.css'
import { createPinia } from "pinia"
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import {
  faLock, faEnvelope, faUser, faBook,
  faTachometerAlt, faCalendarCheck, faBell,
  faMailReply, faFaceSmile, faAdd, faMessage,
  faPuzzlePiece, faStar, faPhone, faUserEdit,
  faCreditCard, faHandshake, faExclamationTriangle,
  faMinusCircle, faTimesCircle, faUndoAlt,
  faClock, faPen, faPencil, faLink, faFilter
} from '@fortawesome/free-solid-svg-icons'

library.add(
  faLock, faEnvelope, faUser, faAdd, faMailReply, faClock, faPen,
  faPencil, faLink, faFilter, faBook, faFaceSmile, faTachometerAlt,
  faCalendarCheck, faPuzzlePiece, faStar, faUserEdit, faCreditCard,
  faHandshake, faEnvelope, faBell, faMessage, faPhone,
  faExclamationTriangle, faMinusCircle, faTimesCircle, faUndoAlt
)

// Pinia setup
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

// Create app
const app = createApp(App)
app.use(router)
app.use(pinia)
app.component('font-awesome-icon', FontAwesomeIcon)
app.mount('#app')
