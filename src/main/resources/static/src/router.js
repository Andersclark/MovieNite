import home from './views/home.js'
import about from './views/about.js'

export const router = new VueRouter({
  mode: 'history',
  routes: [
    { 
      path: '/', 
      component: home
    },
    { 
      path: '/about', 
      component: about
    }
  ]
})