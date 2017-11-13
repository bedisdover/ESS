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
        <el-input></el-input>
      </el-form-item>
      <el-form-item label="试卷分数">
        <LevelScore :maxNum="maxNum" :num="exam.num" :marks="exam.marks"
                    v-on:onUpdateScore="onUpdateScore"></LevelScore>
      </el-form-item>
      <el-form-item class="footer">
        <el-button type="primary" @click="submit">{{exam ? '确认修改' : '立即创建'}}</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import request from '../lib/request'
  import Util from '../lib/util'
  import LevelScore from './LevelScore'

  export default {
    name: 'ExamForm',
    props: ['courseId', 'maxNum', 'exam', 'onConfirm', 'onCancel'],
    components: {LevelScore},
    data () {
      return {
        test: '1'
      }
    },
    methods: {
      onUpdateScore: function (num, marks) {
        this.exam.num = num
        this.exam.marks = marks
      },
      submit: function () {
//        this.exam.startTime = Util.formatTime(this.exam.time[0])
//        this.exam.endTime = Util.formatTime(this.exam.time[1])
        let params = {
          courseId: this.courseId,
          examId: this.exam.examId,
          name: this.exam.name,
          num: this.exam.num,
          marks: this.exam.marks
        }

        let url = '/exam' + (params.examId ? '/update' : '/add')
        request(url, 'post', JSON.stringify(params), (success, message) => {
          if (success) {
            this.$emit('onConfirm', this.exam)
          } else {
            Util.notifyError(message)
          }
        })
      }
    }
  }
</script>

<style scoped>
  .main {
    text-align: left;
  }
</style>
