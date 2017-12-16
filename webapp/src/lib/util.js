import Cookies from 'js-cookie'
import {Loading, Notification} from 'element-ui'
import format from 'date-fns/format'

const util = {}

/**
 * 设置页面title
 */
util.title = function (title) {
  if (title) {
    title = 'ESS | ' + title
  } else {
    title = 'ESS | 在线考试支持系统'
  }

  window.document.title = title
}

let loading = null

/**
 * 全页面 loading
 */
util.loading = function () {
  loading = Loading.service({
    target: '#app-content',
    text: '拼命加载中...',
    lock: true
  })
}

/**
 * 取消 loading
 */
util.closeLoading = function () {
  if (loading) {
    loading.close()
  }
}

/**
 * 获取cookie内容
 * @param name cookie名称
 * @returns 对应数据, 若为json类型数据, 返回转换后的对象
 */
util.getCookie = function (name) {
  let data = Cookies.get(name)

  try {
    data = JSON.parse(data)
  } catch (e) {
    // do nothing
  }

  return data
}

/**
 * 存储cookie, cookie 过期时间统一设置为 1 天
 * @param name cookie名称
 * @param data 对应数据
 */
util.setCookie = function (name, data) {
  if (navigator.cookieEnabled) {
    Cookies.set(name, data, {expires: 1})
  } else {
    util.notifyError('请打开浏览器Cookie')
  }
}

/**
 * 移除cookie
 * @param name cookie名称
 */
util.removeCookie = function (name) {
  Cookies.remove(name)
}

/**
 * 成功通知
 * @param message 成功信息
 */
util.notifySuccess = function (message) {
  Notification.success({
    title: '成功',
    message: message
  })
}

/**
 * 错误通知
 * @param message 错误信息
 */
util.notifyError = function (message) {
  Notification.error({
    title: '错误',
    message: message
  })
}

/**
 * 校验邮箱合法性
 */
util.validateEmail = (rule, value, callback) => {
  const mailRegex = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+\.){1,63}[a-z0-9]+$/

  if (value === '') {
    callback(new Error('请输入邮箱'))
  } else if (!mailRegex.test(value)) {
    callback(new Error('请输入正确邮箱'))
  } else {
    callback()
  }
}

/**
 * 根据参数名称获取url中的参数
 */
util.getParamByName = function (name) {
  let regex = new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)')
  let result = (regex.exec(location.search) || [null, ''])[1].replace(/\+/g, ' ')

  return decodeURIComponent(result) || null
}

/**
 * 格式化时间
 */
util.formatTime = function (date, timeFormat = 'yyyy-MM-dd hh:mm:ss') {
  return format(date, timeFormat)
}

/**
 * 返回倒计时
 */
util.countdown = function (target) {
  let time = target - new Date()

  if (time <= 0) {
    return null
  }

  let day = Math.floor(time / 1000 / 60 / 60 / 24)
  let dayText = day ? day + '天 ' : ''

  time = time - day * (1000 * 60 * 60 * 24) - 8 * 1000 * 60 * 60

  return dayText + this.formatTime(time, 'HH:mm:ss')
}

/**
 * 数组随机排列(Fisher–Yates shuffle 算法)
 */
util.shuffle = function (array) {
  if (!array || !array.length) {
    return []
  }

  let temp = array.slice()

  for (let i = array.length - 1; i >= 0; i--) {
    let randomIndex = Math.floor(Math.random() * (i + 1))

    let itemAtIndex = temp[randomIndex]
    temp[randomIndex] = temp[i]
    temp[i] = itemAtIndex
  }

  return temp
}

export default util
