<template>
  <div >
    <el-row>
      <el-col :span="20"  :offset="2">
        <el-card class="box-card clearfix">
          <div slot="header">
            <span>我的课程</span>
            <el-button v-if="user.role === 1" style="float: right; padding: 3px 0" type="text">
              <router-link to="createCourse">创建课程</router-link>
            </el-button>
          </div>
          <el-table
            :data="myListData"
            style="width: 100%;text-align: left">
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="left" inline class="demo-table-expand">
                  <el-form-item label="课程名称：">
                    <span>{{ props.row.name }}</span>
                  </el-form-item>
                  <el-form-item label="年份：">
                    <span>{{ props.row.year }}</span>
                  </el-form-item>
                  <el-form-item label="年级：">
                    <span>{{ props.row.grade }}</span>
                  </el-form-item>
                  <el-form-item label="学期：">
                    <span>{{ props.row.term }}</span>
                  </el-form-item>
                  <el-form-item label="班级：">
                    <span>{{ props.row.cls }}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column
              label="课程名称"
              prop="name"
            >
            </el-table-column>
            <el-table-column
              label="年级"
              prop="year"
            >
            </el-table-column>
            <el-table-column
              label="年级"
              prop="grade"
            >
            </el-table-column>
            <el-table-column
              label="学期"
              prop="term"
            >
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="editCourseHandle(scope.row)" v-if="user.role === 1">编辑课程</el-button>
                <el-button type="danger" size="mini" @click="dialogHandle(scope.row.name, scope.row.id)" v-if="user.role === 2">退出课程</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog
      :close-on-click-modal=false
      title="退出课程"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <span>您正在退出{{courseName}}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dropCourse">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      :close-on-click-modal=false
      title="编辑课程"
      :visible.sync="editDialogVisible"
      width="60%"
    >
      <el-form ref="courseForm" :model="courseForm" label-width="80px" :rules="rules">
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="courseForm.name" ></el-input>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-select v-model="courseForm.grade" placeholder="请选择年级" style="width: 100%">
            <el-option label="大一" value="1"></el-option>
            <el-option label="大二" value="2"></el-option>
            <el-option label="大三" value="3"></el-option>
            <el-option label="大四" value="4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年份" prop="year">
          <el-select v-model="courseForm.year" style="width: 100%" placeholder="请选择年份">
            <el-option v-for="year in years" :label="year.label" :key="year.value" :value="year.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学期" prop="term">
          <el-select v-model="courseForm.term" placeholder="请选择学期" style="width: 100%">
            <el-option label="第一学期" value="1"></el-option>
            <el-option label="第二学期" value="2"></el-option>
            <el-option label="第三学期" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" prop="cls">
          <el-checkbox-group v-model="courseForm.cls">
            <el-checkbox label="1"></el-checkbox>
            <el-checkbox label="2"></el-checkbox>
            <el-checkbox label="3"></el-checkbox>
            <el-checkbox label="4"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="选课密码" prop="password">
          <el-input v-model="courseForm.password"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="modifyCourse('courseForm')">更新课程信息</el-button>
          <el-button v-on:click="editDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  import request from '../lib/request'

  export default {
    name: 'MyCourse',
    props: ['user'],
    data () {
      return {
        myListData: [],
        courseId: '',
        courseName: '',
        dialogVisible: false,
        editDialogVisible: false,
        courseForm: {
          name: '',
          grade: '',
          cls: [],
          year: '',
          term: '',
          password: ''
        },
        years: [{
          label: '2014 - 2015',
          value: '2014'
        }, {
          label: '2015 - 2016',
          value: '2015'
        }, {
          label: '2016 - 2017',
          value: '2016'
        }, {
          label: '2017 - 2018',
          value: '2017'
        }],
        rules: {
          name: [
            { required: true, message: '请输入课程名称', trigger: 'blur' }
          ],
          grade: [
            { required: true, message: '请选择年级', trigger: 'blur' }
          ],
          year: [
            { required: true, message: '请选择年份', trigger: 'blur' }
          ],
          term: [
            { required: true, message: '请选择学期', trigger: 'blur' }
          ],
          cls: [
            { type: 'array', required: true, message: '请至少选择一个班级', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请填写选课密码', trigger: 'blur' }
          ]
        }
      }
    },
    created: function () {
      request('/course/my', 'get', '', (success, message, data) => {
        if (success) {
          this.myListData = data
        } else {
          this.$notify.error({
            title: '错误',
            message: message
          })
        }
      })
    },
    methods: {
      dialogHandle (courseName, courseId) {
        this.courseName = courseName
        this.courseId = courseId
        this.dialogVisible = true
      },
      editCourseHandle (course) {
        this.courseId = course.id
        this.editDialogVisible = true
        this.courseForm.name = course.name
        this.courseForm.password = course.password
        this.courseForm.year = course.year.toString()
        this.courseForm.grade = course.grade.toString()
        this.courseForm.term = course.term.toString()
        this.courseForm.cls = course.cls.split(',')
      },
      modifyCourse (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.editDialogVisible = false
            let params = {
              name: this.courseForm.name,
              grade: this.courseForm.grade,
              cls: this.courseForm.cls,
              year: this.courseForm.year,
              term: this.courseForm.term,
              password: this.courseForm.password,
              id: this.courseId
            }
            request('/course/modify', 'post', params, (success, message, data) => {
              if (success) {
                this.myListData = this.myListData.map((obj) => {
                  if (obj.id === this.courseId) {
                    obj.name = params.name
                    obj.grade = params.grade
                    obj.cls = params.cls.join(',')
                    obj.year = params.year
                    obj.term = params.term
                    obj.password = params.password
                  }
                  return obj
                })
              } else {
                this.$notify.error({
                  title: '错误',
                  message: message
                })
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      dropCourse () {
        this.dialogVisible = false
        let params = {
          courseId: this.courseId
        }
        request('/course/drop', 'post', params, (success, message, data) => {
          if (success) {
            this.$router.push('list')
          } else {
            this.$notify.error({
              title: '错误',
              message: message
            })
          }
        })
      }
    }
  }
</script>

<style scoped>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }

  .box-card {
    width: 100%;
  }
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both;
  }
</style>
