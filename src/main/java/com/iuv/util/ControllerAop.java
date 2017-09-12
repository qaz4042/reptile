/*
package com.iuv.handler;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.geccocrawler.gecco.annotation.PipelineName;


import java.lang.reflect.Method;

@Aspect
@Component
public class ControllerAop {

    //private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAop.class);

    @Pointcut("@annotation(com.geccocrawler.gecco.annotation.PipelineName)")
    public void cutPipelineName() {
    }


    @Around("cutPipelineName()")
    public Object cutPipelineName(ProceedingJoinPoint point) throws Throwable {
        try {
            Object returnObj = point.proceed();
            return returnObj;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Method getMethod(ProceedingJoinPoint point) {
        Method method = null;

        Object target = point.getTarget();
        Signature sig = point.getSignature();

        if (sig instanceof MethodSignature) {
            MethodSignature msig = (MethodSignature) sig;
            try {
                method = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return method;
    }
}
*/
