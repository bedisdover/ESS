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

export default util
