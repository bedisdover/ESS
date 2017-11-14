<template>
  <div>
    <el-row>
      <el-col :span="20" :offset="2">
        <el-card class="box-card clearfix">
          <div slot="header">
            <span style="margin-right: -205px">试题列表</span>
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

          </el-table>
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
  export default {
    name: 'QuestionList',
    props: ['id'],
    data () {
      return {
        questionListData: [],
        uploadDialogVisible: false,
        fileList: []
      }
    },
    methods: {
    }
  }
</script>
