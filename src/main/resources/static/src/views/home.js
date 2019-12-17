import movieList from "../components/movieList.js";
import searchField from '../components/searchField.js';
import OAuthLogin from '../components/OAuthLogin.js';

export default {
  components: {
    movieList,
    searchField,
    OAuthLogin
  },

  template: `
    <div class="container">
            <OAuthLogin/>
            <searchField />
            <movieList />
    </div>
  `
};
