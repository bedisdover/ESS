import Router from 'vue-router'
import Index from '@/pages/Index'
import Login from '@/pages/Login'
import Error404 from '@/pages/404'
import Register from '@/pages/Register'
import CourseList from '@/components/CourseList'
import MyCourse from '@/components/MyCourse'
import CreateCourse from '@/components/CreateCourse'

// 无需登录的页面
export const withoutLogin = [
  'Index', 'Login', 'Register'
]

// 单独页面
const pageRouter = [
  {
    path: '/',
    name: 'Index',
    alias: '/index',
    component: Index,
    meta: {
      title: '首页'
    }
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: {
      title: '登录'
    }
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
    meta: {
      title: '注册'
    }
  }
]

// 包含导航栏页面
const navRouter = [
  {
    path: '/list',
    name: 'CourseList',
    component: CourseList,
    meta: {
      title: '课程列表'
    }
  },
  {
    path: '/createCourse',
    name: 'CreateCourse',
    component: CreateCourse,
    meta: {
      title: '创建课程'
    }
  },
  {
    path: '/my',
    name: 'MyCourse',
    component: MyCourse,
    meta: {
      title: '我的课程'
    }
  }
]

const page404 = {
  path: '/*',
  name: 'error_404',
  component: Error404,
  title: '404-页面不存在'
}

const router = new Router({
  routes: [
    ...pageRouter,
    ...navRouter,
    page404
  ]
})

export default router
