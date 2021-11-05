<template>
  <div>
    <form action="create-tournament" v-on:submit.prevent="saveTournament">
      <div class="tournament-form">
        <label for="tournamentName">New Tournament Name:</label>
        <input
          name="tournamentName"
          type="text"
          v-model="tournament.tournamentName"
          clearable
        />
      </div>
      <div class="tournament-form">
        <label for="gameName">Game Name: </label>
        <input
          name="gameName"
          type="text"
          v-model="tournament.gameName"
          clearable
        />
      </div>
      <div class="tournament-form">
        <label for="playerCount">Player Count: </label>
        <input
          name="playerCount"
          type="number"
          v-model="tournament.playerCount"
          placeholder="Multiple of 8"
          step="8"
          min="8"
        />
      </div>
      <div class="tournament-form">
        <label for="startDate">Start Date: </label>
        <input name="startDate" type="date" v-model="tournament.dateFrom" />
      </div>
      <div class="tournament-form">
        <label for="endDate">End Date: </label>
        <input name="endDate" type="date" v-model="tournament.dateTo" />
      </div>
      <div class="tournament-form">
        <label for="startTime">Start Time: </label>
        <input name="startTime" type="time" v-model="tournament.startTime" />
      </div>
      <div class="tournament-form">
        <label for="description" style="vertical-align: top;">Description: </label>
        <textarea call="3" cols="50" rows="10" v-model="tournament.description">
        </textarea>
      </div>
      <div class="tournament-form">
        <label for="team">Team Tournament? </label>
        <input
          name="team"
          type="checkbox"
          v-bind:change="tournament.teamTournament"
          v-model="tournament.teamTournament"
        />
      </div>
      <div class="actions">
        <button>Submit</button>
      </div>
      <router-link v-bind:to="{ name: 'home' }">
      <button formaction="#/home">Cancel</button>
    </router-link>
    </form>
    
  </div>
</template>
<script>
import TournamentService from "../services/TournamentService.js";
export default {
  data() {
    return {
      tournament: {
        tournamentName: "",
        gameName: "",
        playerCount: "",
        dateFrom: "",
        dateTo: "",
        startTime: "",
        description: "",
        teamTournament: false,
      },
    };
  },
  methods: {
    saveTournament() {
      const finalTournament = {
        tournamentName: this.tournament.tournamentName,
        gameName: this.tournament.gameName,
        playerCount: this.tournament.playerCount,
        dateFrom: this.tournament.dateFrom,
        dateTo: this.tournament.dateTo,
        startTime: this.tournament.startTime,
        description: this.tournament.description,
        teamTournament: this.tournament.teamTournament,
      };
      // if(finalTournament.startTime.includes("PM")){
      //     let time = parseInt(finalTournament.startTime.substr(0,2));
      //     time += 12;
      //     finalTournament.startTime = time + finalTournament.startTime.substr(2);
      // }
      if (this.tournament.playerCount % 8 !== 0 || this.tournament.playerCount === 0 || this.tournament.playerCount === "") {
        window.alert("Player Count must be a multiple of 8!");
        return;
      }
      TournamentService.createTournament(finalTournament)
        .then((resp) => {
          if (resp.status === 200) {
            window.alert("Tournament Successfully Created!");
            this.$router.push({ name: "home" });
          }
        })
        .catch((e) => {
          window.alert(e + "failed");
        });
    },
  },
};
</script>
<style lang="stylus" scoped></style>