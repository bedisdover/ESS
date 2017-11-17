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
            <el-table-column
              type="selection"
              width="55">
            </el-table-column>
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
          <div style="margin-top: 20px">
            <el-button style="float: left"
                       type="danger"
                       size="small">删除题目
            </el-button>
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page=page
              :page-sizes="[10, 15, 20, 50]"
              :page-size=size
              layout="total, sizes, prev, pager, next, jumper"
              :total=total>
            </el-pagination>
          </div>
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
  import ElButton from '../../node_modules/element-ui/packages/button/src/button.vue'

  export default {
    components: {ElButton},
    name: 'QuestionList',
    props: ['id', 'courseName'],
    data () {
      return {
        page: 1,
        size: 10,
        total: 50,
        questionListData: [],
        uploadDialogVisible: false,
        fileList: []
      }
    },
    methods: {
      reloadQuestionList () {
        let url = '/question/list?courseId=' + this.id + '&page=' + this.page + '&size=' + this.size
        request(url, 'get', '', (success, message, data) => {
          if (success) {
            this.total = data.num
            this.questionListData = data.questionInfoList
          } else {
            util.notifyError(message)
          }
        })
      },
      handleSizeChange (val) {
        this.size = val
        this.reloadQuestionList()
      },
      handleCurrentChange (val) {
        this.page = val
        this.reloadQuestionList()
      }
    },
    beforeMount: function () {
      this.reloadQuestionList()
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
