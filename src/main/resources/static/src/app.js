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
    <div id="app">
    <div id="topspacer"></div>
      <navBar />
      <sideBar />
      <router-view />
    </div>
  `,
  async created() {
    let result = await fetch('auth/whoami')
    result = await result.json();

    console.log('/whoami', result);
  }
}