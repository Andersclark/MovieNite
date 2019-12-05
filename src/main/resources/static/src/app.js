import navBar from './components/navBar.js'

export default {

  components: {
    navBar
  },

  template: `
    <div id="app">
      <navBar />
      <router-view />
    </div>
  `
}