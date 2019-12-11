export default{

  props: ['moviePoster', 'movieTitle', 'movieId'],

  template: `
  <router-link :to="'/movie/' + movieId" class="movie-card col" tag="div">
        <img class="movie-poster" :src="moviePoster" alt="Movie poster"/>
        <div class="movie-text container">
        <h3 class="movie-header">{{movieTitle}}</h3>
        <h4 class="movie-year">{{movieYear}}</h4>
      </router-link>
  </div>`
}