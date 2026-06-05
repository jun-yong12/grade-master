package kr.ac.tukorea.grade.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 로그인 체크 인터셉터
 * 세션에 loginUser가 없으면 /login으로 리다이렉트
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            log.warn("[Interceptor] 미인증 접근: {}", request.getRequestURI());
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
