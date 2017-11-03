<template>
  <div class="main">
    <el-card>
      <div slot="header">
        考试名称
      </div>
      <Question :question="question" :index="1" @onSubmit="onSubmit" @onNext="onNext"></Question>
      <hr/>
      <AnswerSheet :number="10"></AnswerSheet>
    </el-card>
  </div>
</template>

<script>
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
    methods: {
      getAnswer: function () {
        return this.answers.join(';')
      },
      onSubmit: function (index, answer) {

      },
      onNext: function (index, answer) {
        this.answers[index] = answer
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
</style>
