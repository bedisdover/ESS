import Cookies from 'js-cookie'
import {Notification} from 'element-ui'

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
  let url = window.location.href
  name = name.replace(/[\[\]]/g, '\\$&')
  let regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
    results = regex.exec(url)
  if (!results) return null
  if (!results[2]) return ''
  return decodeURIComponent(results[2].replace(/\+/g, ' '))
}

export default util
