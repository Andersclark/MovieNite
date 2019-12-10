export default{

  props: ['moviePoster', 'movieTitle', 'movieId'],

  template: `
  <router-link :to="'/movie/' + movieId" class="movie-card"   tag="div" >
  <!-- <router-link to="{ name: 'movieDetails', params:{ id: {{movieId}} }}" class="movie-card"   tag="div" > --> 
      <img class="movie-poster" :src="moviePoster" alt="poster"/>
      <h5>{{movieTitle}}</h5>
      <button type="submit">Book</button>
  </router-link>`




}