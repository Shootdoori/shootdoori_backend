package com.shootdoori.match.user.aop;

import com.shootdoori.match.dto.LoginRequest;
import com.shootdoori.match.user.service.AuthService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthLoggingAop {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private static final String LOG_LOGIN_SUCCESS = "User login success: email={}, device={}";
    private static final String LOG_LOGIN_FAIL = "Login failed: email={}, reason={}";

    @AfterReturning(
        pointcut = "execution(* com.shootdoori.match.user.service.AuthService.login(..) && args(request, userAgent)"
    )
    public void logLoginSuccess(LoginRequest request, String userAgent) {
        log.info(LOG_LOGIN_SUCCESS, request.email(), userAgent);
    }

    @AfterThrowing(
        pointcut = "execution(* com.shootdoori.match.user.service.AuthService.login(..)) && args(request, ..)",
        throwing = "ex"
    )
    public void logLoginFail(LoginRequest request, Exception ex) {
        log.warn(LOG_LOGIN_FAIL, request.email(), ex.getMessage());
    }
}
