export const store = new Vuex.Store({
	state: {
		greeting: 'Yo dude',
		isSideBarOpen: false,
		searchResults: {},
	},
	mutations: {
		changeGreeting(state, val) {
			state.greeting = val;
		},
		toggleSideBar(state) {
			state.isSideBarOpen = !state.isSideBarOpen;
		},
		updateSearchResults(state, val){
			state.searchResults = val;
		},
	},
});
