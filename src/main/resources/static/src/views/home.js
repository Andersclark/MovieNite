import movieList from "../components/movieList.js";
import OAuthLogin from "../components/OAuthLogin.js";

export default {
  components: {
    movieList,
    OAuthLogin
  },
  template: `
    <div>
      <OAuthLogin/>
      <movieList />
    </div>
  `
};
