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
  xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8')
  xhr.responseType = 'json'

  xhr.onload = function () {
    success(xhr.response.success, xhr.response.message, xhr.data)
  }

  xhr.onerror = function () {
    if (error instanceof Function) {
      error()
    } else {
      console.log('error')
    }
  }

  xhr.send(data)

  return xhr
}

export default request
