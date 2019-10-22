package com.cwks.bizcore.sjjh.core.vo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * BeansUtils
 *
 * @author CSSNJ
 * @version 1.0
 */
@Component
public class BeansUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T> T getBean(Class<T> bean) {
        return context.getBean(bean);
    }

    public static <T> T getBean(String var1, @Nullable Class<T> var2) {
        return context.getBean(var1, var2);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        BeansUtils.context = context;
    }
}


