package com.iuv.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        context = arg0;
    }

    /**
     * 根据类型读取BEAN
     **/
    public static <T> T getBean(Class<T> cla) {
        return context.getBean(cla);
    }

    /**
     * 根据名称读取BEAN
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationClass) {
        return context.getBeansWithAnnotation(annotationClass);
    }
}
