<template>
  <div class="tournament">
      <div id="tournament-details">
      <h1>Tournament Details</h1>
      <tournament-details/>
      </div>
  </div>
</template>

<script>
import TournamentDetails from '../components/TournamentDetails.vue'
export default {
  name: 'tournament', 
  components: {TournamentDetails}
};
</script>

<style>



</style>