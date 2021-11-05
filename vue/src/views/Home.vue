<template>
  <div class="home">
    
      <h1 class="welcome">Welcome, {{ user.username }}.</h1>
      <div class="container">
        <router-link class="homebutton" v-bind:to="{ name: 'browser' }"
          >Tournament Browser</router-link
        >
        <router-link class="homebutton" v-bind:to="{ name: 'add-tournament' }"
          >Create Tournament</router-link
        >
        <router-link class="homebutton" v-bind:to="{ name: 'add-team' }"
          >Create Team</router-link
        >
      </div>
      <div id="main-grid">
      <div class="created-tournaments">
        <h3 id="tournaments">Created Tournaments</h3>
        <div
          id="content"
          v-for="tournament in getUserCreatedTournaments"
          v-bind:key="tournament.id"
        >
          <router-link
            v-bind:to="{
              name: 'tournament',
              params: { id: tournament.tournamentId },
            }" style="text-decoration: none;"
          >
            <p class="link">{{ tournament.tournamentName }}</p>
          </router-link>
        </div>
      </div>
      <div class="entered-tournaments">
        <h3 id="enteredTournaments">Entered Tournaments</h3>
        <div id="content"
          v-for="tournament in enteredTournamentList"
          v-bind:key="tournament.tournamentId"
        >
          <router-link
            v-bind:to="{
              name: 'tournament',
              params: { id: tournament.tournamentId },
            }" style="text-decoration: none;"
          >
            <p class="link">{{ tournament.tournamentName }}</p>
          </router-link>
        </div>
      </div>

      <div class="created-teams">
        <h3 id="myteam">Created Teams</h3>
        <div id="content" v-for="team in getUserCreatedTeams" v-bind:key="team.id">
          <router-link
            v-bind:to="{ name: 'team-details', params: { id: team.teamId } }" style="text-decoration: none;"
          >
            <p class="link">{{ team.teamName }}</p>
          </router-link>
        </div>
      </div>
      <div class="joined-teams">
        <h3 id="joinedTeams">Joined Teams</h3>
        <div id="content" v-for="team in joinedTeamList" v-bind:key="team.teamId">
          <router-link
            v-bind:to="{ name: 'team-details', params: { id: team.teamId } }" style="text-decoration: none;"
          >
            <p class="link">{{ team.teamName }}</p>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TournamentService from "../services/TournamentService.js";
import TeamService from "../services/TeamService.js";
export default {
  data() {
    return {
      user: {},
      createdTournamentList: [],
      teamList: [],
      enteredTournamentList: [],
      joinedTeamList: [],
    };
  },
  created() {
    TournamentService.getCurrentUser().then((response) => {
      this.user = response.data;
    });
    TournamentService.getAllTournaments().then((response) => {
      this.createdTournamentList = response.data;
    });
    TeamService.getAllTeams().then((response) => {
      this.teamList = response.data;
    });
    TournamentService.getTournamentsByUserId().then((response) => {
      this.enteredTournamentList = response.data;
    });
    TeamService.getTeamsByUserId().then((response) => {
      this.joinedTeamList = response.data;
    });
  },
  computed: {
    getUserCreatedTournaments() {
      return this.createdTournamentList.filter((tournament) => {
        return tournament.creatorId === this.user.id;
      });
    },

    getUserCreatedTeams() {
      return this.teamList.filter((team) => {
        return team.creatorId === this.user.id;
      });
    },
  },
};
</script>

<style>
template{
  font-family: sans-serif;
}
.welcome {
  font-family: sans-serif;
  text-emphasis: bold;
}
.container{
  font-family: sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;  
  text-align: center;
  
}


.created-tournaments {
  grid-area: createdTournaments;
  
}
.entered-tournaments {
  grid-area: enteredTournaments;
}
.created-teams {
  grid-area: createdTeams;
}
.joined-teams {
  grid-area: joinedTeams;
}
#main-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas:
    "createdTournaments enteredTournaments"
    "createdTeams joinedTeams";
}
.home {
  font-family: sans-serif;
  background-color: #e5e5e5;
}
.homebutton {
  background-color: #14213d;
  color: white;
  padding: 14px 20px;
  margin-top: 20px;
  margin: 8px 8px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  width: 11%;
  text-decoration: none;
}
.homebutton:hover {
  opacity: 0.8;
}
.created-tournaments{   
  border-top: 5px solid black;
  margin: 10px;
  border-left: 5px solid black;
  padding-left: 20px;
  

}
.entered-tournaments{
  border-top: 5px solid black;
  margin: 10px;
  border-left: 5px solid black;
  padding-left: 20px;
}
.created-teams{
  border-top: 5px solid black;
  margin: 10px;
  border-left: 5px solid black;
  padding-left: 20px;
}
.joined-teams{
  
  border-top: 5px solid black;
  margin: 10px;
  border-left: 5px solid black;
  padding-left: 20px;
  
}
.link{
   background-color: black;
  color: #ffffff;
  padding: 14px 14px;
  margin-top: 20px;
  margin: 8px 8px;
  border: none;
  border-radius: 2px;
  cursor: pointer;
  width: 25%;
  text-align: left;
  text-decoration: none;

}
router-link{
  text-decoration: none;
}

</style>