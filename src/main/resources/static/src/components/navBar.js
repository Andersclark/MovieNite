export default {
  template: `
  <div>
        <div @click="toggleSideBar" class="hamburger">
          <i class="fas fa-bars"></i>
        </div>
        <ul class="navbar">
            <li class="nightnavlink"><router-link  to="/">HOME</router-link></li>
            <li class="nightnavlink"><router-link to="/about">ABOUT WEB SECURITY</router-link></li>
            <li class="nightnavlink"><router-link to="/apidoc">API</router-link></li>
        </ul>
  </div>`,
 
 methods: {
  toggleSideBar() {
    this.$store.commit('toggleSideBar')
  }
}
};
