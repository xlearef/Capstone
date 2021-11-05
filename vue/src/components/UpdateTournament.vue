<template>
  <div>
    <form action='update-tournament' v-on:submit.prevent='saveTournament'>
        <div class="tournament-form">
            <label for="tournamentName">New Tournament Name:</label>
            <input name='tournamentName' type="text" v-model="currentTournament.tournamentName" clearable/>
        </div>
        <div class="tournament-form">
            <label for="gameName">Game Name: </label>
            <input name='gameName' type="text" v-model="currentTournament.gameName" clearable/>
        </div>
        <div class="tournament-form">
            <label for="playerCount">Player Count: </label>
            <input name='playerCount' type="number" v-model="currentTournament.playerCount" min="8" placeholder="Multiples of 8" step="8"/>
        </div>
        <div class="tournament-form">
            <label for="startDate">Start Date: </label>
            <input name='startDate' type='date' v-model='currentTournament.dateFrom'>
        </div>
        <div class="tournament-form">
            <label for="endDate">End Date: </label>
            <input name='endDate' type='date' v-model='currentTournament.dateTo'>
        </div>
        <div class="tournament-form">
            <label for="startTime">Start Time: </label>
            <input name='startTime' type='time' v-model='currentTournament.startTime'>
        </div>
        <div class="tournament-form">
            <label for="description">Description: </label>
            <textarea call="3" cols="50" rows="10" v-model='currentTournament.description'>
            </textarea>
        </div>
        <div class="tournament-form">
            <label for="team">Team Tournament? </label>
            <input name='team' type='checkbox' v-bind:change='currentTournament.teamTournament' v-model='currentTournament.teamTournament'>
        </div>
        <div class="actions">
        <button>Submit</button>
        </div>
    </form>
    <router-link v-bind:to="{name: 'tournament', params: {id: this.$route.params.id}}">
        <button>Cancel</button>
    </router-link>
</div>
</template>

<script>
import TournamentService from '../services/TournamentService.js'
export default {
     data() {
        return {
            allTournaments: [],
            currentTournament: {},
            cUser: {}
        }
    },
    created() {
        TournamentService.getAllTournaments().then(
            (response) => {
                if (response.status === 200) {
                    this.allTournaments = response.data;
                    this.currentTournament = this.allTournaments.find(
                        (tournament) => {
                            return tournament.tournamentId === this.$route.params.id;
                        }
                    );
                }
            }
        );
        TournamentService.getCurrentUser().then(
            (response) => {
                this.cUser = response.data;
            }
        );
    },
    methods: {
        saveTournament() {
            const finalTournament = {
                tournamentId: this.currentTournament.tournamentId,
                creatorId: this.currentTournament.creatorId,
                tournamentName : this.currentTournament.tournamentName,
                gameName : this.currentTournament.gameName,
                playerCount : this.currentTournament.playerCount,
                dateFrom : this.currentTournament.dateFrom,
                dateTo : this.currentTournament.dateTo,
                startTime : this.currentTournament.startTime,
                description : this.currentTournament.description,
                teamTournament : this.currentTournament.teamTournament
            };
            TournamentService.updateTournament(finalTournament).then(
                (response) => {
                    if (response.data === true) {
                        window.alert("Tournament Successfully Updated!")
                        this.$router.push({name: 'tournament', params: {id: this.$route.params.id}})
                    } else if (response.data === false) {
                        window.alert("You cannot update a tournament if you are not the creator")
                    }
                }
            ); 
        }
    }
}
</script>

<style>

</style>