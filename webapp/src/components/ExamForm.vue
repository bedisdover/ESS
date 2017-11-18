<template>
  <div class="main">
    <el-form ref="examForm" :model="examInfo" :rules="rules" labelWidth="80px">
      <el-form-item label="考试名称" prop="name" required>
        <el-input v-model="examInfo.name" class="input"></el-input>
      </el-form-item>
      <el-form-item label="考试时间" prop="time" required>
        <el-date-picker
          v-model="examInfo.startTime"
          type="datetime"
          placeholder="开始时间">
        </el-date-picker>
        <span class="to">至</span>
        <el-date-picker
          v-model="examInfo.endTime"
          type="datetime"
          placeholder="结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="参加人员" prop="students" required>
        <StudentsList :courseId="courseId" :students="exam.studentInfoList"
                      v-on:onUpdateStudents="onUpdateStudents"></StudentsList>
      </el-form-item>
      <el-form-item label="试卷分数" prop="score" required>
        <LevelScore :maxNum="maxNum" :num="examInfo.num" :marks="examInfo.marks"
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
  import StudentsList from './StudentsList'
  import LevelScore from './LevelScore'

  export default {
    name: 'ExamForm',

    props: ['courseId', 'maxNum', 'exam', 'onConfirm', 'onCancel'],

    components: {StudentsList, LevelScore},

    data () {
      const validateTime = (rule, value, callback) => {
        if (this.examInfo.startTime < new Date()) {
          callback(new Error('开始时间不得早于当前时间'))
        } else if (this.examInfo.endTime <= this.examInfo.startTime) {
          callback(new Error('结束时间不得早于开始时间'))
        } else {
          callback()
        }
      }
      const validateStudents = (rule, value, callback) => {
        if (this.examInfo.students.length === 0) {
          callback(new Error('参加人员不能为空'))
        } else {
          callback()
        }
      }
      const validateScore = (rule, value, callback) => {
        if (this.examInfo.score === 0) {
          callback(new Error('试题分数不能为零'))
        } else {
          callback()
        }
      }

      return {
        examInfo: {
          name: this.exam.name ? this.exam.name : '',
          startTime: this.exam.startTime ? new Date(this.exam.startTime) : '',
          endTime: this.exam.endTime ? new Date(this.exam.endTime) : '',
          students: [],
          num: this.exam.num ? [...this.exam.num] : [],
          marks: this.exam.marks ? [...this.exam.marks] : [],
          score: 0
        },
        rules: {
          name: [
            {required: true, message: '考试名称不能为空'}
          ],
          time: [
            {required: true, validator: validateTime}
          ],
          students: [
            {required: true, validator: validateStudents, trigger: 'change'}
          ],
          score: [
            {required: true, validator: validateScore}
          ]
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
      submit: function () {
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
