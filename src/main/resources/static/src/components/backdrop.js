export default {
	template: `
  <div v-if="$store.state.isSideBarOpen" class="backdrop" @click="toggleSideBar">
    <slot></slot>
  </div>
  <div v-else>
    <slot></slot>
  </div>
  `,

	methods: {
		toggleSideBar() {
			this.$store.commit('toggleSideBar');
		},
	},
};
