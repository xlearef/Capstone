<template>
  <div class="bracket">
      <tournament-bracket/>
  </div>
</template>

<script>
import TournamentBracket from '../components/TournamentBracket.vue'
export default {
    components: {
        TournamentBracket
    }
}
</script>

<style>
.bracket{
    background-color: #e5e5e5;
    font-family: sans-serif;
}

</style>