import movieCard from './movieCard.js';

export default {
	components: {
		movieCard,
	},

	template: `
    <div class="row movie-list">
      <movieCard :key="movie.imdbID"  :movieId="movie.imdbID" :moviePoster="movie.Poster" :movieTitle="movie.Title" v-for="movie in $store.state.searchResults" />
       
       <div class="pagination twelve columns">
       		
				<button v-if="$store.state.currentPage>=2" @click="paginate(-1)">prev</button>
				<button v-else disabled>prev</button>
				
				  <select @change="paginateHelper">
					<option v-for="index in this.totalPages" :selected="$store.state.currentPage===index" :value="index">{{index}}</option>
				  </select>
				 
				<button v-if="$store.state.currentPage<this.totalPages" @click="paginate(1)">next</button>
				<button v-else disabled>next</button>
			</div>
      </div>`,

	computed: {
		totalPages() {
			return Math.ceil(this.$store.state.totalResults / 10);
		},
	},

	methods: {
		async paginate(page) {
			this.$store.commit('updateCurrentPage', page);
			const response = await fetch(
				'http://localhost:8080/api/v1/movies/search?title=' +
					this.$store.state.currentSearchString +
					'&page=' +
					this.$store.state.currentPage,
			);
			let data = await response.json();
			this.$store.commit('updateSearchResults', data);
		},

		paginateHelper() {
      let newPage;
			if (event.target.value < this.$store.state.currentPage) {
        newPage = this.$store.state.currentPage - event.target.value;
        		this.paginate(-newPage);
			} else {
				newPage = event.target.value-this.$store.state.currentPage;
				this.paginate(newPage)
      }
		},
	},
};
