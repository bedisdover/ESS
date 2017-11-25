<template>
  <div class="main">
    <ul class="question-no">
      <li v-for="n in number" :class="{visited: visited.indexOf(n) !== -1, active: active === n}" @click="jump(n)">
        {{n}}
      </li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: 'AnswerSheet',

    props: ['number'],

    data () {
      return {
        active: 1,
        visited: []
      }
    },

    methods: {
      jump: function (n) {
        this.visited.push(this.active)
        this.active = n
      }
    }
  }
</script>

<style scoped>
  .question-no {
    display: flex;
    flex-wrap: wrap;
    list-style: none;
    padding: 0;
  }

  .question-no > li {
    width: 40px;
    height: 40px;
    margin: 5px;
    line-height: 40px;
    cursor: pointer;
    background: #eeeeee;
    border-radius: 4px;
  }

  .question-no > li.visited {
    color: white;
    background: #82bcf9;
  }

  .question-no > li.active {
    margin: 0 5px;
    width: 50px;
    height: 50px;
    line-height: 50px;
    color: white;
    background: #409EFF;
  }
</style>
