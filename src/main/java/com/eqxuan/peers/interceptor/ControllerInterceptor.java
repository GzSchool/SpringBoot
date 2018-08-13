package com.eqxuan.peers.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @Auther: Mature
 * @Date: 2018/8/12 15:32
 * @Description: 接口耗时拦截切面
 */
@Aspect
@Component
public class ControllerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);

    /**
     * 定义一个切入点. 解释下：
     *
     * ~ 第一个 * 代表任意修饰符及任意返回值. ~ 第二个 * 定义在web包或者子包 ~ 第三个 * 任意方法 ~ .. 匹配任意数量的参数.
     */
    static final String POINT_CUT = "execution(* com.eqxuan.peers.controller.*..*(..))";

    @Pointcut(value = POINT_CUT)
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            if(result==null){
                //如果切到了 没有返回类型的void方法，这里直接返回
                return null;
            }
            long end = System.currentTimeMillis();

            logger.info("============================================================================================");
            String tragetClassName = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();

            String typeStr = joinPoint.getSignature().getDeclaringType().toString().split(" ")[0];
            logger.info("类/接口:" + tragetClassName + "(" + typeStr + ")");
            logger.info("方法:" + methodName);

            Long total = end - start;
            logger.info("耗时: " + total + " ms!");
            logger.info("============================================================================================");

            return result;

        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            logger.info("====around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
            throw e;
        }

    }

}
