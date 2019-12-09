export default{

  props: ['moviePoster', 'movieTitle'],

  template: `
  <div class="movie-card">
      <img class="movie-poster" :src="moviePoster" alt="poster"/>
      <h5>{{movieTitle}}</h5>
      <button type="submit">Book</button>
  </div>`
}