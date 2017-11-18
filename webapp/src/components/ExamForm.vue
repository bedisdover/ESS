<template>
  <div class="main">
    <el-form ref="examForm" :model="examInfo" labelWidth="80px">
      <el-form-item label="考试名称" required>
        <el-input v-model="examInfo.name" class="input" :readonly="exam.readonly"></el-input>
      </el-form-item>
      <el-form-item label="考试时间" required>
        <el-date-picker
          v-model="examInfo.startTime"
          :readonly="exam.readonly"
          type="datetime"
          placeholder="开始时间">
        </el-date-picker>
        <span class="to">至</span>
        <el-date-picker
          v-model="examInfo.endTime"
          :readonly="exam.readonly"
          type="datetime"
          placeholder="结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="参加人员" required>
        <StudentsList :courseId="courseId" :students="exam.studentInfoList" :readonly="exam.readonly"
                      v-on:onUpdateStudents="onUpdateStudents"></StudentsList>
      </el-form-item>
      <el-form-item label="试卷分数" required>
        <LevelScore :maxNum="maxNum" :num="examInfo.num" :marks="examInfo.marks" :readonly="exam.readonly"
                    v-on:onUpdateScore="onUpdateScore"></LevelScore>
      </el-form-item>
      <el-form-item class="footer" v-if="!exam.readonly">
        <el-button type="primary" @click="submit">{{exam.examId ? '确认修改' : '立即创建'}}</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import request from '../lib/request'
  import Util from '../lib/util'
  import StudentsList from './StudentsList'
  import LevelScore from './LevelScore'

  export default {
    name: 'ExamForm',

    props: ['courseId', 'maxNum', 'exam', 'onConfirm', 'onCancel'],

    components: {StudentsList, LevelScore},

    data () {
      return {
        examInfo: {
          name: this.exam.name ? this.exam.name : '',
          startTime: this.exam.startTime ? new Date(this.exam.startTime) : '',
          endTime: this.exam.endTime ? new Date(this.exam.endTime) : '',
          students: this.exam.studentInfoList ? this.exam.studentInfoList : [],
          num: this.exam.num ? [...this.exam.num] : [],
          marks: this.exam.marks ? [...this.exam.marks] : [],
          score: 0
        }
      }
    },

    methods: {
      onUpdateStudents: function (students) {
        this.examInfo.students = students
      },
      onUpdateScore: function (num, marks, score) {
        this.examInfo.num = num
        this.examInfo.marks = marks
        this.examInfo.score = score
      },
      validate: function () {
        if (this.examInfo.name === '') {
          Util.notifyError('考试名称不能为空')
          return false
        }

        if (this.examInfo.startTime < new Date()) {
          Util.notifyError('开始时间不得早于当前时间')
          return false
        } else if (this.examInfo.endTime <= this.examInfo.startTime) {
          Util.notifyError('结束时间不得早于开始时间')
          return false
        }

        if (this.examInfo.students.length === 0) {
          Util.notifyError('参加人员不能为空')
          return false
        }

        if (this.examInfo.score === 0) {
          Util.notifyError('试题分数不能为零')
          return false
        }

        return true
      },
      submit: function () {
        if (!this.validate()) {
          return
        }

        let params = {
          courseId: this.courseId,
          examId: this.exam.examId,
          name: this.examInfo.name,
          startTime: Util.formatTime(this.examInfo.startTime),
          endTime: Util.formatTime(this.examInfo.endTime),
          num: this.examInfo.num,
          marks: this.examInfo.marks,
          students: this.examInfo.students
        }

        let url = '/exam' + (params.examId ? '/update' : '/add')
        request(url, 'post', JSON.stringify(params), (success, message) => {
          if (success) {
            this.$emit('onConfirm', params)
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

  .input {
    width: 500px;
  }

  .to {
    margin: 0 20px;
  }

  .footer {
    margin-top: 40px;
  }
</style>
