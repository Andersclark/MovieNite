import home from './views/home.js'
import about from './views/about.js'
import apidoc from './views/apidoc.js'

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
    },
    {
      path: '/apidoc',
      component: apidoc
    },
  ]
})