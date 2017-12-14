<template>
  <div>
    <el-row>
      <el-col :span="20" :offset="2">
        <el-card class="box-card clearfix">
          <div slot="header">
            <el-tooltip content="删除选中题目" effect="light">
                        <span class="operation deleteIcon" @click="deleteQuestion">
                          <svg class="icon " aria-hidden="true">
                            <use xlink:href="#icon-delete"></use>
                          </svg>
                        </span>
            </el-tooltip>
            <span style="margin-right: -72px" v-if="courseName != null">【{{courseName}}】试题列表</span>
            <span style="margin-right: -72px" v-else>试题列表</span>
              <a href="/question/download" target="_blank">
                <el-tooltip content="下载试题模版" effect="light">
                        <span class="operation">
                          <svg class="icon" aria-hidden="true">
                            <use xlink:href="#icon-clouddownload"></use>
                          </svg>
                        </span>
                </el-tooltip>
              </a>
              <el-tooltip content="上传试题库" effect="light">
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
        :with-credentials="true"
        :data="{courseId: parseInt(id)}"
        :on-success="uploadSuccess"
        :show-file-list="false"
        name="questions"
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
  import ElButton from '../../node_modules/element-ui/packages/button/src/button.vue'

  export default {
    components: {ElButton},
    name: 'QuestionList',
    props: ['id', 'courseName'],
    data () {
      return {
        page: 1,
        size: 10,
        total: 0,
        questionListData: [],
        deleteQuestionList: [],
        uploadDialogVisible: false,
        loading: true
      }
    },
    methods: {
      uploadSuccess (response) {
        if (response.success) {
          util.notifySuccess(response.message)
          this.uploadDialogVisible = false
          this.reloadQuestionList()
        } else {
          util.notifyError(response.message)
        }
      },
      reloadQuestionList () {
        this.loading = true
        let params = {
          courseId: this.id,
          page: this.page,
          size: this.size
        }
        request('/question/list', 'post', params, (success, message, data) => {
          if (success) {
            this.total = data.num
            this.questionListData = data.questionInfoList
            this.loading = false
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
      },
      handleSelectionChange (val) {
        this.deleteQuestionList = val.map((obj) => {
          return obj.questionId
        })
      },
      deleteQuestion () {
        if (this.deleteQuestionList.length === 0) {
          return false
        }
        request('/question/delete', 'post', JSON.stringify(this.deleteQuestionList), (success, message) => {
          if (success) {
            this.deleteQuestionList = []
            this.reloadQuestionList()
          } else {
            util.notifyError(message)
          }
        })
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
