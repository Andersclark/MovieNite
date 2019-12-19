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
      <button v-if="!$store.state.displayLoginButton" @click="logout">Logout</button>
        </ul>
  </div>`,

  methods: {
    toggleSideBar() {
      this.$store.commit("toggleSideBar");
    },
    async logout() {
      let response = await fetch("/logout");
      response = await response.text();

      if (response == '"Bye!"') {
        this.$store.commit("setUser", null);
        this.$store.commit('setDisplayLoginButton', true)
      }
    }
  }
};
