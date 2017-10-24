<template>
  <div>
    <div v-if="person.role === 1">
      <el-row>
        <el-col :span="12"  :offset="6">
          <el-card class="box-card" style="width: 100% ; margin-top: 5%">
            <el-form ref="form" :model="form" label-width="80px">
              <el-form-item label="课程名称">
                <el-input v-model="form.name" ></el-input>
              </el-form-item>
              <el-form-item label="年级">
                <el-select v-model="form.grade" placeholder="请选择年级" style="width: 100%">
                  <el-option label="大一" value="1"></el-option>
                  <el-option label="大二" value="2"></el-option>
                  <el-option label="大三" value="3"></el-option>
                  <el-option label="大四" value="4"></el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="年份">
                <el-select v-model="form.year" style="width: 100%">
                  <el-option v-for="year in years" v-bind:label="year" v-bind:key="year.id" v-bind:value="year"></el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="学期">
                <el-select v-model="form.term" placeholder="请选择学期" style="width: 100%">
                  <el-option label="第一学期" value="1"></el-option>
                  <el-option label="第二学期" value="2"></el-option>
                  <el-option label="第三学期" value="3"></el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="班级">
                <el-checkbox-group v-model="form.class">
                  <el-checkbox label="1"></el-checkbox>
                  <el-checkbox label="2"></el-checkbox>
                  <el-checkbox label="3"></el-checkbox>
                  <el-checkbox label="4"></el-checkbox>
                </el-checkbox-group>
              </el-form-item>

              <el-form-item label="选课密码">
                <el-input v-model="form.password"/>
              </el-form-item>

              <el-form-item>
                <el-button type="primary" @click="onSubmit">立即创建</el-button>
                <el-button v-on:click="onCancel">取消</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div v-else>
        您身份不正确
    </div>

  </div>
</template>

<script>
  import ElFormItem from '../../node_modules/element-ui/packages/form/src/form-item.vue'
  import ElCheckbox from '../../node_modules/element-ui/packages/checkbox/src/checkbox.vue'

  export default {
    components: {
      ElCheckbox,
      ElFormItem},
    name: 'CreateCourse',
    data () {
      return {
        form: {
          name: '',
          grade: '',
          class: [],
          year: [],
          term: '',
          password: ''
        },
        years: [
          '2014 - 2015', '2015 - 2016', '2016 - 2017', '2017 - 2018', '2018 - 2019'
        ],
        person: {
          role: 1,
          name: 'wangzelin',
          email: 'wzl@test.cn'
        }
      }
    },
    methods: {
      onSubmit () {
        console.log(this.form.name)
      },
      onCancel () {
        alert('aaa')
        this.form = {
          name: '',
          grade: '',
          class: [],
          year: [],
          term: '',
          password: ''
        }
      }
    }
  }
</script>

<style scoped>

</style>
