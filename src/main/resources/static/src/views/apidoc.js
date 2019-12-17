
export default {
    template: `
    <div id="apidoc">
    <div class="container">
            <div class="row">
                <div class="one-half column">
                     <h3>API documentation</h3>
                      <p>MovieKnight is both a user-friendly webapp for booking private viewings with friends at your home and an API serving data on movies.</p>
                      <p>Our database is in itself a cache of data our users request, which are then collected from the <a href="">Open Movie Database, OMDB</a>, which is pretty slow.</p>
                </div>
            </div>
       
            <h3>Request methods</h3>
            <p>Cutten API-version is v1, which means the current path to the api is <code>/api/v1/</code></p>
            <p>All responses should be in the form of proper HTTP-responses with data in the response-body</p>
            <div class="container">
                <div class="column"
                    <div class="row">
                        <button>GET</button>     <span>Find by Imdb ID</span><code class="nightcode">/movies/{id}</code><span>Movie</span>
                    </div>
                    <div class="row">
                        <button>GET</button>     <span>Search by title</span><code class="nightcode">/movies/search?title=</code><span>MoviePreview[]</span>
                    </div>
                </div>
            </div>
            <div class="container">
            
             <div class="row">
                <div class="column">
                    <h3>Examples</h3>
                    <p>The API returns JSON-objects of either a full Movie-object or an array of MoviePreview-objects, which contain only the essentials.</p>
                    <h4>Object: Movie</h4>
                    <pre class="nightpre"><code class="nightcode" >
{
    "_id": "5dea2a367c7e970998ca2ac0",
    "Title": "Solo: A Star Wars Story",
    "Year": "2018",
    "Rated": "PG-13",
    "Released": "25 May 2018",
    "Runtime": "135 min",
    "Genre": "Action, Adventure, Sci-Fi",
    "Director": "Ron Howard",
    "Writer": "Jonathan Kasdan, Lawrence Kasdan, George Lucas (based on characters created by)",
    "Actors": "Alden Ehrenreich, Joonas Suotamo, Woody Harrelson, Emilia Clarke",
    "Plot": "During an adventure into the criminal underworld, Han Solo meets his future co-pilot Chewbacca and encounters Lando Calrissian years before joining the Rebellion.",
    "Language": "English",
    "Country": "USA",
    "Awards": "N/A",
    "Poster": "https://m.media-amazon.com/images/M/MV5BOTM2NTI3NTc3Nl5BMl5BanBnXkFtZTgwNzM1OTQyNTM@._V1_SX300.jpg",
    "Ratings": [
        {
            "Source": "Internet Movie Database",
            "Rating": null
        },
        {
            "Source": "Rotten Tomatoes",
            "Rating": null
        },
        {
            "Source": "Metacritic",
            "Rating": null
        }
    ],
    "Metascore": "62",
    "imdbRating": "6.9",
    "imdbVotes": "244,965",
    "imdbID": "tt3778644",
    "Type": "movie",
    "DVD": "14 Sep 2018",
    "BoxOffice": "N/A",
    "Production": "Walt Disney Pictures",
    "Website": "N/A",
    "Response": "True"
}
                    </code></pre>
                 </div>
            </div>
            </div>
           <div class="container">
            <div class="row">
                <div class="column">
                    <h4>Object: SearchResult</h4>
                    <pre class="nightpre"><code class="nightcode" >
[
    {
        "_id": null,
        "Title": "Star Wars: Episode IV - A New Hope",
        "Year": "1977",
        "imdbID": "tt0076759",
        "Type": "movie",
        "Poster": "https://m.media-amazon.com/images/M/MV5BNzVlY2MwMjktM2E4OS00Y2Y3LWE3ZjctYzhkZGM3YzA1ZWM2XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg"
    },
    {
        "_id": null,
        "Title": "Star Wars: Episode V - The Empire Strikes Back",
        "Year": "1980",
        "imdbID": "tt0080684",
        "Type": "movie",
        "Poster": "https://m.media-amazon.com/images/M/MV5BYmU1NDRjNDgtMzhiMi00NjZmLTg5NGItZDNiZjU5NTU4OTE0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg"
    },
]
                     </code></pre>
                </div>
            </div>
           </div>
</div>
        </div>
  `,
}