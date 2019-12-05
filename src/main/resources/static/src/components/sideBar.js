export default {
	template: `
  <div v-if="$store.state.isSideBarOpen" class="side-bar">
    <div class="link-container">
        <router-link style="margin-bottom: 10px" to="/">Home</router-link>
        <router-link style="margin-bottom: 10px" to="/about">About</router-link>
    <button @click="toggleSideBar" type="reset">Close</button>
    </div>
  </div>`,

	methods: {
		toggleSideBar() {
			this.$store.commit('toggleSideBar');
		},
	},
};
