<template>
  <div class="main">
    <el-row>
      <el-col :span="20" :offset="2">
        <el-card class="box-card">
          <div slot="header">
            <el-button type="text" class="btn-back" @click="hideExamForm" v-show="examFormVisible">&lt;&lt; 返回
            </el-button>
            <span>{{title}}</span>
            <el-button type="text" class="btn-create" @click="createExam" v-show="id && !examFormVisible">新建考试
            </el-button>
          </div>
          <el-table :data="examList" class="table" v-show="!examFormVisible" v-loading="loading">
            <el-table-column label="考试名称" prop="name"></el-table-column>
            <el-table-column label="开始时间" prop="startTime"></el-table-column>
            <el-table-column label="结束时间" prop="endTime"></el-table-column>
            <el-table-column label="考试人数" prop="studentNum"></el-table-column>
            <el-table-column label="试题数量" prop="questionNum"></el-table-column>
            <el-table-column label="总分" prop="score"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                  <span class="operation">
                    <el-tooltip content="考试详情" effect="light">
                        <span>
                          <svg class="icon" aria-hidden="true" @click="examInfo(scope.row)">
                            <use xlink:href="#icon-info"></use>
                          </svg>
                        </span>
                    </el-tooltip>
                    <el-tooltip content="考试分析" effect="light">
                        <router-link
                          :class="{invisible: editable(scope.row.endTime)}"
                          :to="'/examAnalysis/' + scope.row.examId">
                          <svg class="icon" aria-hidden="true">
                            <use xlink:href="#icon-analysis"></use>
                          </svg>
                        </router-link>
                    </el-tooltip>
                    <el-tooltip content="编辑考试" effect="light">
                        <span :class="{invisible: !editable(scope.row.endTime)}">
                          <svg class="icon" aria-hidden="true" @click="editExam(scope.row)">
                            <use xlink:href="#icon-edit"></use>
                          </svg>
                        </span>
                    </el-tooltip>
                    <el-tooltip content="删除考试" effect="light">
                        <span>
                          <svg class="icon" aria-hidden="true" @click="deleteExam(scope.$index, scope.row.examId)">
                            <use xlink:href="#icon-delete"></use>
                          </svg>
                        </span>
                    </el-tooltip>
                  </span>
              </template>
            </el-table-column>
          </el-table>
          <ExamForm :exam="exam" v-on:onConfirm="onConfirm" :onCancel="hideExamForm" v-if="examFormVisible"></ExamForm>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import request from '../lib/request'
  import Util from '../lib/util'
  import ExamForm from '../components/ExamForm'

  export default {
    name: 'ExamList',

    props: ['id', 'cls'],

    components: {ExamForm},

    data () {
      return {
        examFormVisible: false,
        examList: [],
        maxNum: [], // 各难度级别试题总数目
        exam: {},
        loading: true
      }
    },

    computed: {
      title: function () {
        return this.examFormVisible ? this.exam.examId ? this.exam.readonly ? '考试详情' : '编辑考试' : '新建考试' : '考试列表'
      }
    },

    mounted: function () {
      Util.loading()

      let url = this.id ? '/exam/list' : '/exam/all'
      let params = this.id ? {courseId: this.id} : ''

      request(url, 'post', params, (success, message, data) => {
        if (success) {
          this.initData(data)
          this.loading = false

          Util.closeLoading()
        }
      })
    },

    methods: {
      editable: function (endTime) {
        return Util.formatTime(new Date()) < endTime
      },
      initData: function (data) {
        if (data.examInfoList) { // 单独课程考试列表
          this.maxNum = data.maxNum

          this.examList = data.examInfoList.map(exam => {
            exam = Object.assign(exam, {courseId: this.id, maxNum: data.maxNum})

            return this.prepareExam(exam)
          })
        } else { // 所有课程列表
          this.examList = data.map(exam => this.prepareExam(exam))
        }
      },
      prepareExam: function (exam) {
        let temp = Object.assign({}, {courseId: this.id, maxNum: this.maxNum}, exam)
        let num = 0
        let score = 0
        for (let i = 0; i < exam.num.length; i++) {
          num += exam.num[i]
          score += exam.num[i] * exam.marks[i]
        }

        temp.score = isNaN(score) ? '--' : score
        temp.questionNum = num
        temp.studentNum = exam.students ? exam.students.length : exam.studentInfoList.length
        temp.students = exam.students ? exam.students : exam.studentInfoList

        return temp
      },
      hideExamForm: function () {
        this.examFormVisible = false
      },
      createExam: function () {
        // 新建考试时重置数据
        this.exam = {
          courseId: this.id,
          maxNum: this.maxNum
        }

        this.examFormVisible = true
      },
      examInfo: function (exam) {
        this.exam = Object.assign({}, exam, {readonly: true})

        this.examFormVisible = true
      },
      editExam: function (exam) {
        this.exam = exam

        this.examFormVisible = true
      },
      deleteExam: function (index, examId) {
        let params = {
          examId: examId
        }

        request('/exam/delete', 'post', params, (success, message) => {
          if (success) {
            this.examList.splice(index, 1)
          } else {
            Util.notifyError(message)
          }
        })
      },
      onConfirm: function (data) {
        let exam = this.prepareExam(data)

        if (exam.examId) { // 编辑考试
          this.examList.map((item, index) => {
            if (item.examId === exam.examId) {
              this.examList.splice(index, 1, exam)
            }
          })
        } else { // 新建考试
          this.examList.unshift(exam)
        }

        this.hideExamForm()
      }
    }
  }
</script>

<style scoped>
  .btn-back {
    float: left;
    padding: 3px 0;
  }

  .btn-create {
    float: right;
    padding: 3px 0;
  }

  .table {
    text-align: left;
  }

  .operation {
    margin-right: 15px;
    font-size: 1.2rem;
  }

  .operation .invisible {
    visibility: hidden;
  }
</style>
