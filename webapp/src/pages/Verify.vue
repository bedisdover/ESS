<template>
  <el-button @click="gotoLogin">去登录</el-button>
</template>

<script>
  export default {
    name: 'Verify',

    methods: {
      gotoLogin: function () {
        this.$emit('onLogin')
      }
    }
  }
</script>
