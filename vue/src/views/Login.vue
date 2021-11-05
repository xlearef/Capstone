<template>
  <div id="heading">
    
    <div id="login" class="text-center">
      <div>
        <div class="container">
        <button
          onclick="document.getElementById('id01').style.display='block'"
          style="width: auto"
          class="login"
        >
          Login
        </button>
        <router-link class="browse-tournament" v-bind:to="{ name: 'browser' }"
          >Tournament Browser</router-link
        >
        </div>
        <div id="id01" class="model">
          <form
            class="form-signin model-content animate"
            @submit.prevent="login"
          >
            <span
              onclick="document.getElementById('id01').style.display='none'"
              class="close"
              title="Close Modal"
              >×</span
            >
            <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
            <div
              class="alert alert-danger"
              role="alert"
              v-if="invalidCredentials"
            >
              Invalid username and password!
            </div>
            <div
              class="alert alert-success"
              role="alert"
              v-if="this.$route.query.registration"
            >
              Thank you for registering, please sign in.
            </div>
            <label for="username" class="sr-only">Username</label>
            <input
              type="text"
              id="username"
              class="form-control"
              placeholder="Username"
              v-model="user.username"
              required
              autofocus
            />
            <label for="password" class="sr-only">Password</label>
            <input
              type="password"
              id="password"
              class="form-control"
              placeholder="Password"
              v-model="user.password"
              required
            />
            <router-link :to="{ name: 'register' }"
              >Need an account?</router-link
            >
            <button type="submit">Sign in</button>
            <button
              type="button"
              onclick="document.getElementById('id01').style.display='none'"
              class="cancelbtn"
            >
              Cancel
            </button>
          </form>
        </div>
        
      </div>
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>

<style>
#heading{
  background-color: #e5e5e5;
  background-image: url(../images/esports.jpg);
  background-size: cover;
  background-repeat: no-repeat;
  padding: 200px;
  
  
}
#login {
  display: flex;
  justify-content: center;
}


.homebutton.login {
  font-family: sans-serif;
  width: 135px;
}

.browse-tournament {
  font-family: sans-serif;
  background-color: #14213d;
  color: white;
  text-decoration: none;
  width: 150px;
  padding: 20px;
   margin: 8px 0;
  border: none;
  cursor: pointer;
}



form {
  margin: auto;
  width: 30%;
  padding: 20px;
  border: 3px solid #f1f1f1;
}
input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #e5e5e5;
  box-sizing: border-box;
}

button {
  background-color: #14213d;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  width: 100%;
}
button:hover, .browse-tournament:hover{
  opacity: 0.8;
}
.text-center {
  padding: 16px;
}
.model {
  display: none;
  position: fixed;
  margin-left: -75px;
  z-index: 1;
  top: 0;
  width: 30%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.4);
  padding-top: 40px;
}
#container {
    width: 100%;
    height: 100%;
    position: absolute;
    visibility:hidden;
    display:none;
}

/*style the model content box*/
.model-content {
  background-color: #fefefe;
  margin: 5% auto 15% auto;
  border: 1px solid #888;
  width: 80%;
}
.login {
  display: block;
  margin: 0;
  align-content: center;
  top: 50%;
  -ms-transform: translateY(-50%);
  transform: translateY(-50%);
}

/* add zoom animation*/
.animate {
  -webkit-animation: animatezoom 0.6s;
  animation: animatezoom 0.6s;
}

@-webkit-keyframes animatezoom {
  from {
    -webkit-transform: scale(0);
  }
  to {
    -webkit-transform: scale(1);
  }
}

@keyframes animatezoom {
  from {
    transform: scale(0);
  }
  to {
    transform: scale(1);
  }
}

@media screen and (max-width: 300px) {
  span.psw {
    display: block;
    float: none;
  }
}
</style>
