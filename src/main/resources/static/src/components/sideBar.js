export default {
	template: `
<div>
	  <div v-if="$store.state.isSideBarOpen" class="side-bar row">
		<div class="link-container eleven columns">
			<button @click="navHome"  style="margin-bottom: 10px">Home</button>
			<button @click="navAbout" style="margin-bottom: 10px">About</button>
			<button @click="navApi" style="margin-bottom: 10px">API</button>   
		</div>		
	</div>
</div>`,

	methods: {
		navHome: function() {
			this.$router.push('home');
			this.$store.commit('toggleSideBar');
		},
		navAbout() {
			this.$store.commit('toggleSideBar');
			this.$router.push('about');
		},
		navApi() {
			this.$store.commit('toggleSideBar');
			this.$router.push('apidoc');
		},

	},
};
