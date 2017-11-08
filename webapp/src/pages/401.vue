<template>
  <div class="error401">
    <div class="error401-body-con">
      <el-card>
        <div class="error401-body-con-title">
          <span>
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-lock"></use>
            </svg>
          </span>
        </div>
        <p class="error401-body-con-message">YOU&nbsp;&nbsp;HAVE&nbsp;&nbsp;NO&nbsp;&nbsp;PRIVILEGE</p>
        <div class="error401-btn-con">
          <el-button @click="goHome" size="large" type="text">前往首页</el-button>
          <el-button @click="backPage" size="large" type="primary">返回上一页
          </el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Error401',
    methods: {
      backPage () {
        this.$router.go(-2)
      },
      goHome () {
        this.$router.push({
          name: 'MyCourse'
        })
      }
    }
  }
</script>

<style scoped>
  .error401-body-con {
    width: 700px;
    height: 500px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }

  .error401-body-con-title {
    text-align: center;
    font-size: 240px;
    font-weight: 700;
    color: #2d8cf0;
    height: 260px;
    line-height: 260px;
    margin-top: 40px;
  }

  .error401-body-con-title span {
    display: inline-block;
    color: #19be6b;
    font-size: 180px;
  }

  .error401-body-con-message {
    display: block;
    text-align: center;
    font-size: 28px;
    font-weight: 400;
    letter-spacing: 5px;
    color: #dddde2;
  }

  .error401-btn-con {
    text-align: center;
    padding: 20px 0;
    margin-bottom: 40px;
  }

  .error401-btn-con > button {
    width: 200px;
  }

  .error401-btn-con > button:last-child {
    margin-left: 40px;
  }
</style>
