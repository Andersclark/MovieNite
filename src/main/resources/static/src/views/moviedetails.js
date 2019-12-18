import createevent from "../components/createevent.js";

export default {
    components: {
        createevent,
    },

    template: `
    <div class="movieDetails container">
        <div class="row">
            <img class="moviedetails-poster four columns" :src="posterUrl"/>
            <div class="moviedetails-card eight columns">
                <h4 class="moviedetails-header">{{movie.Title}} ({{movie.Year}})</h4>
                <ul class="moviedetails-list">
                    <li><strong>Metascore:</strong> {{movie.Metascore}}%</li>
                    <li><strong>IMDB-rating:</strong> {{movie.imdbRating}}/10 ({{movie.imdbVotes}} votes)</li>
                    <li><strong>Runtime:</strong> {{movie.Runtime}}</li>
                    <li><strong>Directed by:</strong> {{movie.Director}}</li>
                    <li><strong>Actors: </strong>{{movie.Actors}}</li>
                </ul>
                <h5 class="moviedetails-plotheader">Plot</h5>
                <p>{{movie.Plot}}</p>
                 <button v-if="!showEvent" v-on:click="createEvent" class="button-primary">Make a movie night</button>
            </div>
        </div>
        <div class="row">
         <createevent v-if="showEvent" :imdbID="movie.imdbID" :title="movie.Title" :runtime="movie.Runtime"   />
        </div>
    </div>
  `,
    data() {
        return {
            movie: {},
            showEvent: false,
        }
    },
    computed: {
        posterUrl: function () {
            return this.movie.Poster && this.movie.Poster.replace(/@.*$/, "@.jpg");
        }
    },
    methods: {
        getMovie: async function () {
            const response = await fetch('http://localhost:8080/api/v1/movies/' + this.$route.params.id);
            this.movie = await response.json();
        },
        createEvent: function () {
            this.showEvent = true;
        }
    },
    created: function () {
        this.getMovie();
    }
}