<template>
  <div class="main">
    <div class="tournamentInfo">
      <div class="tournament">
        <div id="tournament-details">
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
        {{(currentTournament.teamTournament ? "This is a team tournament." : "")}}
        <br />
        <div v-if="currentTournament.teamTournament === false" >
        <td id="players">
          Players:
          <tr
            v-for="player in currentTournament.players"
            v-bind:key="player.playerId"
          >
            {{
              player.userName
            }}
          </tr>
        </td>
        </div>
        <div v-if="currentTournament.teamTournament === true">
          <td id="teams">
          Teams:
          <tr
            v-for="team in currentTournament.teams"
            v-bind:key="team.teamId"
          >
            {{
              team.teamName
            }}
          </tr>
        </td>
        </div>
        </div>
        <br />
        <router-link v-if='currentTournament.teamTournament' :to="{ name : 'team-join' , params : {id : currentTournament.tournamentId}}">
          <button class="detail-button">Team Join</button>
        </router-link>
        <button v-if="!userIn && !currentTournament.teamTournament" v-on:click="join()" class="detail-button">Join</button>
        <button v-if="userIn" v-on:click="leave()" class="detail-button">Leave</button>
        <router-link v-bind:to="{name: 'update-tournament', params: {id: currentTournament.tournamentId}}">
        <button v-if="isCreator" class="detail-button">Update</button>
        </router-link>
                <router-link v-bind:to="{name : 'bracket', params: {id : currentTournament.tournamentId}}">
          <button class="detail-button">Bracket View</button>
        </router-link>
        <router-link v-bind:to="{ name: 'home' }">
          <button class="detail-button">Back to Home</button>
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
      allTournaments: [],
      currentTournament: [],
      player: {},
      leaveEnabled: false,
      cUser: {},
      key1: 0,
    };
  },

  created() {
    TournamentService.getAllTournaments().then((tournamentData) => {
      this.allTournaments = tournamentData.data;
      this.currentTournament = this.allTournaments.find(
        (tournament) => tournament.tournamentId == this.$route.params.id
      );
    });
    TournamentService.getCurrentUser()
      .then((response) => {
        this.cUser = response.data;
      })
      .catch((e) => console.log(e));
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
      TournamentService.leaveTournament(this.$route.params.id).then(
        (response) => {
          if (response.data === true) {
            window.alert("Tournament Successfully Left");
            this.$router.push({name: 'browser'});
          } else {
            window.alert("You cannot leave a tournament you have not joined");
          }
        }
      );
    },
    join() {
      if (
        this.currentTournament.playerCount ===
        this.currentTournament.players.length
      ) {
        window.alert("This tournament is already at max capacity");
        return;
      }
      TournamentService.joinTournament(this.$route.params.id).then(
        (response) => {
          if (response.data === true) {
            window.alert("Tournament Successfully Joined!");
          } else {
            window.alert("You have already joined this tournament");
          }
        }
      );
      location.reload();
    },
  },
};
</script>

<style>
.tournament{
  background-color: #e5e5e5;
  font-family: sans-serif;
  
  
}
#tournament-details{
  font-weight: bold;
  margin-top: 5px;
  padding: 5px;
  border-style: inset;
  text-align: center;
}
.detail-button{
  max-width: 20%;
  margin-left: 10px;
}
#players {
  text-align: center;
}
#teams {
  text-align: center;
}
</style>