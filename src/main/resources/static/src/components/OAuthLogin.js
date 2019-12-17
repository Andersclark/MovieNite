export default {
  template: `
    <button v-if="displayLoginButton" id="signinButton" @click="onSignInClick">Link my Google Calendar</button>
  `,
  data() {
    return {
      displayLoginButton: true
    };
  },
  methods: {
    onSignInClick() {
      this.$auth2.grantOfflineAccess().then(this.signInCallback);
    },
    async signInCallback(authResult) {
      console.log("authResult", authResult);

      if (authResult["code"]) {
        // Hide the sign-in button now that the user is authorized
        this.displayLoginButton = false;

        // Send the code to the server
        let result = await fetch("api/auth/storeauthcode", {
          method: "POST",
          headers: {
            "Content-Type": "application/octet-stream; charset=utf-8",
            "X-Requested-With": "XMLHttpRequest"
          },
          body: authResult["code"]
        });

        result = await result.json();

        console.log("/storeauthcode:", result);

        if(!result) return

        let login = await fetch("/login", {
          method: "POST",
          headers: {
            "Content-Type": "Application/json"
          },
          body: JSON.stringify({
            username: result.email,
            password: "password"
          })
        });

        login = await login.json();

        console.log("/login", login);
      } else {
        // There was an error.
        console.error("OAuth error");
      }
    }
  }
};
