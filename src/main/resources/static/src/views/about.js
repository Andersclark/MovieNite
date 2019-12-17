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
                <button @click="getEvents">Get Events</button>
            </div>
        </div>
    </div>
  `,
  methods: {
    async getEvents() {
      let result = await fetch('/api/calendar')
      console.log(await result.text());
    }
  },
  computed: {
    greeting() {
      return this.$store.state.greeting
    }
  }
}