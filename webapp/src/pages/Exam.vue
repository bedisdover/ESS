<template>
  <div class="exam-main">
    <el-card>
      <div slot="header">
        考试名称
      </div>
      <Question :question="question" :index="current" :editable="editable"></Question>
      <div class="button-container">
        <el-button type="error" @click="previous" v-show="current !== 0">上一题</el-button>
        <el-button type="primary" @click="next" v-show="!submitVisible">下一题</el-button>
        <el-button type="primary" @click="submit" v-show="submitVisible">交卷</el-button>
      </div>
      <hr/>
      <AnswerSheet :number="questionList.length" :current="current" @onJump="jumpTo"></AnswerSheet>
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
        current: 0,
        editable: false,
        questionList: [
          {
            content: '这是题目1\ntest',
            options: [
              '选项A',
              '选项B',
              '选项C',
              '选项D'
            ],
            answers: new Array(4).fill('')
          },
          {
            content: '这是题目2\ntest',
            options: [
              '选项A',
              '选项B',
              '选项C',
              '选项D'
            ],
            answers: new Array(4).fill('')
          },
          {
            content: '这是题目3\ntest',
            options: [
              '选项A',
              '选项B',
              '选项C',
              '选项D'
            ],
            answers: new Array(4).fill('')
          }
        ]
      }
    },

    computed: {
      question: function () {
        return this.questionList[this.current]
      },
      submitVisible: function () {
        return this.current === this.questionList.length - 1
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
      jumpTo: function (n) {
        this.current = n
      },
      previous: function () {
        this.current--
      },
      next: function () {
        this.current++
      },
      submit: function () {
        console.log(123)
      }
    }
  }
</script>

<style scoped>
  .exam-main {
    max-width: 1200px;
    min-width: 800px;
    margin: 20px auto 0;
  }

  .exam-main hr {
    border: dashed 1px #e6ebf5;
    margin: 20px -20px;
  }

  .button-container {
    text-align: right;
    margin-top: 30px;
    padding-right: 15px;
  }
</style>
