package com.tenco.blog._core.interceptor;

import com.tenco.blog._core.errors.exception.Exception401;
import com.tenco.blog.board.BoardController;
import com.tenco.blog.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// IoC - Singleton
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    /**
     * preHandle - 컨트롤러에 들어가기 전에 동작하는 method
     * returnType = boolean, true = go to controller / false = stop
     */
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        log.info("Interceptor : {}", request.getRequestURL());
        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser == null) {
            throw new Exception401("Login needed, Please login");
            // return false;
        }
        return true;
    }

    // view 가 렌더링 되기 전에 rollback 되는 method
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

    // view 가 완전 렌더링 된 후 call 되는 method
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}
}
