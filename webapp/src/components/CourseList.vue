<template>
  <div>
    <el-row>
      <el-col :span="20"  :offset="2">
        <el-card class="box-card" style="width: 100% ; margin-top: 2%">
          <div slot="header">
            <span>课程列表</span>
            <el-button v-if="person.role === 1" style="float: right; padding: 3px 0" type="text">
              <router-link to="createCourse">创建课程</router-link>
            </el-button>
          </div>
          <el-table
            :data="tableData"
            stripe>
            <el-table-column
              prop="date"
              label="日期"
              >
            </el-table-column>
            <el-table-column
              prop="name"
              label="姓名">
            </el-table-column>
            <el-table-column
              prop="address"
              label="地址">
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: 'CourseList',
    data () {
      return {
        tableData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }],
        person: {
          role: 1,
          name: 'wangzelin',
          email: 'wzl@test.cn'
        }
      }
    }
  }
</script>

<style scoped>

</style>
