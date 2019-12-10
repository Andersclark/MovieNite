import home from './views/home.js'
import about from './views/about.js'
import apidoc from './views/apidoc.js'
import moviedetails from "./views/moviedetails.js";

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
    {
      name: "moviedetails",
      path: '/movie/:id',
      component: moviedetails,
      params: true
    }
  ]
});