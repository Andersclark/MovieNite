export default {
    template: `
    <div class="movieDetails">
        <img class="moviedetails-poster" :src="posterUrl"/>
        <div class="moviedetails-card">
            <h4 class="moviedetails-header">{{movie.Title}} ({{movie.Year}})</h4>
            <ul class="moviedetails-list">
                <li><strong>Metascore:</strong> {{movie.Metascore}}%</li>
                <li><strong>IMDB-rating:</strong> {{movie.imdbRating}}/10 ({{movie.imdbVotes}} votes)</li>
                <li><strong>Runtime:</strong> {{movie.Runtime}}</li>
                <li><strong>Directed by:</strong> {{movie.Director}}</li>
                <li><strong>Actors: </strong>{{movie.Actors}}</li>
            </ul>
            <a href="#" class="floatbutton">
                <i class="fa fa-calendar-plus plusbutton"></i>
            </a>

            <h5 class="moviedetails-plotheader">Plot</h5>
            <p>{{movie.Plot}}</p>
        </div>
        <div class="movieevent" v-show: >
            
        </div>
</div>
    </div>
  `,
    data(){
        return {
            movie: { },
        }
    },
    computed: {
      posterUrl: function() { return this.movie.Poster && this.movie.Poster.replace(/@.*$/, "@.jpg"); }
    },
    methods: {
        getMovie: async function () {
            const response = await fetch('http://localhost:8080/api/v1/movies/' +  this.$route.params.id );
            this.movie = await response.json();

        }
    },
    created: function() {
        this.getMovie();
        }
}