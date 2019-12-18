import OAuthLogin from "./OAuthLogin.js";

export default {
    props: ['imdbID', 'title', 'runtime'],
    components: {
        OAuthLogin
    },
    template: `
    <div class="ten columns createevent" >
        <OAuthLogin/>
        <template v-if="showEventForm" >
            <div v-show="!finalizedForm" class="movie-event-form">
            
                <h4>New movie knight</h4>
                <h5>Movie: {{event.movie}}</h5>
                
                <div id="event-inputs">
                    <textarea v-model="event.description" type="text" placeholder="description"></textarea>
                    <input v-model="event.location" type="text" placeholder="location">
                </div>
                
                <select class="timeList" v-model="event.startTime" name="Calendar-openings:">
                  <option  v-for="time in calendarOpenings" :key="time" :value="time">{{convertDate(time)}}</option>
                </select> 
                
                <button class="button-primary" v-on:click="saveEvent">Save</button>
            </div>
        </template>
        
        <div v-show="finalizedForm" class="eleven columns">
            <h5>{{event.movie}} -night!</h5>
            <p>Time: {{convertDate(event.startTime)}}</p>
            <p>Duration: {{event.duration}} min</p>
            <p>Location: {{event.location}}</p>
            <p>Description: {{event.description}}</p>
            <button class="button-primary" v-on:click="flipEventForm">Edit</button>
        </div>
    </div>
    `,
    data() {
        return {
            finalizedForm: false,
            event: {
                movieId: this.imdbID,
                movieURL: location.href,
                organizerId: 'organizerID',
                organizer: 'organizer',
                guestIds: [],
                duration: this.runtime.replace(" min", ""),
                startTime: 0,
                description: '',
                location: '',
                movie: this.title,
            },
            calendarOpenings: [],
        };
    },
    computed: {
        showEventForm: function () {
            return !this.$store.state.displayLoginButton;
        },
    },
    mounted() {
        this.getTimes();
    },
    methods: {
        convertDate(time) {
            return new Date(time).toUTCString().replace("GMT", "");
        },
        getTimes: async function() {
            let dates = await fetch(`/api/v1/calendar?duration=${this.runtime.replace(" min", "")}`);
            dates = await dates.json();
            let today = new Date();
            this.calendarOpenings = dates.map(date => date - today.getTimezoneOffset() * 60 * 1000)
        },
        saveEvent: async function () {
            this.flipEventForm();
            
            let response = await fetch('/api/v1/events', {
                    method: 'POST',
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(this.event)
                }
            );
            response = await response.text();
            console.log(response)
        },
        flipEventForm: function () {
            this.finalizedForm = !this.finalizedForm;
        },
        editEvent: function () {
            this.showEventForm = true;
        },
    }
};
