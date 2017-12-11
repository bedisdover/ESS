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
          <el-form ref="form" class="formClass" :inline="true">
            <el-form-item label="考试课程：">
              <span class="font">{{courseName}}</span>
            </el-form-item>
            <el-form-item label="考试开始时间：">
              <span class="font">{{startTime}}</span>
            </el-form-item>
            <el-form-item label="考试结束时间：">
              <span class="font">{{endTime}}</span>
            </el-form-item>
          </el-form>
          <div id="chart"></div>
          <div id="distributeChart"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  import request from '../lib/request'
  import util from '../lib/util'
  import ElFormItem from '../../node_modules/element-ui/packages/form/src/form-item.vue'

  export default {
    components: {ElFormItem},
    name: 'ExamAnalysis',
    props: ['examId'],
    data () {
      return {
        score: 0,
        scoreList: [],
        downUrl: '/exam/score/download?examId=' + this.examId,
        courseName: '',
        startTime: '',
        endTime: ''
      }
    },
    mounted: function () {
      let params = {
        examId: this.examId
      }
      request('/exam/query/simple', 'post', params, (success, message, data) => {
        if (success) {
          console.log(data)
          this.courseName = data.name
          this.startTime = data.startTime
          this.endTime = data.endTime
        } else {
          util.notifyError(message)
        }
      })
      request('/exam/analyze', 'post', params, (success, message, data) => {
        if (success) {
          console.log(data)
          var echarts = require('echarts')
          // 基于准备好的dom，初始化echarts实例
          var myChart = echarts.init(document.getElementById('chart'))
          var distributeEchart = echarts.init(document.getElementById('distributeChart'))
          myChart.showLoading()
          distributeEchart.showLoading()
          this.scoreList = data.scoreList
          this.score = data.sum
          var echartData = []
          var xAxisData = []
          var distributeXAxisData = []
          var distributeEchartData = []

          for (var index in this.scoreList) {
            var tmp = Math.round(this.scoreList[index])
            var distributeTmp = parseInt(this.scoreList[index] / 10)
            if (echartData[tmp] === undefined) {
              echartData[tmp] = 0
            }
            echartData[tmp]++
            if (distributeEchartData[distributeTmp] === undefined) {
              distributeEchartData[distributeTmp] = 0
            }
            distributeEchartData[distributeTmp]++
          }
          for (var i = 0; i <= this.score; i++) {
            xAxisData[i] = i
            if (echartData[i] === undefined) {
              echartData[i] = 0
            }
          }
          for (i = 0; i <= this.score / 10; i++) {
            if (i === this.score / 10) {
              distributeXAxisData[i] = 10 * i
            } else {
              distributeXAxisData[i] = '[' + 10 * i + ' - ' + 10 * (i + 1) + ')'
            }

            if (distributeEchartData[i] === undefined) {
              distributeEchartData[i] = 0
            }
          }

          // 绘制图表
          myChart.setOption({
            title: {
              text: '考试成绩分布'
            },
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'cross'
              }
            },
            xAxis: {
              data: xAxisData,
              boundaryGap: false
            },
            yAxis: {
              minInterval: 1,
              axisLabel: {
                formatter: '{value} 人'
              },
              axisPointer: {
                snap: true
              }
            },
            series: [{
              name: '成绩分布人数',
              smooth: true,
              type: 'line',
              data: echartData
            }]
          })
          myChart.hideLoading()

          // 绘制图表
          distributeEchart.setOption({
            title: {
              text: '考试成绩分数段分布'
            },
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'cross'
              }
            },
            xAxis: {
              data: distributeXAxisData
            },
            yAxis: {
              minInterval: 1,
              axisLabel: {
                formatter: '{value} 人'
              },
              axisPointer: {
                snap: true
              }
            },
            series: [{
              name: '成绩分数段分布人数',
              type: 'bar',
              barMaxWidth: '12%',
              data: distributeEchartData
            }]
          })
          distributeEchart.hideLoading()
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

  .formClass {
    margin: 0 auto;
    width: 800px;
  }

  #distributeChart {
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

  .font {
    font-weight: bolder;
  }
</style>
