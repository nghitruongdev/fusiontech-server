package com.vnco.fusiontech.common.utils;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils implements ApplicationContextAware {
    private static ApplicationContext context;
    
    @Override
    public void setApplicationContext(@NotNull ApplicationContext context) throws BeansException {
        BeanUtils.context = context;
    }

    public static <T> T getBean(@NonNull Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
