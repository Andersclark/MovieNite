import movieList from "../components/movieList.js";
import searchField from '../components/searchField.js';

export default {
  components: {
    movieList,
    searchField,
  },

  template: `
    <div>
      <searchField />
      <movieList />
    </div>
  `
};
