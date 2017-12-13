<template>
  <div class="main">
    <div v-if="all && all.length > 0">
      <el-input v-model="checkedShow" readonly @click.native="showPopover"></el-input>
      <el-popover id="popover" v-model="popoverVisible">
        <el-transfer v-model="checked" :data="all" :titles="['未选', '已选']"
                     :props="{key: 'studentId', label: 'name'}"></el-transfer>
      </el-popover>
    </div>
    <div v-else>
      还没有学生,
      <router-link :to="'/examStudents/' + $route.params.id">去上传</router-link>
    </div>
  </div>
</template>

<script>
  import request from '../lib/request'

  export default {
    name: 'StudentList',

    props: ['courseId', 'students', 'readonly', 'onUpdateStudents'],

    data () {
      let checked = this.students ? this.students.reduce((array, student) => {
        array.push(student.studentId)

        return array
      }, []) : []

      return {
        all: [], // 所有学生列表
        checked: checked,
        popoverVisible: false
      }
    },

    computed: {
      /**
       * 用于input显示选择的学生列表
       */
      checkedShow: function () {
        if (this.checked.length === 0) {
          return ''
        }

        let temp = this.checked.slice(0, 5)
        let name = this.all.reduce((array, student) => {
          if (temp.indexOf(student.studentId) !== -1) {
            array.push(student.name)
          }

          return array
        }, [])

        return name.join('、') + (name.length === 5 ? ' 等' + this.checked.length + '人' : '')
      }
    },
    watch: {
      checked: function () {
        let students = this.all.filter(student => {
          return this.checked.indexOf(student.studentId) !== -1
        })

        this.$emit('onUpdateStudents', students)
      }
    },

    mounted () {
      let params = {
        courseId: this.courseId
      }

      request('/student/course/get', 'post', params, (success, message, data) => {
        if (success) {
          if (this.readonly) {
            this.all = data.map(student => Object.assign(student, {disabled: true}))
          } else {
            this.all = data
          }
        }
      })

      document.addEventListener('click', this.hidePopover)
    },

    methods: {
      showPopover: function (e) {
        this.popoverVisible = true

        e.stopPropagation()
      },
      hidePopover: function (e) {
        let popover = document.getElementById('popover')

        if (popover && popover.contains(e.target)) {
          return
        }

        this.popoverVisible = false
      }
    },

    destroyed () {
      document.removeEventListener('click', this.hidePopover)
    }
  }
</script>

<style>
  .el-popover {
    padding: 0;
  }
</style>

<style scoped>
  .main {
    width: 500px;
  }

  .main a {
    color: #409EFF;
  }
</style>
