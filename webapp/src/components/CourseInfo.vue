<template>
  <el-form label-position="left" inline class="demo-table-expand">
    <el-form-item label="课程名称：">
      <span>{{ props.row.name }}</span>
    </el-form-item>
    <el-form-item label="年份：">
      <span>{{ props.row.year }}</span>
    </el-form-item>
    <el-form-item label="年级：">
      <span>{{ props.row.grade }}</span>
    </el-form-item>
    <el-form-item label="学期：">
      <span>{{ props.row.term }}</span>
    </el-form-item>
    <el-form-item label="班级：">
      <span>{{ props.row.cls }}</span>
    </el-form-item>
  </el-form>
</template>

<script>
  export default {
    name: 'CourseInfo',
    props: ['props']
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
</style>
