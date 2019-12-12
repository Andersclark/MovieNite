export default {
	template: `
  <div>
  <div class="container">
    <div class="row">
       <div id="searchField">
            <input type="text" v-model="searchString" v-on:keyup.enter="search">
            <button @click="search"> <i class="fas fa-search"></i></button>
            <h3 v-if="noResults">{{errorMsg}}</h3>
        </div>
    </div>
  </div>
  </div>`,
	data() {
		return {
      searchString: '',
      noResults: false,
      errorMsg: ''
		};
	},
	methods: {
		async search() {
      this.noResults = false;
			if (this.searchString !== this.$store.state.currentSearchString) {
				this.$store.commit('updateCurrentPage', 'reset');
			}
			this.$store.commit('updateSearchString', this.searchString);
			const response = await fetch(
				'http://localhost:8080/api/v1/movies/search?title=' +
					this.searchString +
					'&page=' +
					this.$store.state.currentPage,
      );
      let data = await response.json();
      if(response.status !== 200) {
        data.message ? this.errorMsg = data.message: this.errorMsg = "No Results"
        this.noResults = true; 
      }
			this.$store.commit('updateSearchResults', data);
		},
	},
};
