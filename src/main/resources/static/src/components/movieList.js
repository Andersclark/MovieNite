import movieCard from './movieCard.js';
import searchField from './searchField.js';

export default {
	components: {
		searchField,
		movieCard,
	},

	computed: {
		totalPages() {
			return Math.ceil(this.$store.state.totalResults / 10);
		},
	},

	methods: {
		async paginate(page) {
			this.currentPage += page;
			const response = await fetch(
				'http://localhost:8080/api/v1/movies/search?title=' +
					this.$store.state.currentSearchString +
					'&page=' +
					this.currentPage,
			);
			let data = await response.json();
			this.$store.commit('updateSearchResults', data);
		},
	},
	data: function() {
		return { currentPage: 1 };
	},

	template: `
  <div>
    <searchField/>
    <div class="movie-list">
      <movieCard :key="movie.imdbID" :moviePoster="movie.Poster" :movieTitle="movie.Title" v-for="movie in $store.state.searchResults" />
      <div class="pagination" v-if="this.totalPages>1">
      <button v-if="this.currentPage>=2" @click="paginate(-1)">prev</button>
      <button v-else disabled>prev</button>
      <h6>{{this.currentPage}} / {{this.totalPages}}</h6>
      <button v-if="this.currentPage<this.totalPages"@click="paginate(1)">next</button>
      <button v-else disabled>next</button>
      </div>
    </div>
  </div>`,
};
