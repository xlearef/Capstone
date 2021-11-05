<template>
<div class="jointeam">

<h1>Enter Your Team for This Tournament</h1>
  <team-join-details />
</div>
</template>

<script>
import TeamJoinDetails from '../components/TeamJoinDetails.vue'

export default {
    components : {
        TeamJoinDetails
    }
}
</script>

<style>
.jointeam{
  font-family: sans-serif;
  background-color: #e5e5e5;
}

</style>