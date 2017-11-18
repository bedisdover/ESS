<template>
  <div class="main">
    <el-row>
      <el-col :span="20" :offset="2">
        <el-card class="box-card">
          <div slot="header">
            <el-button type="text" class="btn-back" @click="hideExamForm" v-show="examFormVisible">&lt;&lt; 返回
            </el-button>
            <span>{{title}}</span>
            <el-button type="text" class="btn-create" @click="createExam" v-show="!examFormVisible">新建考试</el-button>
          </div>
          <el-table :data="examList" class="table" v-show="!examFormVisible">
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
                    <el-tooltip v-if="editable(scope.row.endTime)" content="编辑考试" effect="light">
                        <span>
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
          <ExamForm :courseId="id" :maxNum="maxNum" :exam="exam" v-on:onConfirm="onConfirm"
                    :onCancel="hideExamForm" v-if="examFormVisible"></ExamForm>
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
        // 各难度级别试题数目
        maxNum: [],
        exam: {}
      }
    },

    computed: {
      title: function () {
        return this.examFormVisible ? this.exam.examId ? this.exam.readonly ? '考试详情' : '编辑考试' : '新建考试' : '考试列表'
      }
    },

    mounted: function () {
      let params = {
        courseId: this.id
      }
      request('/exam/list', 'post', params, (success, message, data) => {
        if (success) {
          this.initData(data)
        }
      })
    },

    methods: {
      editable: function (endTime) {
        return Util.formatTime(new Date()) < endTime
      },
      initData: function (data) {
        this.maxNum = data.maxNum
        data.examInfoList.forEach(exam => {
          this.examList.push(this.prepareExam(exam))
        })
      },
      prepareExam: function (exam) {
        let temp = Object.assign({}, exam)
        let num = 0
        let mark = 0
        for (let i = 0; i < exam.num.length; i++) {
          num += exam.num[i]
          mark += exam.num[i] * exam.marks[i]
        }

        temp.score = mark
        temp.questionNum = num
        temp.studentNum = exam.students ? exam.students.length : exam.studentInfoList.length

        return temp
      },
      hideExamForm: function () {
        this.examFormVisible = false
      },
      createExam: function () {
        // 新建考试时重置数据
        this.exam = {}

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
    font-size: 1.5em;
  }

  .operation svg {
    cursor: pointer;
  }
</style>
