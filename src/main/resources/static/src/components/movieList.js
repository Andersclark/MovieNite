import movieCard from './movieCard.js'

export default{
  
  components: {
    movieCard
  },

  template: `
  <div class="movie-list">
    <movieCard :moviePoster="movie.poster" :movieTitle="movie.title" v-for="movie in $store.state.movies" />
  </div>`

}