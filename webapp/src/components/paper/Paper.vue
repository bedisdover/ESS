<template>
  <el-card>
    <div slot="header">
      {{exam.name}}
    </div>
    <Question :question="question" :index="current" :editable="editable" @onChange="updateAnswer"></Question>
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
        editable: true
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

    methods: {
      updateAnswer: function (answer) {
        this.questionList[this.current].answer = answer

        Util.setCookie('paper', this.questionList)
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
        this.$emit('onEndExam', this.questionList)
      }
    }
  }
</script>

<style scoped>
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
