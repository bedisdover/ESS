<template>
  <div class="main">
    <div class="summary">
      {{score}}
    </div>
    <div class="level-container">
      <div class="level-box" v-for="(n, index) in maxNum">
        <div>难度 {{index + 1}}</div>
        <el-input-number v-model="questionNum[index]" :min="0" :max="n" :controls-position="'right'"
                         size="small"></el-input-number>
        <el-input v-model="questionMark[index]" placeholder="分数" size="small"></el-input>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'LevelScore',
    description: '试题难度等级分数查看/编辑器',
    props: ['maxNum', 'num', 'marks'],
    data () {
      return {
        levelNum: this.maxNum.length,
        questionNum: [],
        questionMark: []
      }
    },
    computed: {
      score: function () {
        let score = 0

        for (let i = 0; i < this.levelNum; i++) {
          if (this.questionMark[i] === '' || isNaN(this.questionMark[i])) {
            continue
          }

          if (this.questionNum[i] === 0) {
            this.questionMark[i] = 0
          } else {
            this.questionMark[i] = parseFloat(this.questionMark[i])
          }

          score += this.questionNum[i] * this.questionMark[i]
        }

        this.$emit('onUpdateScore', this.questionNum, this.questionMark)

        return score
      }
    },
    mounted: function () {
      this.questionNum = this.num ? this.num : new Array(this.levelNum).fill(0)
      this.questionMark = this.marks ? this.marks : new Array(this.levelNum).fill('')
    }
  }
</script>

<style scoped>
  .summary {
    font-size: 1.2em;
  }

  .level-container {
    display: inline-flex;
    flex-wrap: wrap;
    text-align: center;
  }

  .level-box {
    width: 130px;
    padding: 10px;
    margin-right: 20px;
    margin-bottom: 20px;
    border: 1px solid #409EFF;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
  }
</style>
