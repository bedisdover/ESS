<template>
  <div class="main">
    <el-card>
      <div slot="header">
        考试名称
      </div>
      <Question :question="question" :index="1"></Question>
      <div class="button-container">
        <el-button type="error" @click="submit">交卷</el-button>
        <el-button type="primary" @click="next">下一题</el-button>
      </div>
      <hr/>
      <AnswerSheet :number="10"></AnswerSheet>
    </el-card>
  </div>
</template>

<script>
  import request from '../lib/request'
  import Question from '../components/Question'
  import AnswerSheet from '../components/AnswerSheet'

  export default {
    name: 'Exam',

    components: {Question, AnswerSheet},

    data () {
      return {
        question: {
          content: '这是题目\ntest',
          options: [
            '选项A',
            '选项B',
            '选项C',
            '选项D'
          ],
          answers: new Array(4).fill('')
        }
      }
    },

    created () {
      let params = {
        examId: this.$route.query.examId,
        email: this.$route.query.email,
        password: this.$route.query.password
      }

      request('/paper/create', 'post', params, function (success, message, data) {
        if (success) {
          console.log(message, data)
        }
      })
    },

    methods: {
      getAnswer: function () {
        return this.answers.join(';')
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
    max-width: 1200px;
    min-width: 800px;
    margin: 20px auto 0;
  }

  .main hr {
    border: dashed 1px #e6ebf5;
    margin: 20px -20px;
  }

  .button-container {
    text-align: right;
    margin-top: 30px;
    padding-right: 15px;
  }
</style>
