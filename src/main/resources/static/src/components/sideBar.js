export default {
	template: `
<div>
	  <div v-if="$store.state.isSideBarOpen" class="side-bar flex-grid">
		<div class="link-container col">
			<router-link @click="toggleSideBar"  style="margin-bottom: 10px" to="/">Home</router-link>
			<router-link @click="toggleSideBar" style="margin-bottom: 10px" to="/about">About</router-link>
			<router-link @click="toggleSideBar" style="margin-bottom: 10px" to="/apidoc">API</router-link>   
		</div>
		<div class="col" id="sidebarWrapper" @click="toggleSideBar"></div>
	</div>
</div>`,

	methods: {
		toggleSideBar() {
			this.$store.commit('toggleSideBar');
		},
	},
};
