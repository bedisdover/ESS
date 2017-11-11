<template>
  <div class="main">
    <ul class="question-no">
      <li v-for="n in number">{{n}}</li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: 'AnswerSheet',
    props: ['number'],
    data () {
      return {
        active: 1
      }
    }
  }
</script>

<style scoped>
  .question-no {
    display: flex;
    list-style: none;
    padding: 0;
  }

  .question-no > li {
    width: 40px;
    height: 40px;
    margin: 0 5px;
    line-height: 40px;
    background: #409EFF;
  }
</style>
