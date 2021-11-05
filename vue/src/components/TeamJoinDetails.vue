<template>
  <div class="main">
    <div class="tournamentInfo">
      <div class="tournament">
        Tournament Name: {{ currentTournament.tournamentName }}
        <br />
        Game Name: {{ currentTournament.gameName }}
        <br />
        Player Count: {{ currentTournament.playerCount }}
        <br />
        Date From: {{ currentTournament.dateFrom }}
        <br />
        Date To: {{ currentTournament.dateTo }}
        <br />
        Start Time: {{ currentTournament.startTime }}
        <br />
        Description: {{ currentTournament.description }}
        <br />
        Team: {{currentTournament.teamTournament}}
        <br />
        <td>
          Teams Playing:
          <tr
            v-for="team in currentTeamList"
            v-bind:key="team.teamId"
          >
            {{
              team.teamName
            }}
          </tr>
        </td>
        <br />
        <!-- <td>
            Your Teams:

            <tr v-for='team in teamList' v-bind:key="team.teamId">
                {{ team.teamName }}
            </tr>
        </td> -->
        <div v-if="!userIn">

        <button v-for='team in teamList' v-bind:key='team.teamId'  v-on:click="join(currentTournament.tournamentId,team.teamId)">JOIN ({{team.teamName}})</button>
        </div>
        <button v-if="userIn" v-on:click="leave()">LEAVE</button>
        <router-link v-bind:to="{name: 'update-tournament', params: {id: currentTournament.tournamentId}}">
        <button v-if="isCreator">UPDATE</button>
        </router-link>
        <router-link v-bind:to="{ name: 'home' }">
          <button>BACK TO HOME</button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import TournamentService from "../services/TournamentService.js";

export default {
  data() {
    return {
      cId : 0,
      allTournaments: [],
      currentTournament: [],
      
      currentTeamList : [],
      teamList: [],
      player: {},
      leaveEnabled: false,
      cUser: {},
    };
  },

  created() {
    TournamentService.getAllTournaments().then((tournamentData) => {
      this.allTournaments = tournamentData.data;
      this.currentTournament = this.allTournaments.find(
        (tournament) => tournament.tournamentId == this.$route.params.id
      );
      this.cId = this.currentTournament.tournamentId;
      TournamentService.getAllTeamsByTournamentId(this.cId).then(r => {
        this.currentTeamList = r.data;
    }) 
    });
    TournamentService.getCurrentUser()
      .then((response) => {
        console.log(response.data);
        this.cUser = response.data;
      })
      .catch((e) => console.log(e));
    TournamentService.getUsersTeams().then(r => {
        this.teamList = r.data;
    });
    
  },
  computed: {
    userIn() {
      if (this.currentTournament.players.length > 0) {
        let player = this.currentTournament.players.find((e) => {
          return e.playerId == this.cUser.id;
        });
        if (player.playerId == this.cUser.id) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    },
    isCreator() {
      if (this.currentTournament.creatorId === this.cUser.id) {
        return true;
      } else {
        return false;
      }
    }
  },
  methods: {
    leave: function () {
      // this.keyFor += 1;
      // TournamentService.leaveTournament(this.$route.params.id);
      TournamentService.leaveTournament(this.$route.params.id).then(
        (response) => {
          if (response.data === true) {
            window.alert("Tournament Successfully Left");
            location.reload();
          } else {
            window.alert("You cannot leave a tournament you have not joined");
          }
        }
      );
    },
    join(tournamentId, teamId) {
      let ids = [tournamentId, teamId];
      if (
        this.currentTournament.playerCount ===
        this.currentTournament.players.length
      ) {
        window.alert("This tournament is already at max capacity");
        return;
      }
      TournamentService.joinTeamTournament(ids).then(
        (response) => {
          if (response.data === true) {
            window.alert("Tournament Successfully Joined!");
            this.$router.push({name: 'tournament', params: {id: this.$route.params.id}});
          } else {
            window.alert("You have already joined this tournament");
          }
        }
      ).catch(e => console.log(e));
    },
  },
};
</script>

<style>
</style>