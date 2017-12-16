<template>
  <el-card class="paper">
    <div slot="header" class="header">
      <el-progress :percentage="percentage" :stroke-width="15" :show-text="false"></el-progress>
      <span class="percentage-text">{{percentageText}}</span>
      <span class="countdown">{{countdown}}</span>
    </div>
    <Question :question="question" :index="current" @onChange="updateAnswer"></Question>
    <div class="button-container">
      <el-button type="error" @click="previous" v-show="current !== 0">上一题</el-button>
      <el-button type="primary" @click="next" v-show="!submitVisible">下一题</el-button>
      <el-button type="primary" @click="submit" v-show="submitVisible">交卷</el-button>
    </div>
    <hr/>
    <AnswerSheet :number="questionList.length" :current="current" @onJump="jumpTo"></AnswerSheet>
  </el-card>
</template>

<script>
  import Util from '../../lib/util'
  import Question from './Question'
  import AnswerSheet from './AnswerSheet'

  export default {
    name: 'Paper',

    props: {
      exam: Object,
      questionList: Array
    },

    components: {Question, AnswerSheet},

    data () {
      return {
        current: 0,
        endTime: new Date(this.exam.endTime),
        countdown: '00:00:00',
        timeout: null,
        answerList: this.questionList.map((question) => {
          return {
            question: {
              questionId: question.questionId
            }
          }
        })
      }
    },

    computed: {
      question: function () {
        return this.questionList[this.current]
      },
      submitVisible: function () {
        return this.current === this.questionList.length - 1
      },
      percentage: function () {
        return (this.current + 1) / this.questionList.length * 100
      },
      percentageText: function () {
        return (this.current + 1) + '/' + this.questionList.length
      }
    },

    created () {
      this.timeout = setInterval(() => {
        this.getCountdown()
      }, 1000)
    },

    methods: {
      getCountdown: function () {
        let countdown = Util.countdown(this.endTime)

        if (countdown) {
          this.countdown = countdown
        } else {
          this.submit()
        }
      },
      updateAnswer: function (answer) {
        this.questionList[this.current].answer = answer
        this.answerList[this.current].answer = answer

        localStorage.setItem('paper', JSON.stringify(this.questionList))
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
        this.$emit('onEndExam', this.answerList)

        clearTimeout(this.timeout)
      }
    },

    destroyed () {
      clearTimeout(this.timeout)
    }
  }
</script>

<style scoped>
  .paper {
    position: relative;
  }

  .header {
    height: 55px;
    line-height: 55px;
    margin: -20px;
    background: #B4BCCC;
    font-size: 1.5em;
    color: white;
    vertical-align: middle;
  }

  .header > div:first-child {
    display: inline-block;
    float: left;
    width: 800px;
    padding: 20px 0 20px 30px;
  }

  .percentage-text {
    float: left;
    margin-left: 20px;
  }

  .countdown {
    float: right;
    padding: 0 30px;
    background: #878D99;
  }

  hr {
    border: dashed 1px #e6ebf5;
    margin: 20px -20px;
  }

  .button-container {
    text-align: right;
    margin-top: 30px;
    padding-right: 15px;
  }
</style>
