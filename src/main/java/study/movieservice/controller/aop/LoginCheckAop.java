package study.movieservice.controller.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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

    @Before("@annotation(study.movieservice.controller.aop.annotation.LoginCheck)")
    public void loginCheck() throws LoginException {
        HttpSession httpSession = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession(false);

        if(isNull(httpSession) || isNull(httpSession.getAttribute(SessionConst.MEMBER_ID))){
            throw new LoginException(ExceptionMessageConst.NEED_LOGIN.getMessage());
        }
    }
}
