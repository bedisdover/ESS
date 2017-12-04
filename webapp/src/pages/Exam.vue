<template>
  <div class="exam-main">
    <Paper
      v-if="paperVisible"
      :exam="exam"
      :questionList="questionList"
      @onEndExam="submitPaper">
    </Paper>
    <Countdown
      v-else
      :exam="exam"
      @onStartExam="loadPaper">
    </Countdown>
  </div>
</template>

<script>
  import Util from '../lib/util'
  import request from '../lib/request'
  import Countdown from '../components/Countdown'
  import Paper from '../components/paper/Paper'

  export default {
    name: 'Exam',

    props: ['examId', 'examKey'],

    components: {Countdown, Paper},

    data () {
      return {
        paperVisible: false,
        exam: {},
        questionList: []
      }
    },

    mounted () {
      let params = {
        examId: this.examId
      }

      request('/exam/query/simple', 'post', params, (success, message, data) => {
        if (success) {
          this.exam = data

          let time = Util.formatTime(new Date())
          if (time >= this.exam.startTime && time < this.exam.endTime) {
            const paper = Util.getCookie('paper')
            if (paper) {
              this.showPaper(paper)
            } else {
              this.loadPaper()
            }
          }
        }
      })
    },

    methods: {
      loadPaper: function () {
        let params = {
          key: this.examKey
        }

        request('/paper/create', 'post', params, (success, message, data) => {
          if (success) {
            Util.setCookie('paper', data)

            this.showPaper(data)
          } else {
            Util.notifyError(message)
          }
        })
      },
      submitPaper: function (questionList) {
        let params = {
          paper: {
            examId: this.exam.examId,
            answeredQuestions: questionList
          }
        }

        request('/paper/submit', 'post', JSON.stringify(params), (success, message) => {
          if (success) {
            Util.removeCookie('paper')

            this.hidePaper()
          } else {
            Util.notifyError(message)
          }
        })
      },
      showPaper: function (data) {
        this.questionList = data
        this.paperVisible = true
      },
      hidePaper: function () {
        this.questionList = null
        this.paperVisible = false
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
</style>
