import navBar from './components/navBar.js'
import sideBar from './components/sideBar.js'
import backdrop from './components/backdrop.js'
export default {

  components: {
    navBar,
    sideBar,
    backdrop
  },

  template: `
    <div id="app" class="container">
    <div id="topspacer"></div>
      <navBar />
      <sideBar />
      <router-view />
    </div>
  `,
  async created() {
    let user = await fetch('api/auth/whoami')
    user = await user.json();

    console.log('/whoami', user);
    this.$store.commit('setUser', user)
  }
}