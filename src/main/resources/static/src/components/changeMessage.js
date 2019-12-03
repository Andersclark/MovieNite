export default {
  template: `
    <button @click="changeMess">Change Message</button>
  `,
  methods: {
    changeMess() {
      this.$store.commit('changeGreeting', 'WADDAP')
    }
  }
}