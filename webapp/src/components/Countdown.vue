<template>
  <el-card class="countdown">
    <div class="countdown-body">
      <div class="name">{{exam.name}}</div>
      <div class="notice">
        <div v-if="!ended()">
          <div class="title">考试规定</div>
          <div class="content">
            <p>一、诚信考试，请自觉遵守诚信考试规范；</p>
            <p>二、请合理安排时间，考试结束时间一到，系统将自动收卷；</p>
            <p>三、中途放弃考试，考试结束前未提交试卷，作弃考处理，成绩为0分；</p>
            <p>四、为确保正常进行考试，请使用最新版本的 Chrome 或 Firefox 浏览器；</p>
            <p>五、一旦开始考试，将无法暂停，所以请您进入答题之前确保您的电脑和网络正常；</p>
            <p>六、如遇浏览器崩溃、系统重启等异常情况，考试进行期间重启浏览器重新打开即可继续考试。</p>
          </div>
        </div>
        <div v-else>
          <div class="title">
            本场考试已经结束
          </div>
        </div>
      </div>
      <div class="footer">
        <el-button type="primary" :disabled="waiting()" @click="handleClick">{{buttonText}}</el-button>
      </div>
    </div>
  </el-card>
</template>

<script>
  import Util from '../lib/util'

  export default {
    name: 'Countdown',

    props: {
      exam: Object
    },

    data () {
      return {
        time: '距离考试开始 : ',
        timeout: null
      }
    },

    created () {
      this.timeout = setInterval(() => {
        this.getTime()
      }, 1000)
    },

    computed: {
      buttonText: function () {
        return this.waiting() ? this.time : this.ended() ? '前往ESS首页' : '开始考试'
      }
    },

    methods: {
      waiting: function () {
        return new Date() < new Date(this.exam.startTime)
      },
      ended: function () {
        return this.exam.end || new Date() > new Date(this.exam.endTime)
      },
      getTime: function () {
        let time = new Date(this.exam.startTime) - new Date()

        let day = Math.floor(time / 1000 / 60 / 60 / 24)

        this.time = '距离考试开始 : ' + day + Util.formatTime(new Date(time), '天h小时m分s秒')
      },
      handleClick: function () {
        if (this.ended()) {
          this.$router.push('/index')
        } else {
          clearTimeout(this.timeout)
          this.$emit('onStartExam')
        }
      }
    },

    destroyed () {
      clearTimeout(this.timeout)
    }
  }
</script>

<style scoped>
  .name {
    color: #409EFF;
    margin: 20px;
    text-align: left;
    font-size: 2em;
    font-weight: 200;
  }

  .notice {
    margin: 0 20px;
    background: #f4f4f4;
    border-radius: 4px;
  }

  .title {
    padding: 40px;
  }

  .content {
    padding: 0 20px 20px;
    text-align: left;
  }

  .footer {
    padding: 40px 0 20px;
    justify-self: end;
  }
</style>
