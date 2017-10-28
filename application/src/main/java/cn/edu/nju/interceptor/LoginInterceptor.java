package cn.edu.nju.interceptor;

import cn.edu.nju.controller.AccountController;
import cn.edu.nju.utils.JsonUtil;
import cn.edu.nju.vo.ResultInfo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintStream;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object o) throws Exception {
        HttpSession session = request.getSession();
        boolean isLogin = AccountController.getUserId(session) != null;
        if (!isLogin) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            PrintStream out = new PrintStream(response.getOutputStream());
            out.println(JsonUtil.toJson(new ResultInfo(
                    false, "请先登录", null
            )));
        }
        return isLogin;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object o, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object o, Exception e) throws Exception {}
}
