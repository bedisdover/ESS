<template>
  <div>
    <el-row>
      <el-col :span="20" :offset="2">
        <el-card class="box-card clearfix">
          <div slot="header">
            <el-tooltip content="删除选中学生名单" effect="light">
                        <span class="operation deleteIcon" @click="deleteStudents">
                          <svg class="icon " aria-hidden="true">
                            <use xlink:href="#icon-delete"></use>
                          </svg>
                        </span>
            </el-tooltip>
            <span style="margin-right: -72px" v-if="courseName != null">【{{courseName}}】学生名单</span>
            <span style="margin-right: -72px" v-else>学生名单</span>
            <a href="/student/download" target="_blank">
              <el-tooltip content="下载学生模版" effect="light">
                        <span class="operation">
                          <svg class="icon" aria-hidden="true">
                            <use xlink:href="#icon-clouddownload"></use>
                          </svg>
                        </span>
              </el-tooltip>
            </a>
            <el-tooltip content="上传学生名单" effect="light">
                        <span class="operation" @click="uploadDialogVisible = true">
                          <svg class="icon" aria-hidden="true">
                            <use xlink:href="#icon-cloudupload"></use>
                          </svg>
                        </span>
            </el-tooltip>
          </div>
          <el-table
            @selection-change="handleSelectionChange"
            v-loading="loading"
            :data="ExamStudentsData"
            style="width: 100%;text-align: left">
            <el-table-column
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column
              label="学生邮箱"
              prop="email"
            >
            </el-table-column>
            <el-table-column
              label="学生姓名"
              prop="name"
            >
            </el-table-column>
            <el-table-column
              label="学生所属班级"
              prop="cls"
            >
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog
      :close-on-click-modal=false
      title="上传学生名单"
      :visible.sync="uploadDialogVisible"
      width="60%"
    >
      <el-upload
        action="/student/upload"
        :with-credentials="true"
        :data="{courseId: parseInt(id)}"
        :on-success="uploadSuccess"
        :show-file-list="false"
        name="studentFile"
        accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">只能上传excel文件</div>
      </el-upload>
    </el-dialog>
  </div>
</template>
<script>
  import request from '../lib/request'
  import util from '../lib/util'

  export default {
    name: 'ExamStudents',
    props: ['id', 'courseName'],
    data () {
      return {
        ExamStudentsData: [],
        deleteStudentList: [],
        uploadDialogVisible: false,
        loading: true
      }
    },
    methods: {
      uploadSuccess (response) {
        if (response.success) {
          util.notifySuccess(response.message)
          this.uploadDialogVisible = false
          this.loadExamStudents()
        } else {
          util.notifyError(response.message)
        }
      },
      loadExamStudents () {
        this.loading = true
        let params = {
          courseId: parseInt(this.id)
        }
        request('/student/course/get', 'post', params, (success, message, data) => {
          if (success) {
            this.ExamStudentsData = data
            this.loading = false
          } else {
            util.notifyError(message)
          }
        })
      },
      handleSelectionChange (val) {
        this.deleteStudentList = val.map((obj) => {
          return obj.email
        })
      },
      deleteStudents () {
        if (this.deleteStudentList.length === 0) {
          return false
        }
        let params = {
          courseId: this.id,
          emails: this.deleteStudentList
        }
        request('/student/course/delete', 'post', params, (success, message) => {
          if (success) {
            this.deleteStudentList = []
            this.loadExamStudents()
          } else {
            util.notifyError(message)
          }
        })
      }
    },
    beforeMount: function () {
      this.loadExamStudents()
    }
  }
</script>
<style scoped>
  .deleteButton {
    margin-top: 20px;
    margin-bottom: 20px;
    float: left
  }
  .operation {
    font-size: 1.5em;
    float: right;
    margin-left: 24px;
  }
  .operation svg {
    cursor: pointer;
  }
  .deleteIcon {
    float: left;
  }
</style>
