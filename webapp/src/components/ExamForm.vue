<template>
  <div class="main">
    <el-form ref="examForm" :model="exam" labelWidth="80px">
      <el-form-item label="考试名称">
        <el-input v-model="exam.name"></el-input>
      </el-form-item>
      <el-form-item label="考试时间">
        <el-date-picker
          v-model="exam.time"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="参加人员">
        <el-input v-model="exam.num"></el-input>
      </el-form-item>
      <el-form-item label="试卷分数">
        <LevelScore :courseId="exam.courseId" :test.sync="exam.scores"></LevelScore>
      </el-form-item>
      <el-form-item class="footer">
        <el-button type="primary" @click="submit">立即创建</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import Util from '../lib/util'
  import LevelScore from './LevelScore'

  export default {
    name: 'ExamForm',
    props: ['exam', 'onConfirm', 'onCancel'],
    components: {LevelScore},
    data () {
      return {
        test: '1'
      }
    },
    methods: {
      submit: function () {
        this.exam.startTime = Util.formatTime(this.exam.time[0])
        this.exam.endTime = Util.formatTime(this.exam.time[1])

        this.$emit('onConfirm', this.exam)
      }
    }
  }
</script>

<style scoped>
  .main {
    text-align: left;
  }
</style>
