package com.example.common.handler;


import com.example.common.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
public class LogHandler {

    private static final Logger logger = LoggerFactory.getLogger(LogHandler.class);

    /**
     * 指定 controller 包下的注解
     */
    @Pointcut("execution( * com.example.controller.*.*(..))")//两个..代表所有子目录，最后括号里的两个..代表所有参数
    public void logPointCut() {

    }


    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        long startTime = System.currentTimeMillis();

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestId = UUID.randomUUID().toString();
        String args = Arrays.toString(pjp.getArgs());
        if (args.getBytes(StandardCharsets.UTF_8).length > 1024) {
            args = args.substring(0, 20).concat("...");
        }
        if (!pjp.getSignature().getName().equals("echo")) {
            logger.info("ip:{}--{}" +
                    "请求地址 : {},\n" +
                    "\t\t\t\tHTTP METHOD : {}，\n" +
                    "\t\t\t\tCLASS_METHOD : {}，\n" +
                    "\t\t\t\t参数 : {}，\n",request.getRemoteAddr(), requestId, request.getRequestURL().toString(), request.getMethod(), pjp.getSignature().getDeclaringTypeName() + "."
                    + pjp.getSignature().getName(), args);
        }

        Object ob = null;
        try {
            ob = pjp.proceed();
        } catch (Exception e) {
            throw new BusinessException(500,e.getMessage());
        }
        String obstr = ob.toString();
        if (obstr.getBytes(StandardCharsets.UTF_8).length > 1024) {
            obstr = obstr.substring(0, 20).concat("...");
        }
        if (!pjp.getSignature().getName().equals("echo")) {
            logger.info("{}----耗时 : {}ms，\n" +
                    "\t\t\t\t返回值：{}", requestId, System.currentTimeMillis() - startTime, obstr);
        }
        return ob;
    }
}
