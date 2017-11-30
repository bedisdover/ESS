<template>
  <div class="exam-main">
    <component :is="currentView" :exam="exam" :questionList="questionList" @onStartExam="loadPaper"></component>
  </div>
</template>

<script>
  import Util from '../lib/util'
  import request from '../lib/request'
  import Countdown from '../components/Countdown'
  import Paper from '../components/paper/Paper'

  export default {
    name: 'Exam',

    components: {Countdown, Paper},

    data () {
      return {
        currentView: 'Countdown',
        exam: {},
        questionList: []
      }
    },

    mounted () {
      let params = {
        examId: this.$route.query.examId
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
          examId: this.$route.query.examId,
          email: this.$route.query.email,
          password: this.$route.query.password
        }

        request('/paper/create', 'post', params, (success, message, data) => {
          if (success) {
            Util.setCookie('paper', data)

            this.showPaper(data)
          }
        })
      },
      showPaper: function (data) {
        this.questionList = data
        this.currentView = 'Paper'
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
