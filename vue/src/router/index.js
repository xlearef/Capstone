import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import Create from '../views/Create.vue'
import Browser from '../views/Browser.vue'
import Tournament from '../views/Tournament.vue'
import TeamForm from '../views/CreateTeam.vue'
import TeamDetails from '../views/TeamDetails.vue'
import UpdateTeam from '../views/UpdateTeam.vue'
import UpdateTournament from '../views/UpdateTournament.vue'
import TeamJoin from '../views/TeamJoin.vue'
import TournamentBracket from '../views/TournamentBracket.vue'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/tournaments/add',
      name: 'add-tournament',
      component: Create,
      meta : {
        requiresAuth : true
      }
    },
    {
      path: '/tournaments',
      name: 'browser',
      component: Browser,
      meta : {
        requiresAuth : false
      }
    },
    {
      path : '/tournament/:id',
      name : 'tournament',
      component : Tournament,
      meta : {
        requiresAuth : false
      }
    },
    {
      path: '/teams/add',
      name: 'add-team',
      component: TeamForm,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/team/:id',
      name: 'team-details',
      component: TeamDetails,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/team/:id/update',
      name: 'update-team',
      component: UpdateTeam,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/tournament/:id/team-tournament-join',
      name: 'team-join',
      component: TeamJoin,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/tournament/:id/update',
      name: 'update-tournament',
      component: UpdateTournament,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/tournament/:id/bracket',
      name: 'bracket',
      component: TournamentBracket,
      meta: {
        requiresAuth: false
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
