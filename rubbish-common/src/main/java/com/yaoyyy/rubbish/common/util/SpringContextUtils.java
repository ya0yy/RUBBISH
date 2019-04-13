package com.yaoyyy.rubbish.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 获取Spring容器的工具类
 * @author: YaoYY
 * @create: 2019-02-18 14:17
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    // autowired不支持静态字段
    private static ApplicationContext applicationContext;

    private SpringContextUtils(){
        System.err.println("构造方法");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    public synchronized static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return SpringContextUtils.applicationContext;
    }

    public synchronized static <T> T getBean(Class<T> type) {
        assertContextInjected();
        return applicationContext.getBean(type);
    }

    private static void assertContextInjected() {
        Assert.isTrue(SpringContextUtils.applicationContext != null, "容器为空！");
    }

}
