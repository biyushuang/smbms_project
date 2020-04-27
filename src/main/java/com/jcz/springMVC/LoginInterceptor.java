package com.jcz.springMVC;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jcz.entity.SmbmsUser;

@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    @Override  // 在执行目标方法之前执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("------:进来拦截器了！--1");
        //获取session
        HttpSession session = request.getSession(true);
        //判断用户是否存在，不存在就跳转到登录界面
        if(session.getAttribute("userSession")==null){
                System.out.println("------:跳转到login页面！");
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            return false;
        }else{
            return true;
        }
    }

    @Override  // 执行目标方法之后执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("------:进来拦截器了！--2");
    }

    @Override  // 在请求已经返回之后执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("------:进来拦截器了！--3");
    }
}