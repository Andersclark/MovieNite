import navBar from './components/navBar.js'
import sideBar from './components/sideBar.js'

export default {

  components: {
    navBar,
    sideBar
  },

  template: `
    <div id="app">
      <navBar />
      <sideBar />
      <router-view />
    </div>
  `
}