// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Router from 'vue-router'
import router, {withoutLogin} from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import './lib/request'
import Util from './lib/util'

Vue.config.productionTip = false

Vue.use(Router)
Vue.use(ElementUI)

router.beforeEach((to, from, next) => {
  Util.title(to.meta.title)

  let user = Util.getCookie('user')

  if (!user && withoutLogin.indexOf(to.name) === -1) {  // 判断是否未登录且前往的页面需要登录
    Util.title('登录')
    next({
      name: 'Login'
    })
  } else if (user && withoutLogin.indexOf(to.name) !== -1) {  // 判断是否已经登录且前往的是登录页
    Util.title('首页')
    next({
      name: 'MyCourse'
    })
  } else {
    next()
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
})
