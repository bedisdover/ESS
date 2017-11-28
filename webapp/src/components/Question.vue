<template>
  <div class="question-main">
    <div class="content">
      <strong class="index">{{index + 1}}.</strong>
      <pre>{{question.content}}</pre>
    </div>
    <div class="options" v-for="(option, index) in question.options" :key="option">
      <el-checkbox :label="option" border @change="onChange(index)">{{option}}</el-checkbox>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Question',

    props: ['question', 'index'],

    data () {
      return {
        checked: this.question.answers
      }
    },

    methods: {
      onChange: function (index) {
        console.log(this.checked)
      }
    }
  }
</script>

<style scoped>
  .question-main {
    margin-top: 10px;
    text-align: left;
  }

  .content {
    font-size: 1.2em;
    margin-bottom: 20px;
    display: flex;
  }

  .content > pre {
    flex: 1;
  }

  .index {
    color: #409EFF;
    margin-right: 15px;
  }

  .options > label {
    display: block;
    padding: 10px;
    margin: 15px;
  }
</style>
