import message from '../components/message.js'
import changeMessage from '../components/changeMessage.js'

export default {
  components: {
    message,
    changeMessage
  },
  template: `
    <div id="about" class="container">
        <div class="row">
            <div class="one-half column">
                <h4>About</h4>
                <p>Lorem ipsum and so on...</p>
                
            </div>
        </div>
    </div>
  `,
  computed: {
    greeting() {
      return this.$store.state.greeting
    }
  }
}