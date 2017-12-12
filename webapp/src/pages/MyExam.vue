<template>
  <div class="main">
    <ExamList v-if="role === 1"></ExamList>
    <PaperList v-else-if="role === 2"></PaperList>
    <div v-else>You Will Never See This Page...</div>
  </div>
</template>

<script>
  import Util from '../lib/util'
  import ExamList from './ExamList'
  import PaperList from '../components/exam/PaperList'

  export default {
    name: 'MyExam',

    components: {ExamList, PaperList},

    data () {
      const role = Util.getCookie('user').role

      return {
        role: role
      }
    }
  }
</script>
