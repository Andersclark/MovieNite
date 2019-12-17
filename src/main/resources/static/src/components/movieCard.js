export default{

  props: ['moviePoster', 'movieTitle', 'movieId'],

  template: `
  <div class="three columns">
    <router-link :to="'/movie/' + movieId" class="movie-card" tag="div">
      <div class="row">
        <img class="movie-poster twelve columns" :src="moviePoster" alt="Movie poster"/>
      </div>
    
      <div class="row">
          <h6 class="movie-header twelve columns">{{movieTitle}}</h6>
      </div>
    </router-link>
</div>
`
}