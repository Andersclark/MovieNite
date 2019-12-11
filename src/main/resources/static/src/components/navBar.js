export default {
  template: `
  <div>
        <div @click="toggleSideBar" class="hamburger">
          <i class="fas fa-bars"></i>
        </div>
        <ul class="nightheader">
            <li class="nightnavlink"><router-link  to="/">HOME</router-link></li>
            <li class="nightnavlink"><router-link to="/about">ABOUT</router-link></li>
            <li class="nightnavlink"><router-link to="/apidoc">API</router-link></li>
        </ul>
  </div>`,
 
 methods: {
  toggleSideBar() {
    this.$store.commit('toggleSideBar')
  }
}
};
