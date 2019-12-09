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
		async paginate() {
             this.currentPage++
             const response = await fetch('http://localhost:8080/api/v1/movies/search?title='+ this.$store.state.currentSearchString + "&page=" + this.currentPage );
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
    </div>
    <div v-if="this.totalPages>1">
      <button @click="paginate">paginate</button></div>
  </div>`,
};
