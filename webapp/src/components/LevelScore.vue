<template>
  <div class="main">
    <el-form :inline="true" :label-position="'top'">
      <el-form-item label="考试名称">
        <el-input size="mini" v-model="num" @change="submitScore"></el-input>
      </el-form-item>
      <el-form-item label="考试名称">
        <el-input size="mini" v-model="num1" @change="submitScore"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import request from '../lib/request'

  export default {
    name: 'LevelScore',
    description: '试题难度等级分数查看/编辑器',
    props: ['courseId'],
    data () {
      return {
        num: '',
        num1: ''
      }
    },
    mounted: function () {
      let params = {courseId: this.courseId}

      request('/level/list', 'post', params, function (success, message, data) {
        if (success) {
          console.log(message, data)
        }
      })
    },
    methods: {
      submitScore: function () {
        this.$emit('update:test', this.num)
      }
    }
  }
</script>

<style scoped>

</style>
