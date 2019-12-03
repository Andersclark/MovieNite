import { router } from './router.js'
import { store } from './store.js'
import app from './app.js'

new Vue({
  store,
  router,
  render: h => h(app)
}).$mount('#app')
