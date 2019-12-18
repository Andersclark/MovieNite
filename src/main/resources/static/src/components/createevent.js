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
            
                <h4>New movienight</h4>
                <h5>Movie: {{event.movie}}</h5>
                
                <input v-model="event.description" type="text" placeholder="description">
                <input v-model="event.location" type="text" placeholder="location">
                
                <select class="timeList" v-model="event.startTime" name="Calendar openings:">
                  <option  v-for="time in calendarOpenings">{{time}}</option>
                </select> 
                
                <button @click="getTimes">Check calendar openings</button>
                <button class="button-primary" v-on:click="saveEvent">Save</button>
            </div>
        </template>
        
        <div v-show="finalizedForm" class="eleven columns">
            <h5>{{event.movie}} -night!</h5>
            <p>Time: {{event.startTime}} - </p>
            <p>Duration: {{event.duration}}</p>
            <p>Location: {{event.location}}</p>
            <p>Description: {{event.description}}</p>
            <ul>Participants: 
                <li>{{event.organizer}}</li>
            </ul>
            <button class="button-primary" v-on:click="flipEventForm">Edit</button>
        </div>
</div>
`,
    data() {
        return {
            finalizedForm: false,
            event: {
                movieId: this.imdbID,
                organizerId: 'organizerID',
                organizer: 'organizer',
                guestIds: [],
                duration: this.runtime,
                startTime: new Date(),
                description: 'description',
                location: 'location',
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
    methods: {
        getTimes: async function() {
            let result = await fetch('/api/calendar?duration=135');
            result = await result.json();
            let timeArray = [];
            for (let time of result) {
                timeArray.push(new Date(time));
            }
            this.calendarOpenings = timeArray;
        },
        saveEvent: async function () {
            this.flipEventForm();
            //this.finalizeEvent();
            const response = await fetch('http://localhost:8080/api/v1/events/',
                {
                    method: 'POST',
                    body: JSON.stringify(this.event)
                }
            );
            this.event = await response.json();
        },
        flipEventForm: function () {
            this.finalizedForm = !this.finalizedForm;
        },
        finalizeEvent: function () {
            console.log("ID: " + (this.$store.user._id));
            //this.event.organizerId = this.$store.user._id;
            //this.event.organizer = this.$store.user.username;
        },
        editEvent: function () {
            this.showEventForm = true;
        },
    },

};
