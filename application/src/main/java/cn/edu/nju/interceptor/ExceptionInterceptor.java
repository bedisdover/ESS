package cn.edu.nju.interceptor;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.utils.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintStream;

public class ExceptionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) {}

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object o, Exception e) throws Exception {
        if (e != null) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            PrintStream out = new PrintStream(response.getOutputStream(), true, "utf-8");
            out.println(JsonUtil.toJson(new ResultInfo(
                    false, "系统异常", null
            )));
            Logger.getLogger(ExceptionInterceptor.class).error(e);
        }
    }
}
