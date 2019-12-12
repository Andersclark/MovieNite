import { router } from './router.js'
import { store } from './store.js'
import app from './app.js'

const CLIENT_ID = "795907338321-t4bumeavl9g5b5k51itg3257eo95qdfq.apps.googleusercontent.com"
gapi.load('auth2', function() {
  Vue.prototype.$auth2 = gapi.auth2.init({
    client_id: CLIENT_ID,
    scope: "https://www.googleapis.com/auth/calendar.events"
  });
});

new Vue({
  store,
  router,
  render: h => h(app)
}).$mount('#app')
