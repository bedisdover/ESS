<template>
  <div>
    <el-row>
      <el-col :span="20" :offset="2">
        <el-card class="box-card clearfix">
          <div slot="header">
            <span style="margin-right: -205px">{{courseName}}试题列表</span>
            <el-button style="float: right;width: 100px" type="primary" size="small">
              下载试题模版
            </el-button>
            <el-button style="float: right;margin-right: 5px;width: 100px" type="primary" size="small"
                       @click="uploadDialogVisible = true">
              上传试题
            </el-button>
          </div>
          <el-table
            :data="questionListData"
            style="width: 100%;text-align: left">
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="left" inline class="demo-table-expand">
                  <el-form-item :label="'选项' + option.optionId+': '" v-for="(option,index) in props.row.options"
                                :key="option.optionId">
                    <span>{{ option.content }}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column
              label="题目内容"
              prop="content"
            >
            </el-table-column>
            <el-table-column
              label="等级"
              prop="level"
            >
            </el-table-column>
            <el-table-column
              label="答案"
              prop="answer"
            >
            </el-table-column>
          </el-table>
          <el-pagination
            style="margin-top: 20px"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page=page
            :page-sizes="[10, 15, 20, 50]"
            :page-size=size
            layout="total, sizes, prev, pager, next, jumper"
            :total=total>
          </el-pagination>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog
      :close-on-click-modal=false
      title="上传试题"
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
    name: 'QuestionList',
    props: ['id', 'courseName'],
    created: function () {
      request('/question/list', 'get', '', (success, message) => {
        if (success) {

        } else {
          util.notifyError(message)
        }
      })
    },
    data () {
      return {
        page: 1,
        size: 10,
        total: 50,
        questionListData: [
          {
            questionId: 32,
            courseId: 9,
            content: '数据结构',
            level: 1,
            answer: '1',
            options: [
              {
                optionId: 1,
                content: 'a'
              },
              {
                optionId: 2,
                content: 'b'
              },
              {
                optionId: 3,
                content: 'c'
              }
            ]
          },
          {
            questionId: 33,
            courseId: 9,
            content: '操作系统',
            level: 2,
            answer: '1,2',
            options: [
              {
                optionId: 1,
                content: 'a'
              },
              {
                optionId: 2,
                content: 'b'
              },
              {
                optionId: 3,
                content: 'c'
              },
              {
                optionId: 4,
                content: 'd'
              },
              {
                optionId: 5,
                content: 'e'
              }
            ]
          },
          {
            questionId: 34,
            courseId: 9,
            content: '数据库',
            level: 3,
            answer: '2',
            options: [
              {
                optionId: 1,
                content: 'a'
              },
              {
                optionId: 2,
                content: 'd'
              },
              {
                optionId: 3,
                content: 'c'
              },
              {
                optionId: 4,
                content: 'e'
              }
            ]
          }
        ],
        uploadDialogVisible: false,
        fileList: []
      }
    },
    methods: {
      handleSizeChange (val) {
        this.size = val
        alert(this.size)
      },
      handleCurrentChange (val) {
        this.page = val
        alert(this.page)
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
</style>
