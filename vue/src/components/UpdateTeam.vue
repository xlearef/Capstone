<template>
  <div>
    <form action="create-team" v-on:submit.prevent="saveTeam">
      <div class="team-form">
        <label for="teamName">Updated Team Name: </label>
        <input type="text" name="teamName" v-model="currentTeam.teamName" />
      </div>
      <div class="team-form">
        <label for="teamMemberCount">Player Count: </label>
        <input
          type="number"
          name="teamMemberCount"
          v-model="currentTeam.teamMemberCount"
          min="1"
        />
      </div>
      <div class="actions">
        <button>Submit</button>
      </div>
    </form>
    <router-link v-bind:to="{ name: 'team-details', params: {id: currentTeam.teamId} }">
      <button>Cancel</button>
    </router-link>
  </div>
</template>

<script>
import TeamService from '../services/TeamService.js'
export default {
    data() {
        return {
            allTeams: [],
            currentTeam: {}
        }
    },
    created() {
        TeamService.getAllTeams().then(
            (response) => {
                this.allTeams = response.data;
                this.currentTeam = this.allTeams.find(
                    (team) => {
                        return team.teamId === this.$route.params.id;
                    }
                );
            }
        );
    },
    methods: {
        saveTeam() {
            const updatedTeam = {
                teamId: this.currentTeam.teamId,
                creatorId: this.currentTeam.creatorId,
                teamName: this.currentTeam.teamName,
                teamMemberCount: this.currentTeam.teamMemberCount
            };
            TeamService.updateTeam(updatedTeam).then(
                (response) => {
                    if (response.data === true) {
                        window.alert("Team Successfully Updated!")
                        this.$router.push({name: 'team-details', params: {id: this.currentTeam.teamId}});
                    } else if (response.data === false) {
                        window.alert("Only the team creator can update a team")
                    }
                }
            );
        }
    }
};
</script>

<style>
</style>