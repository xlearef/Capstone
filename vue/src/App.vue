<template>
  <div id="app">
    <div id="nav">
      <img src="./images/trophy.png" class="img">
      <router-link v-bind:to="{name: 'home'}" style="text-decoration: none;">
      <h4 class="heading">Winner's Bracket</h4>
      </router-link>
      <div class="links">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>&nbsp;
      </div>
    </div>
    <router-view />
  </div>
</template>

<style>
.img{
  max-width: 8%;
  max-height: 8%;
}
#nav{
  display: flex;
  justify-content: space-between;
  
}

body{
  margin: 0;
  
  background-color: #14213d;
}
.heading{
  font-family: sans-serif;
  color: #ffffff;
  font-size: 80px;
  margin: 0;
  padding: 0;
  text-align: center;
  
}

#nav {
  padding: 30px 8px;
  text-align: right;
  background-color: black;
  color: #fca311;
  font-weight: bold;
}

#nav a {
  font-weight: bold;
  color: #fca311;
  
}

#nav a.router-link-exact-active a:hover{
  text-decoration: underline;
}

#nav a.router-link-exact-active {
  color: #fca311;
}

template {
  
  background-color: #E5E5E5;
}

</style>
