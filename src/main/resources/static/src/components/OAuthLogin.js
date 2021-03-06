export default {
    template: `
    <button v-if="$store.state.displayLoginButton" id="signinButton" @click="onSignInClick" class="button-primary" >Link my Google Calendar (required to create event)</button>
  `,
    methods: {
        onSignInClick() {
            this.$auth2.grantOfflineAccess().then(this.signInCallback);
        },
        async signInCallback(authResult) {
            // console.log("authResult", authResult);

            if (authResult["code"]) {
                // Send the code to the server
                let result = await fetch("api/v1/auth/storeauthcode", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/octet-stream; charset=utf-8",
                        "X-Requested-With": "XMLHttpRequest"
                    },
                    body: authResult["code"]
                });

                result = await result.json();

                // console.log("/storeauthcode:", result);

                if (result.error) return;

                let user = await fetch("/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        username: result.email,
                        password: "password"
                    })
                });

                user = await user.json();

                if (user.email) {
                    // console.log(user);
                    this.$store.commit('setUser', user);
                    this.$store.commit('setDisplayLoginButton', false)
                }

            } else {
                // There was an error.
                console.error("OAuth error");
            }
        }
    },
    mounted: function () {
        //this.onSignInClick();
    }
};
