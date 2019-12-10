export default {
	template: `
  <div>
  <div class="container">
    <div class="row">
       <div id="searchField">
            <input type="text" v-model="searchstring" v-on:keyup.enter="search">
            <button @click="search"> <i class="fas fa-search"></i></button>
        </div>
    </div>
  </div>
  </div>`,
	data() {
		return {
			searchstring: '',
		};
	},
	methods: {
		async search() {
			if (this.searchstring !== this.$store.state.currentSearchString) {
				this.$store.commit('updateCurrentPage', "reset");
			}
			this.$store.commit('updateSearchString', this.searchstring);
			const response = await fetch(
				'http://localhost:8080/api/v1/movies/search?title=' + this.searchstring + "&page=" +this.$store.state.currentPage,
			);
			let data = await response.json();
			this.$store.commit('updateSearchResults', data);
		},
	},
};
