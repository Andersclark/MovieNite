export const store = new Vuex.Store({
  state: {
    greeting: 'Yo dude'
  },
  mutations: {
    changeGreeting(state, val) {
      state.greeting = val
    }
  }
})