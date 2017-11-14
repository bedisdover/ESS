import Router from 'vue-router'
import Index from '@/pages/Index'
import Login from '@/pages/Login'
import Error404 from '@/pages/404'
import Error401 from '@/pages/401'
import Register from '@/pages/Register'
import Verify from '@/pages/Verify'
import CourseList from '@/pages/CourseList'
import MyCourse from '@/pages/MyCourse'
import CreateCourse from '@/pages/CreateCourse'
import Exam from '@/pages/Exam'
import ExamList from '@/pages/ExamList'
import QuestionList from '@/pages/QuestionList'

// 单独页面
const singlePage = [
  'Error404', 'Error401', 'Verify', 'Exam'
]

// 无需登录的页面
const withoutLogin = [
  'Index', 'Login', 'Register'
]

/**
 * 权限定义
 * 0 --- 教师独有权限
 * 1 --- 学生独有权限
 */
const privileges = [
  [
    'CreateCourse', 'QuestionList'
  ],
  []
]

/**
 * 判断是否具有页面访问权限
 * @param role 角色类型
 * @param name 访问的页面名称
 */
const hasPrivilege = function (role, name) {
  if (role !== 1 && role !== 2) {
    return false
  }

  // 检验是否访问独有权限的页面
  return privileges[role & 1].indexOf(name) === -1
}

// 不含导航栏页面路由
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
  },
  {
    path: '/verify',
    name: 'Verify',
    component: Verify,
    meta: {
      title: '邮箱验证'
    }
  }, {
    path: '/exam',
    name: 'Exam',
    component: Exam,
    meta: {
      title: '在线考试'
    }
  }
]

// 包含导航栏页面路由
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
  },
  {
    path: '/examList',
    name: 'ExamList',
    component: ExamList,
    mata: {
      title: '考试列表'
    }
  },
  {
    path: '/questionList/:id',
    name: 'QuestionList',
    component: QuestionList,
    props: true,
    mata: {
      title: '试题列表'
    }
  }
]

// 401 页面
const page401 = {
  path: '/noPrivilege',
  name: 'Error401',
  component: Error401,
  meta: {
    title: '401-无访问权限'
  }
}

// 404 页面
const page404 = {
  path: '/*',
  name: 'Error404',
  component: Error404,
  meta: {
    title: '404-页面不存在'
  }
}

const router = new Router({
  routes: [
    ...pageRouter,
    ...navRouter,
    page401,
    page404
  ]
})

export {
  singlePage,
  withoutLogin,
  hasPrivilege
}

export default router
