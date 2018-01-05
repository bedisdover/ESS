<template>
  <div class="answer-sheet-main">
    <ul class="question-no">
      <li v-for="n in number" :class="{visited: visited.indexOf(n) !== -1, active: active === n}"
          @click="jump(n)">
        {{n}}
      </li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: 'AnswerSheet',

    props: ['number', 'current'],

    data () {
      return {
        visited: []
      }
    },

    computed: {
      active: function () {
        return (this.current + 1) || 1
      }
    },

    methods: {
      jump: function (n) {
        this.visited.push(this.active)
        this.active = n

        this.$emit('onJump', n - 1)
      }
    }
  }
</script>

<style scoped>
  .question-no {
    display: flex;
    flex-wrap: wrap;
    list-style: none;
    margin: 0;
    padding: 0;
  }

  .question-no > li {
    width: 40px;
    height: 40px;
    margin: 0 5px;
    line-height: 40px;
    cursor: pointer;
    background: #eeeeee;
    border-radius: 4px;
  }

  .question-no > li.visited {
    color: white;
    background: #a4dbf9;
  }

  .question-no > li.active {
    color: white;
    background: #409EFF;
  }
</style>
