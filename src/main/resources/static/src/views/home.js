export default {
  template: `
    <div id="home" class="container">
      <div class="row">
        <div class="one-half column" style="margin-top: 25%">
          <h4>MovieNite, Yeeeeeeeeaa!</h4>
          <p>This index.html page is a placeholder with the CSS, font and favicon. It's just waiting for you to add some
            content! If you need some help hit up the <a href="http://www.getskeleton.com">Skeleton documentation</a>.</p>
        </div>
      </div>
      <button v-if="displayLoginButton" id="signinButton" @click="onSignInClick">Link my Google Calendar</button>
    </div>
  `,
  data() {
    return {
      displayLoginButton: true
    }
  },
  methods: {
    onSignInClick() {
      this.$auth2.grantOfflineAccess().then(this.signInCallback);
    },
    async signInCallback(authResult) {
      console.log('authResult', authResult);
    
      if (authResult['code']) {
    
        // Hide the sign-in button now that the user is authorized
        this.displayLoginButton = false;
    
        // Send the code to the server
        let result = await fetch('/storeauthcode', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/octet-stream; charset=utf-8',
            'X-Requested-With': 'XMLHttpRequest',
          },
          body: authResult['code']
        });
        
        console.log('/storeauthcode:', await result.text());
        
      } else {
        // There was an error.
        console.error('OAuth error');
        
      }
    }
  }
}