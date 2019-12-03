import message from '../components/message.js'
import changeMessage from '../components/changeMessage.js'

export default {
  components: {
    message,
    changeMessage
  },
  template: `
    <div id="about">
      <h2>About stuff</h2>
      <message :greeting="greeting" />
      <changeMessage />
    </div>
  `,
  computed: {
    greeting() {
      return this.$store.state.greeting
    }
  }
}