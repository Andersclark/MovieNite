export default {
  template: `
  <div>
  <div class="hamburger">
      <i class="fas fa-bars"></i>
  </div>
  <div class="container">
    <div class="row">
      <div class="header">
        <router-link style="margin-right: 10px" to="/">Home</router-link>
        <router-link to="/about">About</router-link>
      </div>
    </div>
  </div>
  </div>`,
};
