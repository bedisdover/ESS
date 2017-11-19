<template>
  <div>
    <el-row>
      <el-col :span="20" :offset="2">
        <el-card class="box-card clearfix">
          <div slot="header">
            <span style="margin-right: -205px">【{{courseName}}】学生名单</span>
            <el-button style="float: right;width: 100px" type="primary" size="small">
              下载学生模版
            </el-button>
            <el-button style="float: right;margin-right: 5px;width: 100px" type="primary" size="small"
                       @click="uploadDialogVisible = true">
              上传学生名单
            </el-button>
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
          <el-button class="deleteButton"
                     type="danger"
                     size="small"
                     @click="deleteStudents"
          >删除学生名单
          </el-button>
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
        action="/question/upload"
        :file-list="fileList">
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
        fileList: [],
        loading: true
      }
    },
    methods: {
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
</style>
