<template>
  <div>
    <div class="tournament-brackets">
        <div class="bracket">
            
            <template v-for="(round, index) in rounds">
                <div class="round" :key="index" :class="['round-' + round]">
                    <template v-for="(match, matchIndex) in round">
                      
                        <div class="match" :key="matchIndex">
                          <router-link :to="{name : 'team-details', params : {id : competitorList[matchIndex].teamId}}" class='team-link'>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 0 && round == 8'>{{competitorList[matchIndex].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 1 && round == 8'>{{competitorList[matchIndex].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 2 && round == 8'>{{competitorList[matchIndex].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 3 && round == 8'>{{competitorList[matchIndex].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 4 && round == 8'>{{competitorList[matchIndex].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 5 && round == 8'>{{competitorList[matchIndex].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 6 && round == 8'>{{competitorList[matchIndex].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 7 && round == 8'>{{competitorList[matchIndex].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 0 && round == 4'>{{competitorList[matchIndex].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 1 && round == 4'>{{competitorList[3].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 2 && round == 4'>{{competitorList[5].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 3 && round == 4'>{{competitorList[6].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 0 && round == 2'>{{competitorList[0].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 1 && round == 2'>{{competitorList[6].teamName}}</div>
                            <div class="match__content" :style="matchStyle" v-if='matchIndex == 0 && round == 1'>{{competitorList[6].teamName}}</div>
                          </router-link>
                          </div>
                    </template>
                </div>
            </template>
        </div>
    </div>
    <tournament-details/>
      </div>
</template>

<script>
import TournamentService from "../services/TournamentService.js";
import TournamentDetails from "../components/TournamentDetails.vue"

export default {
  components : {
    TournamentDetails
  },
  props: {
    bracketSize: {  
      type: Number,
        default: 8
      },
      matchStyle: {
        type: Object,
        default: () => ({
          border: '2px solid gray',
          width: '100%',
          height: '30px',
          position: 'relative'
        })
      }
    },
  data() {
    return {
      allTournaments: [],
      currentTournament: {},
      activePlayers: [],
      numberOfRounds: 0,
      competitorList : [],
      teamCount : 2,
      defaultRounds : [],
      count : 1,
    };
  },
  created() {
    TournamentService.getAllTournaments().then((tournamentData) => {
      this.allTournaments = tournamentData.data;
      this.currentTournament = this.allTournaments.find(
        (tournament) => tournament.tournamentId == this.$route.params.id
      );
      this.activePlayers = this.currentTournament.players;
       if(this.currentTournament.teamTournament){
        this.competitorList = this.currentTournament.teams;
          // this.defaultRounds = 4;
            for(let i = this.currentTournament.playerCount; i > 1; i /=2){
              this.defaultRounds.push(i);
            }
            this.defaultRounds.push(1);
          } else {
            this.competitorList = this.currentTournament.players;
            for(let i = this.currentTournament.playerCount; i > 1; i /= 2){
              this.defaultRounds.push(i);
            }

            for(let i = this.currentTournament.playerCount; i > 1; i /=2){
              this.defaultRounds.push(i);
            }
            this.defaultRounds.push(1);
            
          }
      for (let tracker = this.currentTournament.playerCount; tracker !== 1; tracker /= 2) {
          this.numberOfRounds++;
      }
    });
  },
  computed: {
      seeds() {
          let seeds = [];
          for (let i = 0; i < this.activePlayers.length; i += 2) {
              seeds.push(this.activePlayers.slice(i, i + 2));
          }
          return seeds;
      },
      rounds () {
        return this.defaultRounds.filter(rounds => rounds <= this.bracketSize)
      },
      matchCompetitor(){
        return this.competitorList[this.count];
      }
  },
  methods: {
      markWinner(player) {  
          let index = this.activePlayers.indexOf(player)
          this.activePlayers.splice(index, 1);
      },
      getWinningCompetitor(){
        this.count += 1;
        if(this.count > 3){
          this.count = 2;
        }
      }
  }
};
</script>

<style scoped>
.round {
    display: inline;
}
    *,
    *::after,
    *::before {
        box-sizing: border-box;
    }
    .bracket {
        display: flex;
    }
    .round {
        display: flex;
        flex-grow: 1;
        flex-direction: column;
    }
    .round:first-child .match::before {
        display: none;
    }
    .round:first-child .match__content::before {
        display: none;
    }
    .round:last-child .match::after {
        display: none;
    }
    .match {
        display: flex;
        flex-direction: column;
        justify-content: center;
        margin: 0 10px;
        padding: 6px 0;
        flex-grow: 1;
        position: relative;
    }
    .match::before {
        content: "";
        display: block;
        min-height: 30px;
        border-left: 2px solid #fca311;
        position: absolute;
        left: -10px;
        top: 50%;
        margin-top: -15px;
        margin-left: -2px;
    }
    .match:nth-child(odd)::after {
        content: "";
        display: block;
        border: 2px solid transparent;
        border-top-color: #fca311;
        border-right-color: #fca311;
        height: 50%;
        position: absolute;
        right: -10px;
        width: 12px;
        top: 50%;
    }
    .match:nth-child(even)::after {
        content: "";
        display: block;
        border: 2px solid transparent;
        border-bottom-color: #fca311;
        border-right-color: #fca311;
        height: 50%;
        position: absolute;
        right: -10px;
        width: 12px;
        bottom: 50%;
    }
    .match__content:hover{
      background-color: rgba(224, 255, 255, .7);
    }
    .team-link{
      text-decoration: none;
    }
    .match__content::before {
        content: "";
        display: block;
        width: 10px;
        border-bottom: 2px solid #fca311;
        margin-left: -2px;
        position: absolute;
        top: 50%;
        left: -10px;
    }
</style>

