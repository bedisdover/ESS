<template>
  <div class="nav">
    <div class="content">
      <div class="nav-left">
        <router-link to="/list">课程列表</router-link>
        <router-link to="/my">我的课程</router-link>
        <router-link to="/myExam">我的考试</router-link>
      </div>
      <div class="nav-right">
        <span class="name">{{user.name}}</span>
        <span class="logout">
          <el-tooltip content="登 出">
            <span>
              <svg class="icon" aria-hidden="true" @click="logout">
                <use xlink:href="#icon-logout"></use>
              </svg>
            </span>
          </el-tooltip>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'NavBar',
    props: ['user'],
    methods: {
      logout: function () {
        this.$emit('onLogout')
      }
    }
  }
</script>

<style scoped>
  .nav {
    width: 100%;
    height: 72px;
    background: #2b83f9 linear-gradient(143deg, #2945cb 20%, #2b83f9 81%, #3a9dff);
    display: inline-block;
    margin-bottom: 25px;
  }

  .content {
    max-width: 1200px;
    width: 100%;
    display: inline-flex;
  }

  .nav-left {
    flex: 1;
    text-align: left;
  }

  a {
    line-height: 72px;
    color: #dedfe3;
    margin: 0 10px;
    padding: 0 20px;
    text-decoration: none;
    cursor: pointer;
    position: relative;
  }

  a:hover, .router-link-active {
    color: gold;
  }

  a:not(.router-link-active):after {
    content: '';
    position: absolute;
    top: 40px;
    left: 0;
    width: 100%;
    height: 5px;
    background: #fff;
    opacity: 0;
    -webkit-transition: height 0.3s, opacity 0.3s, -webkit-transform 0.3s;
    -moz-transition: height 0.3s, opacity 0.3s, -moz-transform 0.3s;
    transition: height 0.3s, opacity 0.3s, transform 0.3s;
    -webkit-transform: translateY(-10px);
    -moz-transform: translateY(-10px);
    transform: translateY(-10px);
  }

  .router-link-active:after {
    opacity: 1;
    top: 40px;
    content: '';
    position: absolute;
    left: 0;
    width: 100%;
    height: 5px;
    background: #fff;
  }

  a:hover:after {
    height: 5px;
    opacity: 1;
    -webkit-transform: translateY(0px);
    -moz-transform: translateY(0px);
    transform: translateY(0px);
  }

  .nav-right > span {
    line-height: 72px;
    color: white;
  }

  .logout {
    cursor: pointer;
    margin-left: 20px;
  }
</style>
