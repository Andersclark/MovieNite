import movieList from '../components/movieList.js'
import oauthLogin from '../components/oauthLogin.js'

export default {
  components: {
    movieList,
    oauthLogin
  },

  template: `
    <div>
      <oauth-login />
      <movieList />
    </div>
  `
}