<template>
<div class="details">
  <h1>Team Details</h1>
  <team-details/>
</div>
</template>

<script>
import TeamDetails from '../components/TeamDetails.vue'
export default {
  components: { TeamDetails },

}
</script>

<style>
.details{
  background-color: #e5e5e5;
  font-family: sans-serif;
}


</style>