<template>
  <div>
    <form action="create-team" v-on:submit.prevent="saveTeam">
      <div class="team-form">
        <label for="teamName">New Team Name: </label>
        <input type="text" name="teamName" v-model="team.teamName" />
      </div>
      <div class="team-form">
        <label for="teamMemberCount">Player Count: </label>
        <input
          type="number"
          name="teamMemberCount"
          v-model="team.teamMemberCount"
        />
      </div>
      <div class="actions">
        <button>Submit</button>
      </div>
      <router-link v-bind:to="{ name: 'home' }">
      <button>Cancel</button>
    </router-link>
    </form>
    
  </div>
</template>

<script>
import TeamService from "../services/TeamService.js";
export default {
  data() {
    return {
      team: {
        teamName: "",
        teamMemberCount: 0,
      },
    };
  },
  methods: {
    saveTeam() {
      const finalTeam = {
        teamName: this.team.teamName,
        teamMemberCount: this.team.teamMemberCount,
      };
      TeamService.createTeam(finalTeam)
        .then((response) => {
          if (response.status === 200) {
            window.alert("Team POST success");
            this.$router.push({ name: "home" });
          }
        })
        .catch((error) => {
          window.alert(error + " failed");
        });
    },
  },
};
</script>

<style>
form{
  background-color: #e5e5e5;
}
</style>