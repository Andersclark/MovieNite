import home from './views/home.js'
import about from './views/about.js'
import apidoc from './views/apidoc.js'
import moviedetails from "./views/moviedetails.js";

export const router = new VueRouter({
  mode: 'history',
  routes: [
    {
      name:"home",
      path: '/', 
      component: home
    },
    {
      name: "about",
      path: '/about', 
      component: about
    },
    {
      name: "apidoc",
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