import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/Index'
import CourseList from '@/components/CourseList'
import MyCourse from '@/components/MyCourse'
import Login from '@/components/Login'
import Register from '@/components/Register'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      alias: '/index',
      component: Index
    },
    {
      path: '/list',
      name: 'CourseList',
      component: CourseList
    },
    {
      path: '/my',
      name: 'MyCourse',
      component: MyCourse
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    }
  ]
})
