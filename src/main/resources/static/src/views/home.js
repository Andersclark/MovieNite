import movieList from "../components/movieList.js";
import searchField from '../components/searchField.js';

export default {
  components: {
    movieList,
    searchField,
  },

  template: `
    <div class="container">
        <div class="row">
          
            <searchField />
            <movieList />
          
      </div>
    </div>
  `
};
