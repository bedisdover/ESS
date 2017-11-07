<template>
  <div class="main">
    <el-row>
      <el-col :span="20" :offset="2">
        <el-card class="box-card">
          <div slot="header">
            <span>考试列表</span>
            <el-button type="text" class="btn-create" @click="showDialog">新建考试</el-button>
          </div>
          <el-table :data="examList" class="table">
            <el-table-column type="expand">
              <template slot-scope="props">
                <ExamInfo :examList="props"></ExamInfo>
              </template>
            </el-table-column>
            <el-table-column label="考试名称" prop="name"></el-table-column>
            <el-table-column label="开始时间" prop="startTime"></el-table-column>
            <el-table-column label="结束时间" prop="endTime"></el-table-column>
            <el-table-column label="考试人数" prop="students"></el-table-column>
            <el-table-column label="试题数量" prop="num"></el-table-column>
            <el-table-column label="总分" prop="scores"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                  <span class="operation">
                    <svg class="icon" aria-hidden="true">
                        <use xlink:href="#icon-edit"></use>
                    </svg>
                    <svg class="icon" aria-hidden="true">
                        <use xlink:href="#icon-delete"></use>
                    </svg>
                  </span>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
        <el-dialog title="收货地址" :visible.sync="examFormVisible">
          <ExamForm></ExamForm>
          <div slot="footer" class="dialog-footer">
            <el-button @click="examFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="examFormVisible = false">确 定</el-button>
          </div>
        </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import ExamInfo from '../components/ExamInfo'
  import ExamForm from '../components/ExamForm'

  export default {
    name: 'ExamList',
    components: {ExamInfo, ExamForm},
    data () {
      return {
        examFormVisible: false,
        examList: [
          {
            name: '考试测试',
            startTime: '2017-11-07 18:00',
            endTime: '2017-11-07 20:00',
            students: 0,
            num: 10,
            scores: 100
          }
        ]
      }
    },
    methods: {
      showDialog: function () {
        this.examFormVisible = true
      }
    }
  }
</script>

<style scoped>
  .btn-create {
    float: right;
    padding: 3px 0;
  }

  .table {
    text-align: left;
  }

  .operation {
    font-size: 1.5em;
  }

  .operation svg {
    cursor: pointer;
  }
</style>
