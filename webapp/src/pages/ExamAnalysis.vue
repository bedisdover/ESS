<template>
  <div>
    <el-row>
      <el-col :span="20" :offset="2">
        <el-card class="box-card clearfix">
          <div slot="header">
            <span style="margin-right: -50px">考试分析</span>
            <a href="downUrl" target="_blank">
              <el-tooltip content="下载学生成绩" effect="light">
                        <span class="operation">
                          <svg class="icon" aria-hidden="true">
                            <use xlink:href="#icon-clouddownload"></use>
                          </svg>
                        </span>
              </el-tooltip>
            </a>
          </div>

          <div id="chart">
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  import request from '../lib/request'
  import util from '../lib/util'

  export default {
    name: 'ExamAnalysis',
    props: ['examId'],
    data () {
      return {
        scoreList: [],
        downUrl: '/exam/score/download?examId=' + this.examId
      }
    },
    mounted: function () {
      let params = {
        examId: this.examId
      }
      request('/exam/query/simple', 'post', params, (success, message, data) => {
        if (success) {
          console.log(data)
        } else {
          util.notifyError(message)
        }
      })
      request('/exam/analyze', 'post', params, (success, message, data) => {
        if (success) {
          this.scoreList = data.scoreList
          var echartData = []
          var xAxisData = []
          var max = 0
          for (var index in this.scoreList) {
            var tmp = parseInt(this.scoreList[index] / 10)
            max = tmp > max ? tmp : max
            if (echartData[tmp] === undefined) {
              echartData[tmp] = 0
            }
            echartData[tmp]++
          }
          for (var i = 0; i <= max; i++) {
            xAxisData[i] = '[' + i * 10 + ' - ' + (i * 10 + 10) + ')'
            if (echartData[i] === undefined) {
              echartData[i] = 0
            }
          }
          var echarts = require('echarts')
          // 基于准备好的dom，初始化echarts实例
          var myChart = echarts.init(document.getElementById('chart'))
          // 绘制图表
          myChart.setOption({
            title: {
              text: '考试成绩分布'
            },
            tooltip: {},
            xAxis: {
              data: xAxisData
            },
            yAxis: {},
            series: [{
              name: '成绩分布人数',
              type: 'bar',
              barMaxWidth: '10%',
              data: echartData
            }]
          })
        } else {
          util.notifyError(message)
        }
      })
    }
  }
</script>
<style scoped>
  #chart {
    width: 1000px;
    height: 500px;
    margin: 0 auto;
  }

  .operation {
    font-size: 1.5em;
    float: right;
    margin-left: 24px;
  }

  .operation svg {
    cursor: pointer;
  }
</style>
