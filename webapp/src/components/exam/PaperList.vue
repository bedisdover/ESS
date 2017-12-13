<template>
  <div class="main">
    <el-row>
      <el-col :span="20" :offset="2">
        <el-card class="box-card">
          <div slot="header">
            <span>考试列表</span>
          </div>
          <el-table :data="examList" class="table" v-loading="loading">
            <el-table-column label="课程名称" prop="courseName"></el-table-column>
            <el-table-column label="考试名称" prop="examName"></el-table-column>
            <el-table-column label="开始时间" prop="startTime"></el-table-column>
            <el-table-column label="结束时间" prop="endTime"></el-table-column>
            <el-table-column label="总分" prop="sum">{{123123}}</el-table-column>
            <el-table-column label="成绩" prop="mark"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <span class="operation" v-if="scope.row.mark !== '--'">
                  <el-tooltip content="查看试卷" effect="light">
                    <router-link :to="'/paper/' + scope.row.examId" target="_blank">
                      <svg class="icon" aria-hidden="true">
                        <use xlink:href="#icon-info"></use>
                      </svg>
                    </router-link>
                  </el-tooltip>
                </span>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import request from '../../lib/request'
  import Util from '../../lib/util'

  export default {
    name: 'PaperList',

    data () {
      return {
        loading: true,
        examList: []
      }
    },

    mounted: function () {
      request('/exam/all', 'post', '', (success, message, data) => {
        if (success) {
          data.map(paper => {
            if (new Date(paper.endTime) >= new Date()) {
              paper.mark = '--'
            }
          })
          this.examList = data

          this.loading = false
        } else {
          Util.notifyError(message)
        }
      })
    }
  }
</script>

<style scoped>
  .table {
    text-align: left;
  }

  .operation {
    font-size: 1.2rem;
  }
</style>
