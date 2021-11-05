<template>
  <div class="tournament">
    <div id="search">
    <button
      class="displayButton"
      v-on:click="displayFilter = !displayFilter"
      v-if="displayFilter == false"
    >
      Show Search Options
    </button>
    <button
      class="displayButton"
      v-on:click="displayFilter = !displayFilter"
      v-if="displayFilter == true"
    >
      Hide Search Options
    </button>
    <tr v-if="displayFilter">
      <td>
        <label for="tournamentName">Tournament Name:</label>
        <input
          class="search-box"
          type="text"
          name="tournamentName"
          v-model="filter.tournamentName"
        />
      </td>
      <td>
        <label for="tournamentName">Game Name:</label>
        <input class="search-box" type="text" name="gameName" v-model="filter.gameName" />
      </td>
    </tr>
</div>
    <div
      v-for="tournament in filteredList"
      v-bind:key="tournament.tournamentId"
     id="tournament-details">
      <h4>{{ tournament.tournamentName }}</h4>
      <h5>{{ tournament.gameName }}</h5>
      <p
        class="not-full"
        v-if="
          filling(
            tournament.players.length > 0
              ? tournament.players.length
              : tournament.teams.length,
            tournament.playerCount
          ) === 0
        "
      >
        {{
          tournament.players.length > 0
            ? tournament.players.length
            : tournament.teams.length
        }}
        / {{ tournament.playerCount }}
      </p>
      <p
        class="almost-full"
        v-if="
          filling(
            tournament.players.length > 0
              ? tournament.players.length
              : tournament.teams.length,
            tournament.playerCount
          ) === 1
        "
      >
        {{
          tournament.players.length > 0
            ? tournament.players.length
            : tournament.teams.length
        }}
        / {{ tournament.playerCount }} - Filling up fast!
      </p>
      <p
        class="full"
        v-if="
          filling(
            tournament.players.length > 0
              ? tournament.players.length
              : tournament.teams.length,
            tournament.playerCount
          ) === 2
        "
      >
        {{
          tournament.players.length > 0
            ? tournament.players.length
            : tournament.teams.length
        }}
        / {{ tournament.playerCount }} - Full
      </p>
      <router-link
        :to="{ name: 'tournament', params: { id: tournament.tournamentId } }"
      >
        <button class="browser-button">View Tournament Details</button>
      </router-link>
      <router-link to="/tournaments">
        <button class="browser-button"
          v-if="tournament.teamTournament == false"
          v-on:click="joinTournament(tournament.tournamentId)"
        >
          Join
        </button>
        <router-link
          v-else
          :to="{ name: 'team-join', params: { id: tournament.tournamentId } }"
        >
          <button class="browser-button">Team Join</button>
        </router-link>
      </router-link>
    </div>
  </div>
</template>

<script>
import TournamentService from "../services/TournamentService.js";
export default {
  name: "tournament-list",
  data() {
    return {
      tournaments: [],
      competitors: [],
      filter: {
        tournamentName: "",
        gameName: "",
      },
      displayFilter: false,
    };
  },
  created() {
    TournamentService.getAllTournaments()
      .then((response) => {
        this.tournaments = response.data;
      })
      .catch((error) => console.log(error));
  },
  methods: {
    joinTournament(tournamentId) {
      let tournament = this.tournaments.find((tournament) => {
        return tournament.tournamentId === tournamentId;
      });
      if (tournament.playerCount === tournament.players.length) {
        window.alert("This tournament is already at max capacity");
        return;
      }
      TournamentService.joinTournament(tournamentId)
        .then((response) => {
          if (response.status === 200 || response.status === 204) {
            if (response.data === true) {
              window.alert("Tournament Successfully Joined");
            } else if (response.data === false) {
              window.alert("You have already joined this tournament");
            }
          }
        })
        .catch((e) => window.alert(e));
      location.reload();
    },
    filling(currentCount, maxCount) {
      let percentage = currentCount / maxCount;
      if (percentage < 0.75) {
        return 0;
      } else if (percentage >= 0.75 && percentage < 1.0) {
        return 1;
      } else {
        return 2;
      }
    },
  },

  computed: {
    filteredList() {
      let filters = this.filter;
      return this.tournaments.filter((tournament) => {
        return (
          tournament.gameName
            .toLowerCase()
            .includes(filters.gameName.toLowerCase()) &&
          tournament.tournamentName
            .toLowerCase()
            .includes(filters.tournamentName.toLowerCase())
        );
      });
    },
  },
};
</script>

<style>
.displayButton {
  background-color: #14213d;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 20%;
}
button:hover {
  opacity: 0.8;
}
.text-center {
  padding: 16px;
}
td {
  font-weight: bold;
}
.full {
  color: red;
}
.almost-full {
  color: orange;
}
.browser-button{
  max-width: 20%;
  margin-left: 10px;
}
#search{
  text-align: center;
}

</style>