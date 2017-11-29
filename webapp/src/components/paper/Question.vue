<template>
  <div class="question-main">
    <div class="content">
      <strong class="index">{{index + 1}}.</strong>
      <pre>{{question.content}}</pre>
    </div>
    <el-checkbox-group class="options" v-model="checked">
      <el-checkbox v-for="option in question.options" :label="option.optionId" :key="option.optionId"
                   border></el-checkbox>
    </el-checkbox-group>
  </div>
</template>

<script>
  import ElCheckboxGroup from '../../../node_modules/element-ui/packages/checkbox/src/checkbox-group.vue'

  export default {
    components: {ElCheckboxGroup},
    name: 'Question',

    props: ['question', 'index'],

    data () {
      return {
        checked: []
      }
    },

    watch: {
      checked: function () {
        console.log(this.checked)
        this.$emit('onUpdateAnswer', this.checked)
      }
    }
  }
</script>

<style scoped>
  .question-main {
    margin-top: 10px;
    text-align: left;
  }

  .content {
    font-size: 1.2em;
    margin-bottom: 20px;
    display: flex;
  }

  .content > pre {
    flex: 1;
  }

  .index {
    color: #409EFF;
    margin-right: 15px;
  }

  .options > label {
    display: block;
    padding: 10px;
    margin: 15px;
  }
</style>
