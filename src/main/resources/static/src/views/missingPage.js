export default {
  template: `
    <div id="missing-page" class="container">
        <div class="row">
            <div class="one-half column">
                <h4>Missing Page</h4>
                <p>Can't find the page: ${location.pathname}</p>
            </div>
        </div>
    </div>
  `
}