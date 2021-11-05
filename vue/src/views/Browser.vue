<template>
  <div class="browser">
    <h1 id="browser-heading">Tournament Browser</h1>
    <tournament-list/>
  </div>
</template>

<script>
import TournamentList from '../components/TournamentList.vue'
export default {
  name: "browser",
  components : {TournamentList}
};
</script>

<style>
.browser{
  font-family: sans-serif;
  background-color: #e5e5e5;
}
#browser-heading{
  text-align: center;
}

</style>