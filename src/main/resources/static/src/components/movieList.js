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
    <div class="movie-list">
      <movieCard :key="movie.imdbID" :moviePoster="movie.Poster" :movieTitle="movie.Title" v-for="movie in $store.state.searchResults" />
      {{$store.state.totalResults}}
    </div>
  </div>`,
};
