import movieCard from './movieCard.js';
import searchField from './searchField.js';

export default {
	components: {
		searchField,
		movieCard,
	},

	template: `
  <div>
    <searchField/>
    <div class="movie-list flex-grid">
    	  <movieCard :key="movie.imdbID" :moviePoster="movie.Poster" :movieTitle="movie.Title" :movieId="movie.imdbID" v-for="movie in $store.state.searchResults" />
    </div>
  </div>`,
};
