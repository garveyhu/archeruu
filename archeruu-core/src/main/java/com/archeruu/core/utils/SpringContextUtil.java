package com.archeruu.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * springcontext上下文
 *
 * @author Archer
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 检测spring是否激活状态
     * @return 激活状态
     */
    public static boolean isActive() {
        return applicationContext != null && ((AbstractApplicationContext) applicationContext).isActive();
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 获取配置文件配置的项
     */
    public static String getEnvironmentProperty(String key) {
        return SpringContextUtil.getApplicationContext().getEnvironment().getProperty(key);
    }

    /**
     * 获取spring.profiles.active
     */
    public static String getActiveProfiles() {
        return SpringContextUtil.getApplicationContext().getEnvironment().getActiveProfiles()[0];
    }
}
