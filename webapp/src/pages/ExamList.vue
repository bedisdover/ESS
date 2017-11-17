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
            <el-table-column type="expand">
              <template slot-scope="props">
                <ExamInfo :examList="props"></ExamInfo>
              </template>
            </el-table-column>
            <el-table-column label="考试名称" prop="name"></el-table-column>
            <el-table-column label="开始时间" prop="startTime"></el-table-column>
            <el-table-column label="结束时间" prop="endTime"></el-table-column>
            <el-table-column label="考试人数" prop="studentNum"></el-table-column>
            <el-table-column label="试题数量" prop="questionNum"></el-table-column>
            <el-table-column label="总分" prop="score"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                  <span class="operation">
                    <el-tooltip content="编辑考试" effect="light">
                        <span>
                          <svg class="icon" aria-hidden="true" @click="editExam(scope.row)">
                            <use xlink:href="#icon-edit"></use>
                          </svg>
                        </span>
                    </el-tooltip>
                    <el-tooltip content="删除考试" effect="light">
                        <span>
                          <svg class="icon" aria-hidden="true" @click="deleteExam(scope.$index)">
                            <use xlink:href="#icon-delete"></use>
                          </svg>
                        </span>
                    </el-tooltip>
                  </span>
              </template>
            </el-table-column>
          </el-table>
          <ExamForm :courseId="id" :maxNum="maxNum" :exam="exam" :clsList="cls" :students="students" v-on:onConfirm="onConfirm"
                    :onCancel="hideExamForm" v-if="examFormVisible"></ExamForm>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import request from '../lib/request'
  import ExamInfo from '../components/ExamInfo'
  import ExamForm from '../components/ExamForm'

  export default {
    name: 'ExamList',
    props: ['id', 'cls'],
    components: {ExamInfo, ExamForm},
    data () {
      return {
        examFormVisible: false,
        examList: [],
        // 各难度级别试题数目
        maxNum: [],
        // 课程学生列表
        students: [],
        exam: {}
      }
    },
    computed: {
      title: function () {
        return this.examFormVisible ? (this.exam ? '新建考试' : '编辑考试') : '考试列表'
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
      request('/student/exam', 'post', params, (success, message, data) => {
        if (success) {
          this.students = data
        }
      })
    },
    methods: {
      initData: function (data) {
        this.maxNum = data.maxNum
        data.examInfoList.forEach(exam => {
          let temp = Object.assign({}, exam)
          let num = 0
          let mark = 0
          for (let i = 0; i < exam.num.length; i++) {
            num += exam.num[i]
            mark += exam.num[i] * exam.marks[i]
          }

          temp.score = mark
          temp.questionNum = num
          temp.studentNum = exam.studentInfoList.length

          this.examList.push(temp)
        })
      },
      hideExamForm: function () {
        this.examFormVisible = false
      },
      createExam: function () {
        // 新建考试时重置数据
        this.exam = {}

        this.examFormVisible = true
      },
      editExam: function (exam) {
        this.exam = exam

        this.examFormVisible = true
      },
      deleteExam: function (index) {
        console.log(index)
      },
      onConfirm: function (exam) {
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
