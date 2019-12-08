export default{

  props: ['moviePoster', 'movieTitle'],

  template: `
  <div class="movie-card">
        <img class="movie-poster" :src="moviePoster" alt="Movie poster"/>
        <div class="movie-text container">
        <h3 class="movie-header">{{movieTitle}}</h3>
        <h4 class="movie-year">{{movieYear}}</h4>
        <button class="bookbutton">Book</button>
      </div>
  </div>`

}