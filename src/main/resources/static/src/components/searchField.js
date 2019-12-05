export default {
    template: `
  <div>
  <div class="container">
    <div class="row">
       <div id="searchField">
            <input type="text" v-model="searchstring" v-on:keyup.enter="search">
            <button @click="search"> <i class="fas fa-search"></i></button>
        </div>
    </div>
  </div>
  </div>`,
    data(){
        return {
            searchstring: '',
        }
    },
    methods: {

        async search() {
            const response = await fetch('http://localhost:8080/api/v1/movies/search?title='+ this.searchstring );
            let data = await response.json();
            this.$store.commit('updateSearchResults', data);
        }
    }
};