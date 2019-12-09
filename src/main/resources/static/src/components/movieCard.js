export default{

  props: ['moviePoster', 'movieTitle', 'movieYear'],

  template: `
  <div class="movie-card col">
        <img class="movie-poster" :src="moviePoster" alt="Movie poster"/>
        <div class="movie-text container">
        <h3 class="movie-header">{{movieTitle}}</h3>
        <h4 class="movie-year">{{movieYear}}</h4>
      </div>
  </div>`

}