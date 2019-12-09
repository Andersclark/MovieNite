export default {
  template: `
  <div>
      <div @click="toggleSideBar" class="hamburger">
          <i class="fas fa-bars"></i>
      </div>
      <div class="nightheader">
        <router-link style="nightnavlink" to="/">Home</router-link>
        <router-link style="nightnavlink" to="/about">About</router-link>
        <router-link style="nightnavlink" to="/apidoc">API</router-link>
      </div>
  </div>`,
 
 methods: {
  toggleSideBar() {
    this.$store.commit('toggleSideBar')
  }
}
};
