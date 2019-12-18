export default {
    template: `
    <div id="about" class="container">
        <div class="row">
            <div class="ten columns">
            <h3>JWT, OAuth & HTTPS</h3>
            <p><strong>This page contains basic info about the three web-security technologies: OAuth2, JWT, HTTPS</strong></p>
            <p>Click on the headlines to open their respective section with more information</p>
                <h4 @click="showJWT = !showJWT" id="json-web-token" class="aboutsectionheader">JSON Web Token<i v-show="!showJWT" class="fas fa-chevron-circle-down"></i><i v-show="showJWT" class="fas fa-chevron-circle-up"></i></h4>
                <div v-show="showJWT">
                  <h5 id="what-is-a-json-web-token-">What is a JSON Web Token?</h5>
                  <p>JWT(JSON Web Token) is a way to transmit data securely by encoding and signing the object digitally. The JWT gets signed with a private key, and can be verified with this. If the data gets tampered before returning to the server, the signature will missmatch and won&#39;t be verified. </p>
                  <p>JWT is an open standard (RFC 7519), that is widely used for securing transmissions. </p>
                  <h5 id="when-to-use-jwt-">When to use JWT?</h5>
                  <ul>
                  <li>Most commonly used for <strong>Authorization</strong>. Once a user has logged in, the JWT can be used to verify the user without the need to login again. The JWT can include username and roles to verify ACL.</li>
                  <li>It can be used for <strong>Sending Information</strong> between frontend and backend securely. The transmission gets signed, and this secures that the data hasn&#39;t gotten tampered with.</li>
                  </ul>
                  <h5 id="the-jwt-structure">The JWT structure</h5>
                  <p>When encoded, the JWT is composed of three parts seperated with dots &quot;  <strong>.</strong>  &quot;:</p>
                  <ul>
                  <li>Header</li>
                  <li>Payload</li>
                  <li>signature</li>
                  </ul>
                  <h6 id="header">Header</h6>
                  <p>The header usually consists of which type the token is, JWT, and which algorithm that is used to encode and sign the token.</p>
                  <h6 id="payload">Payload</h6>
                  <p>The payload is the part of the token that carries the data that gets transmitted. The data is stored in different <a href="https://www.iana.org/assignments/jwt/jwt.xhtml">claims</a> </p>
                  <h6 id="signature">Signature</h6>
                  <p>This is the part that validates that the token hasn&#39;t been tampered with. The signature gets created by encoding the header and payload, and then encode those together with a secret key(random keyboard cat). </p>
                  <h5 id="bottom-line">Bottom line</h5>
                  <p>This all together creates a HTML/HTTP friendly Base64-URL, that can be transmitted securely. The created JWT will look something like this: </p>
                  <p><img src="https://cdn.auth0.com/content/jwt/encoded-jwt3.png" alt=""></p>
                  <h6 id="read-more-on-jwts-official-website-https-jwt-io-">Read more on <a href="https://jwt.io/">JWTs official website</a>.</h6>
                </div>
                <h4 id="oauth2" @click="showOauth = !showOauth" class="aboutsectionheader">OAuth2<i v-show="!showOauth" class="fas fa-chevron-circle-down"></i><i v-show="showOauth" class="fas fa-chevron-circle-up"></i></h4>
                  <div v-show="showOauth">
                    <h5 id="what-is-open-authentication">What is Open Authentication</h5>
                    <p>OAuth 2.0 is one of the most widely used protocols for authorization of access to web services, usually from a provider to a user. It is most widely used in development of different web clients such as apps, devices and webpages.
                    OAuth 2.0 enables a server to grant a user access to a third-party resource securely, without exposing the servers original credentials.</p>
                    <h5 id="oauth-solves-a-problem">OAuth solves a problem...</h5>
                    <p>Traditionally, if, for example, a company wants to share its access to an external resource with a user, the company would have to share it's credentials, usually username and password, with the user.</p>
                    <p>This creates serveral problems, among which the most problematic is the exposing of password and username and the lack of ability to limit the users access to the company's third party-resource.</p>
                    <p>OAuth solves this by enabling the resource-sharing party to never exposes it's own true credentials. Instead, when offering handing out access to a third-party resources to a user, it can give that user an access-token which is unique for that user-agent. Therefore, instead of the entity possibly leaking the actual all-powerful company-credentials, it can only leak it's own token, which is usually client-specific, limited in time and can easily be revoked without hurting the services true credentials.</p>
                    <p><img src="https://assets.digitalocean.com/articles/oauth/implicit_flow.png" alt="OAuth authentication-flow"></p>
                    <h4 id="what-does-it-look-like">What does it look like?</h4>
                    <p><strong>Example of OAuth 2.0-token:</strong></p>
                    <pre><code>{
&quot;access_token&quot;:&quot;ACCESS_TOKEN&quot;,
&quot;token_type&quot;:&quot;bearer&quot;,
&quot;expires_in&quot;:2592000,
&quot;refresh_token&quot;:&quot;REFRESH_TOKEN&quot;,
&quot;scope&quot;:&quot;read&quot;,
&quot;uid&quot;:100101,
&quot;info&quot;:{&quot;name&quot;:&quot;Mark E. Mark&quot;,
        &quot;email&quot;:&quot;mark@thefunkybunch.com&quot;}
}
                    </code></pre>
                    <h6 id="breakdown-of-key-properties">Breakdown of key properties</h6>
                    <p><strong>access_token</strong>: a string which is the true value of the token, but with a limited lifecycle. Is usally the part of the token shared with the client.
                    <strong>refresh_token</strong>: another string used to request a new access_token when the old expires.</p>
                    Read more on<a href="https://oauth.net/2/">OAuths official webpage</a>.
                  </div>
                    
                  <h3 id="https" @click="showHttps = !showHttps" class="aboutsectionheader">HTTPS<i v-show="!showHttps" class="fas fa-chevron-circle-down"></i><i v-show="showHttps" class="fas fa-chevron-circle-up"></i></h3>
                  <div v-show="showHttps">
                    <h4 id="hyper-text-transfer-protocol-secure">Hyper Text Transfer Protocol (Secure)</h4>
                    <p>HTTPS is a protocol for encrypted transport of data over the HTTP-protocol. Its purpose is to ensure that when entities communicate over the internet both parties are who they say they are and the data being sent can only be decrypted by the two parties.
                    HTTPS is now, effectively, a web standard. Regular unencrypted HTTP is so insecure that few modern applications still use it without the extra layer of encryption.</p>
                    <h4 id="how-does-it-work">How does it work?</h4>
                    <p>HTTPS requires each webb-server to hold a signed digital certificate, ensuring that the server is who it says it is. The certificate will be locked on an actual IP-adress, thus requiring your server to have a locked adress.
                    In addition, the actual data being transmitted needs to be encrypted. At first this was done through Secure Socket Layer (SSL) but it is now being replaced by Transport Layer Security(TLS).</p>
                    Read more on <a href="https://oauth.net/2/">JWTs official website</a>.
                  </div>
            </div>
        </div>
    </div>
  `,
    data() {
        return {
            showJWT: false,
            showOauth: false,
            showHttps: false,
        }
    },
}