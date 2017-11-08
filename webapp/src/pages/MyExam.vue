<template>
  <div class="main">
    <el-row>
      <el-col :span="20" :offset="2">
        <el-card class="box-card">
          <div slot="header">
            <el-button type="text" class="btn-back" @click="hideExamForm" v-show="examFormVisible">&lt;&lt; 返回
            </el-button>
            <span>{{getTitle()}}</span>
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
            <el-table-column label="考试人数" prop="students"></el-table-column>
            <el-table-column label="试题数量" prop="num"></el-table-column>
            <el-table-column label="总分" prop="scores"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                  <span class="operation">
                    <el-tooltip content="编辑考试" effect="light">
                        <svg class="icon" aria-hidden="true" @click="editExam(scope.$index, scope.row)">
                            <use xlink:href="#icon-edit"></use>
                        </svg>
                    </el-tooltip>
                    <el-tooltip content="删除考试" effect="light">
                        <svg class="icon" aria-hidden="true" @click="deleteExam(scope.$index)">
                            <use xlink:href="#icon-delete"></use>
                        </svg>
                    </el-tooltip>
                  </span>
              </template>
            </el-table-column>
          </el-table>
          <ExamForm :exam="exam" v-on:onConfirm="onConfirm" :onCancel="hideExamForm"
                    v-show="examFormVisible"></ExamForm>
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
    props: ['id'],
    components: {ExamInfo, ExamForm},
    data () {
      return {
        examFormVisible: false,
        examList: [
          {
            name: '考试测试',
            startTime: '2017-11-07 18:00',
            endTime: '2017-11-07 20:00',
            time: [new Date('2017-11-07 18:00'), new Date('2017-11-07 20:00')],
            students: 0,
            num: 10,
            scores: 100
          },
          {
            name: '考试测试1',
            time: [new Date('2017-11-07 18:00'), new Date('2017-11-07 20:00')],
            startTime: '2017-11-07 18:00',
            endTime: '2017-11-07 20:00',
            students: 0,
            num: 10,
            scores: 100
          }
        ],
        exam: {
          courseId: this.id
        },
        index: -1
      }
    },
    created: function () {
      let params = {
        courseId: this.id
      }
      request('/exam/list', 'post', params, function (success, message, data) {
        if (success) {
          this.examList = data
        }
      })
    },
    methods: {
      getTitle: function () {
        return this.examFormVisible ? this.index === -1 ? '新建考试' : '编辑考试' : '考试列表'
      },
      hideExamForm: function () {
        this.examFormVisible = false
      },
      createExam: function () {
        // 新建考试时重置数据
        this.exam = {courseId: this.courseId}
        this.index = -1

        this.examFormVisible = true
      },
      editExam: function (index, exam) {
        this.exam = exam
        this.index = index

        this.examFormVisible = true
      },
      deleteExam: function (index) {
      },
      onConfirm: function (exam) {
        if (this.index === -1) { // 新建考试

        } else { // 编辑考试
          this.examList[this.index] = exam
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
