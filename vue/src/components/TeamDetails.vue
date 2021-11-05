<template>
  <div class="main">
    <div class="team-info">
      <div class="team">
        <div id="team-details">
        Team Name: {{ currentTeam.teamName }}
        <br />
        Max Number Of Players: {{ currentTeam.teamMemberCount }}
        <br />
        <td>
          <h3>Members:</h3>
          <tr v-for="player in currentTeam.teamMembers" v-bind:key="player.id">
            {{
              player.username
            }}
          </tr>
        </td>
        </div>
        <button v-if="joinUserNotIn" v-on:click="joinTeam(currentTeam.teamId)" class="team-detail-button">Join Team</button>
        <button v-if="leaveUserIn && !isTeamCreator" v-on:click="leaveTeam(currentTeam.teamId)" class="team-detail-button">Leave Team</button>
        <router-link v-bind:to="{name: 'update-team', params: {id: currentTeam.teamId}}">
        <button v-if="isTeamCreator" class="team-detail-button">Update Team Details</button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import TeamService from "../services/TeamService.js";
import TournamentService from "../services/TournamentService.js";
export default {
  data() {
    return {
      allTeams: [],
      currentTeam: [],
      cUser: {},
    };
  },
  created() {
    TeamService.getAllTeams().then((response) => {
      this.allTeams = response.data;
      this.currentTeam = this.allTeams.find((team) => {
        return team.teamId == this.$route.params.id;
      });
    });
    TournamentService.getCurrentUser().then((response) => {
      this.cUser = response.data;
    });    
  },
  computed: {
      joinUserNotIn() {
          for (let member of this.currentTeam.teamMembers) {
              if (member.playerId === this.cUser.id || this.currentTeam.teamMemberCount === this.currentTeam.teamMembers.length) {
                  return false;
              }
          }
          return true;
      },
      leaveUserIn() {
          for (let member of this.currentTeam.teamMembers) {
              if (member.playerId === this.cUser.id) {
                  return true;
              }
          }
          return false;
      },
      isTeamCreator() {
        if (this.currentTeam.creatorId === this.cUser.id) {
          return true;
        } else {
          return false;
        }
      }
  },
  methods: {
      joinTeam(teamId) {
          if (this.currentTeam.teamMembers.length === this.currentTeam.teamMemberCount) {
              return;
          }
          TeamService.joinTeam(teamId).then(
              (response) => {
                  if (response.status === 200 || response.status === 204) {
                      window.alert("Team Successfully Joined");
                  }
              }
          )
          .catch(
              (error) => {
                  if (error.response) {
                      console.log("Bad Request");
                  } else if (error.request) {
                      console.log("Could not reach service");
                  }
              }
          );
          location.reload();
      },
      leaveTeam(teamId) {
          if (this.currentTeam.creatorId === this.cUser.id) {
              window.alert("Team Creator cannot leave the team. Please disband the team instead.")
              return;
          }
          TeamService.leaveTeam(teamId).then(
              (response) => {
                  if (response.status === 200 || response.status === 204) {
                      window.alert("Successfully Left the Team")
                  }
              }
          )
          .catch(
              (error) => {
                  if (error.response) {
                      console.log("Bad Request");
                  } 
              }
          );
          location.reload();
      },
    
  }
};
</script>

<style>
.team{
  background-color: #e5e5e5;
  font-family: sans-serif;
  
  
}
#team-details{
  font-weight: bold;
  margin-top: 5px;
  padding: 5px;
  border-style: inset;
  text-align: center;
}
.team-detail-button{
  max-width: 20%;
  margin-left: 10px;
  text-align: center;
}
</style>