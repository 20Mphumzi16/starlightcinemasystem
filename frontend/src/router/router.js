import { createWebHistory, createRouter } from 'vue-router'

import HomePage from '@/pages/HomePage.vue';  
import ShowtimesPage from '@/pages/ShowtimesPage.vue';
import SeatSelectionPage from '@/pages/SeatSelectionPage.vue';
import PaymentPage from '@/pages/PaymentPage.vue';

import BasicInfo from '@/components/BasicInfo.vue'
import DetailedInfo from '@/components/DetailedInfo.vue'
import ChangePassword from '@/components/ChangePassword.vue'
import MyTickets from '@/components/MyTickets.vue'
import MyPasses from '@/components/MyPasses.vue'
import MyMemberships from '@/components/MyMemberships.vue'
import UnpaidBookings from '@/components/UnpaidBookings.vue'
import ProfilePage from '@/pages/ProfilePage.vue'
import OrderHistory from '@/components/OrderHistory.vue'
import MyLikes from '@/components/MyLikes.vue'
import LinkedAccounts from '@/components/LinkedAccounts.vue'
import AdminPage from '@/pages/AdminPage.vue'
import AdminDashboard from '@/components/AdminDashboard.vue'
import AdminMovies from '@/components/AdminMovies.vue'
import AdminShows from '@/components/AdminShows.vue'
import AdminGenres from '@/components/AdminGenres.vue'
import ConfirmationPage from '@/pages/ConfirmationPage.vue'
import AdminLogin from '@/pages/AdminLogin.vue';

const routes = [
  { path: '/', component: HomePage },
   { path: '/confirmation', component: ConfirmationPage },
{
  path: "/seat-selection/:showId",
  name: "seat-selection",
  component: SeatSelectionPage,
  props: true
},

{
  path: '/showtimes/:id',
  name: 'Showtimes',
  component: ShowtimesPage,
  props: true
},

{
  path: '/admin/login',
  name: 'AdminLogin',
  component: AdminLogin
},
{
  path: '/payment',
  name: 'Payment',
  component: PaymentPage,
  props: true
},

  {
  path: '/profile',
  component: ProfilePage,
  children: [
    { path: '', redirect: 'basic' },  // ✅ relative, not "/basic"
    { path: 'basic', name: 'BasicInfo', component: BasicInfo },
    { path: 'detailed', name: 'DetailedInfo', component: DetailedInfo },
    { path: 'password', name: 'ChangePassword', component: ChangePassword },
    { path: 'tickets', name: 'MyTickets', component: MyTickets },
    { path: 'passes', name: 'MyPasses', component: MyPasses },
    { path: 'memberships', name: 'MyMemberships', component: MyMemberships },
    { path: 'unpaid', name: 'UnpaidBookings', component: UnpaidBookings },
    { path: 'orders', name: 'OrderHistory', component: OrderHistory },
    { path: 'likes', name: 'MyLikes', component: MyLikes },
    { path: 'linked', name: 'LinkedAccounts', component: LinkedAccounts }
  ]
},

 {
    path: '/admin',
    component: AdminPage,
    children: [
      { path: '', redirect: '/admin/dashboard' },
      { path: 'dashboard', component: AdminDashboard },
      { path: 'movies', component: AdminMovies },
      { path: 'shows', component: AdminShows },
      { path: 'genres', component: AdminGenres },
    ]
  }




]

export const router = createRouter({
  history: createWebHistory(),
  routes,
})
// ✅ Route guard for admin authentication
router.beforeEach((to, from, next) => {
  const adminId = localStorage.getItem('adminId')

  if (to.path.startsWith('/admin') && to.path !== '/admin/login' && !adminId) {
    next('/admin/login')
  } else if (to.path === '/admin/login' && adminId) {
    // if admin is already logged in, redirect away from login page
    next('/admin/dashboard')
  } else {
    next()
  }
})

