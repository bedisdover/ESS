<template>
  <div class="paper">
    <el-card>
      <div slot="header" class="header">
        得分: {{mark}} / {{sum}}
      </div>
      <div class="question">
        <div class="content">
          <strong class="index">{{current + 1}}.</strong>
          <pre>{{question.content}}</pre>
        </div>
        <hr/>
        <div class="options">
          <div class="answer">
            正确答案: {{rightAnswerText()}}
            &nbsp;&nbsp;
            已选答案: {{checkedAnswerText()}}
            <span class="label-success" v-if="isRight()">
              (正确)
            </span>
            <span class="label-error" v-else>
              (错误)
            </span>
          </div>
          <label
            v-for="option in question.options"
            :key="option.optionId"
            :class="getLabelClass(option.optionId)">
            <span>{{option.content}}</span>
          </label>
        </div>
      </div>
      <div class="button-container">
        <el-button type="error" @click="previous" v-show="current !== 0">上一题</el-button>
        <el-button type="primary" @click="next" v-show="current !== questionList.length - 1">下一题</el-button>
      </div>
      <hr/>
      <AnswerSheet :number="questionList.length" :current="current" @onJump="jumpTo"></AnswerSheet>
    </el-card>
  </div>
</template>

<script>
  import request from '../lib/request'
  import Util from '../lib/util'
  import AnswerSheet from '../components/exam/AnswerSheet'

  export default {
    name: 'Paper',

    props: ['examId', 'email'],

    components: {AnswerSheet},

    data () {
      return {
        current: 0,
        sum: 0,
        mark: 0,
        checkedAnswer: [], // 已选答案
        answer: [], // 正确答案
        question: {},
        questionList: []
      }
    },

    mounted () {
      let url = '/exam/student/review'
      let params = {
        examId: this.examId
      }

      if (this.email) {
        url = '/exam/teacher/review'
        params.email = this.email
      }

      request(url, 'post', params, (success, message, data) => {
        if (success) {
          this.initData(data)
        } else {
          Util.notifyError(message)
        }
      })
    },

    watch: {
      questionList: function () {
        this.question = this.questionList[this.current].question
        this.answer = this.question.answer.split(',').map(item => parseInt(item))
        this.checkedAnswer = this.questionList[this.current].answer
      },
      current: function () {
        this.question = this.questionList[this.current].question
        this.answer = this.question.answer.split(',').map(item => parseInt(item))
        this.checkedAnswer = this.questionList[this.current].answer
      }
    },

    methods: {
      initData: function (data) {
        this.sum = data.sum
        this.mark = data.mark
        this.questionList = data.answeredQuestions
      },
      rightAnswerText: function () {
        return this.answer.map(item => String.fromCharCode(item + 64)).join('、')
      },
      checkedAnswerText: function () {
        return this.checkedAnswer.map(item => String.fromCharCode(item + 64)).join('、')
      },
      isRight: function () {
        return JSON.stringify(this.answer) === JSON.stringify(this.checkedAnswer)
      },
      getLabelClass: function (optionId) {
        return {
          // 已选但错误
          error: this.checkedAnswer.indexOf(optionId) !== -1 && this.answer.indexOf(optionId) === -1,
          // 正确答案
          right: this.answer.indexOf(optionId) !== -1
        }
      },
      jumpTo: function (n) {
        this.current = n
      },
      previous: function () {
        this.current--
      },
      next: function () {
        this.current++
      }
    }
  }
</script>

<style scoped>
  .paper {
    max-width: 1200px;
    min-width: 800px;
    margin: 20px auto 0;
  }

  .question {
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

  .answer {
    margin: 15px;
  }

  .answer .label-success {
    color: #67C23A;
  }

  .answer .label-error {
    color: #FA5555;
  }

  .options label {
    display: block;
    padding: 10px;
    margin: 15px;
    border: 1px solid #d8dce5;
    border-radius: 4px;
  }

  .options label.error {
    border-color: #FA5555;
  }

  .options label.right {
    border-color: #409EFF;
  }

  .button-container {
    text-align: right;
    margin-top: 30px;
    padding-right: 15px;
  }

  hr {
    border: dashed 1px #e6ebf5;
    margin: 20px -20px;
  }
</style>
