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
 * 错误通知
 * @param message 错误信息
 */
util.notifyError = function (message) {
  Notification.error({
    title: '错误',
    message: message
  })
}

export default util
