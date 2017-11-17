<template>
  <div class="main">
    <el-checkbox-group v-model="checkedCls" @change="handleCheckedClsChange">
      <el-checkbox v-for="cls in clsList.split(',')" :label="cls" :key="cls">{{cls}} 班</el-checkbox>
    </el-checkbox-group>
    <el-input class="input" v-model="checkedShow"></el-input>
  </div>
</template>

<script>
  export default {
    name: 'StudentList',
    props: ['checkedStudents', 'students', 'clsList', 'onUpdateStudents'],
    data () {
      return {
        checked: [],
        checkedCls: []
      }
    },
    computed: {
      studentOfCls: function () {
        let temp = {}

        if (this.clsList === '') {
          return temp
        }

        this.clsList.split(',').forEach(cls => {
          temp[cls] = []
        })

        this.students.forEach(student => {
          temp[student.cls].push(student)
        })

        return temp
      },
      /**
       * 用于input显示选择的学生列表
       */
      checkedShow: function () {
        if (this.checked.length === 0) {
          return ''
        }

        let temp = []
        this.checkedCls.forEach(cls => {
          temp.push(this.studentOfCls[cls][0].name)
        })

        return temp.join('、') + ' 等' + this.checked.length + '人'
      }
//      clsList: function () {
//        let temp = new Set()
//
//        this.students.forEach(student => {
//          temp.add(student.cls)
//        })
//
//        return temp
//      }
    },
    methods: {
      handleCheckedClsChange: function (value) {
        this.checked = this.checked.concat(this.studentOfCls[value])
      }
    }
  }
</script>

<style scoped>
  .input {
    width: 500px;
  }
</style>
