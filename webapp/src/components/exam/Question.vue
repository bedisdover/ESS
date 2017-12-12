<template>
  <div class="question-main">
    <div class="content">
      <strong class="index">{{index + 1}}.</strong>
      <pre>{{question.content}}</pre>
    </div>
    <div class="options" :clsss="{editable: editable}">
      <div v-if="editable">
        <el-checkbox-group v-model="checked" @change="handleChange">
          <el-checkbox
            :key="option.optionId"
            v-for="option in question.options"
            :label="option.optionId">
            {{option.content}}
          </el-checkbox>
        </el-checkbox-group>
      </div>
      <div v-else>
        <label
          :key="option.optionId"
          v-for="option in question.options">
          <input type="checkbox">
          <span>{{option.content}}</span>
        </label>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Question',

    props: ['question', 'index', 'editable'],

    data () {
      return {
        checked: this.question.answer ? [...this.question.answer] : []
      }
    },

    watch: {
      index: function () {
        this.checked = this.question.answer ? [...this.question.answer] : []
      }
    },

    methods: {
      handleChange: function () {
        this.$emit('onChange', this.checked)
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

  .options label {
    display: block;
    padding: 10px;
    margin: 15px;
    border: 1px solid #d8dce5;
    border-radius: 4px;
  }
</style>
