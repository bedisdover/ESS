<template>
  <div class="main">
    <div class="content">
      <strong class="index">{{index}}.</strong>
      <pre>{{question.content}}</pre>
    </div>
    <div class="options" v-for="(option, index) in question.options" :key="option">
      <el-checkbox v-model="checked[index]" :label="option" border></el-checkbox>
    </div>
    <div class="button-container">
      <el-button type="error" @click="submit">交卷</el-button>
      <el-button type="primary" @click="next">下一题</el-button>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Question',
    props: ['question', 'index'],
    data () {
      let optionNum = this.question.options.length

      let checked = new Array(optionNum).fill(false)

      return {
        checked: checked
      }
    },
    methods: {
      getAnswer: function () {
        let answer = []

        this.checked.map((item, index) => {
          if (item) {
            answer.push(index + 1)
          }
        })

        return answer.join(',')
      },
      submit: function () {
        this.$emit('onSubmit', this.index, this.getAnswer())
      },
      next: function () {
        this.$emit('onNext', this.index, this.getAnswer())
      }
    }
  }
</script>

<style scoped>
  .main {
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

  .button-container {
    text-align: right;
    margin-top: 30px;
    padding-right: 15px;
  }
</style>
