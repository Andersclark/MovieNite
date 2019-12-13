import OAuthLogin from "./OAuthLogin.js";

export default {
    components: {
        OAuthLogin,
    },

    template: `
        <div> 
            <div v-if="showEventForm" class="movie-event movie-event-form">
            <h4>New movienight</h4>
                <input v-model="event.name" type="text" placeholder="event name">
                <input v-model="event.description" type="text" placeholder="description">
                <input v-model="event.time" type="text" placeholder="time">
                <button class="button-primary" v-on:click="showEventForm = false">Save</button>
                <OAuthLogin/>
            </div>
            
            <div v-else class="movie-event">
               <h5>{{event.name}}</h5>
               <h5>{{event.time}}</h5>
               <p>{{event.description}}</p>
               <h5>!!PUT USERLIST HERE !!</h5>
               <button class="button-primary" v-on:click="showEventForm = true">Edit</button>
            </div>
        </div>
`,
    data() {
        return {
            showEventForm: true,
            event: {
                movie: 'movie',
                name: 'name',
                time: 'time',
                description: 'description',
            },
        }
    },
    methods: {
        getEvent: async function () {
            const response = await fetch('http://localhost:8080/api/v1/events/'+ this.event.id);
            return await response.json();
        },
       /* saveEvent: async function(){
            this.showEventForm = false;
            const response = await fetch('http://localhost:8080/api/v1/events/',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-Content-Type-Options': 'nosniff'
                    },
                    body: JSON.stringify(this.event),
                }
            );
            return await response.json();
        },*/
        editEvent: function(){
            this.showEventForm = true;
        }
    },
    created: function() {
        //this.getEvent();
    }
}