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
      <navBar />
      <sideBar />
      <backdrop>
        <router-view />
      </backdrop>
    </div>
  `
}