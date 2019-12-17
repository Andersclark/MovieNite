export const store = new Vuex.Store({
  state: {
    user: null,
    displayLoginButton: false,
    isSideBarOpen: false,
    searchResults: {},
    totalResults: 0,
    currentSearchString: "",
    currentPage: 1
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
    },
    setDisplayLoginButton(state, val) {
      state.displayLoginButton = val;
    },
    toggleSideBar(state) {
      state.isSideBarOpen = !state.isSideBarOpen;
    },
    updateSearchResults(state, val) {
      state.totalResults = val.totalResults;
      state.searchResults = val.Search;
    },
    updateSearchString(state, val) {
      state.currentSearchString = val;
    },
    updateCurrentPage(state, val) {
      val === "reset" ? (state.currentPage = 1) : (state.currentPage += val);
    }
  }
});
