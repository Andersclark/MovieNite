import movieList from "../components/movieList.js";
import searchField from '../components/searchField.js';

export default {
  components: {
    movieList,
    searchField,
  },

  template: `
        <div class="row">
        <div class="one-half column">
      <searchField />
      <movieList />
      </div>
    </div>
  `
};
