package study.movieservice.controller.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import study.movieservice.domain.ExceptionMessageConst;
import study.movieservice.domain.SessionConst;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;

import static java.util.Objects.isNull;

@Component
@Aspect
public class LoginCheckAop {

    @Pointcut("execution(* study.movieservice.service.*.*(..))")
    private void loginPointcut(){}

    @Before("loginPointcut()")
    public void loginCheck() throws LoginException {
        HttpSession httpSession = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession(false);

        if(isNull(httpSession) || isNull(httpSession.getAttribute(SessionConst.MEMBER_ID))){
            throw new LoginException(ExceptionMessageConst.NEED_LOGIN.getMessage());
        }
    }
}
