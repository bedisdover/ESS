import Util from './util'

const request = function (url, type, data, success, error) {
  let xhr = null

  if (window.ActiveXObject) {
    // 如果是IE浏览器
    xhr = new window.ActiveXObject('Microsoft.XMLHTTP')
  } else if (window.XMLHttpRequest) {
    // 非IE浏览器
    xhr = new XMLHttpRequest()
  }

  xhr.open(type, process.env.API_ROOT + url)
  // 允许跨域, 使用cookie
  xhr.withCredentials = true

  xhr.responseType = 'json'

  let params = ''

  if (data instanceof Object) {
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8')

    for (let key in data) {
      params += key + '=' + data[key] + '&'
    }
  } else if (typeof data === 'string') {
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8')

    params = data
  }

  xhr.send(params)

  xhr.onload = function () {
    success(xhr.response.success, xhr.response.message, xhr.response.data)
  }

  xhr.onerror = function () {
    Util.notifyError('出错了...')

    if (error instanceof Function) {
      error()
    } else {
      console.log('error')
    }
  }

  return xhr
}

export default request
