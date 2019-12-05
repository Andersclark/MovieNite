export const store = new Vuex.Store({
	state: {
		greeting: 'Yo dude',
		isSideBarOpen: false,
	},
	mutations: {
		changeGreeting(state, val) {
			state.greeting = val;
		},
		toggleSideBar(state) {
			state.isSideBarOpen = !state.isSideBarOpen;
		},
	},
});
