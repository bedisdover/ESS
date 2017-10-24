// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Router from 'vue-router'
import router, {withoutLogin} from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Cookies from 'js-cookie'
import App from './App'
import './lib/request'
import Util from './lib/util'

Vue.config.productionTip = false

Vue.use(Router)
Vue.use(ElementUI)

// 整页加载
// let loading
// loading = Loading.service({
//   // fullscreen: true,
//   // lock: true,
//   text: '玩命加载中...'
// })
// loading.close()

router.beforeEach((to, from, next) => {
  Util.title(to.meta.title)

  if (!Cookies.get('user') && withoutLogin.indexOf(to.name) === -1) {  // 判断是否未登录且前往的页面需要登录
    Util.title('登录')
    next({
      name: 'Login'
    })
  } else if (Cookies.get('user') && withoutLogin.indexOf(to.name) !== -1) {  // 判断是否已经登录且前往的是登录页
    Util.title('首页')
    next({
      name: 'Index'
    })
  } else {
    next()
  }

  // loading.close()
})

router.afterEach(() => {
  // loading.close()
  window.scrollTo(0, 0)
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
})
