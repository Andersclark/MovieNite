import createevent from "../components/createevent.js";

export default {
    components: {
        createevent,
    },

    template: `
    <div class="movieDetails flex-grid">
        <img class="moviedetails-poster col" :src="posterUrl"/>
        <div class="moviedetails-card col">
            <h4 class="moviedetails-header">{{movie.Title}} ({{movie.Year}})</h4>
            <ul class="moviedetails-list">
                <li><strong>Metascore:</strong> {{movie.Metascore}}%</li>
                <li><strong>IMDB-rating:</strong> {{movie.imdbRating}}/10 ({{movie.imdbVotes}} votes)</li>
                <li><strong>Runtime:</strong> {{movie.Runtime}}</li>
                <li><strong>Directed by:</strong> {{movie.Director}}</li>
                <li><strong>Actors: </strong>{{movie.Actors}}</li>
            </ul>
            <a v-on:click="createEvent" class="floatbutton">
                <i v-on:click="createEvent" class="fa fa-calendar-plus plusbutton"></i>
            </a>

            <h5 class="moviedetails-plotheader">Plot</h5>
            <p>{{movie.Plot}}</p>
        </div>
            <createevent v-if="showEvent" :imdbID="movie.imdbID"/>
    </div>
  `,
    data(){
        return {
            movie: { },
            showEvent: false,
        }
    },
    computed: {
      posterUrl: function() { return this.movie.Poster && this.movie.Poster.replace(/@.*$/, "@.jpg"); }
    },
    methods: {
        getMovie: async function () {
            const response = await fetch('http://localhost:8080/api/v1/movies/' +  this.$route.params.id );
            this.movie = await response.json();
        },
        createEvent: function(){
            this.showEvent = true;
        }
    },
    created: function() {
        this.getMovie();
        }
}