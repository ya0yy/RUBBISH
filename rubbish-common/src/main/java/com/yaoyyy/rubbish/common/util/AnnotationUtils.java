package com.yaoyyy.rubbish.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: YaoYY
 * @create: 2019-02-17 10:22
 */
public class AnnotationUtils extends org.springframework.core.annotation.AnnotationUtils {

    /**
     *  传入实例对象，返回属性值的map
     */

    /**
     * @param annotationClass 注解类型
     * @param tClass 返回对象的类型
     * @return java.util.Set<T>
     * @author YaoYY
     */
    public static <T> Set<T> getInstancesFromContext(Class annotationClass, Class<T> tClass) throws Exception {
        Set<T> instances = new LinkedHashSet<>();
        Map<String, Object> objectMap = getObjectFromContext(annotationClass);
        for (String s : objectMap.keySet()) {
            Object o = objectMap.get(s);
            if (tClass.isInstance(o)) {
                T t = tClass.cast(o);
                instances.add(t);
            }
        }
        return instances;
    }

    /**
     * 从Spring容器中拿到annotationClass的component
     * @param annotationClass 注解类型
     * @return java.util.Map<beanName,bean>
     * @author YaoYY
     */
    public static Map<String, Object> getObjectFromContext(Class annotationClass) throws Exception {
        Map<String, Object> map = new LinkedHashMap<>();
        // 获取ApplicationContext
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext();
        return applicationContext.getBeansWithAnnotation(annotationClass);
    }

    /**
     * @param location {@link AnnotationUtils#getAnnotationClass(java.lang.String, java.lang.Class)}
     * @param annotationClass 需要查找的注解的class
     * @param tClass 需要实例化的类型
     * @return tClass的实例
     * @author YaoYY
     */
    public static <T> Set<T> getAnnotationInstances(String location, Class annotationClass, Class<T> tClass) throws Exception {
        Set<T> instances = new LinkedHashSet<>();
        Object[] objects = getAnnotationObject(location, annotationClass);
        for (Object o : objects) {
            if (tClass.isInstance(o)) {
                // 如果o是tClass的实例，就强转为tClass的类型
                T t = tClass.cast(o);
                instances.add(t);
            }
        }
        return instances;
    }

    /**
     * 注意：！！！！需要实例的对象请确保该类有空参构造器！！！！
     * @param location {@link AnnotationUtils#getAnnotationClass(java.lang.String, java.lang.Class)}
     * @param annotationClass 需要查找的注解的class
     * @return 返回指定包下所有该注解的Object类型对象
     * @author YaoYY
     */
    public static Object[] getAnnotationObject(String location, Class annotationClass) throws Exception {
        Set<Object> objects = new LinkedHashSet<>();
        Class[] clazzs = getAnnotationClass(location, annotationClass);
        for (Class clazz : clazzs) {
            objects.add(clazz.newInstance());
        }
        return objects.toArray(new Object[0]);
    }

    /**
     * @param location 需要解析的包的类路径，例如classpath*:com/yao/main/*  或 com/yao/main/**.class
     * @param annotationClass 需要查找的注解的class
     * @return 返回指定包下所有该注解的class对象
     * @author YaoYY
     */
    public static Class[] getAnnotationClass(String location, Class annotationClass) throws Exception {
        Set<Object> clazzs = new LinkedHashSet<>();
        Resource[] resources = getResource(location);
        // 使用Spring提供的简单元数据读取工厂
        SimpleMetadataReaderFactory readerFactory = new SimpleMetadataReaderFactory();
        for (Resource resource : resources) {
            // 通过resource拿到元数据读取器
            MetadataReader metadataReader = readerFactory.getMetadataReader(resource);
            // 通过元数据读取器拿到注解元数据
            AnnotationMetadata metadata = metadataReader.getAnnotationMetadata();
            if (metadata.hasAnnotation(annotationClass.getName())) {
                // 如果包含这个注解就创建它的class对象
                String className = metadata.getClassName();
                clazzs.add(Class.forName(className));
            }
        }
        return clazzs.toArray(new Class[0]);
    }

    /**
     * @description:  传入一个类路径（classpath*:）返回该路径下所有class的Resource
     * @author: YaoYY
     */
    private static Resource[] getResource(String location) {
        Resource[] resources = null;
        // Spring的通配符资源解析器
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            resources = resolver.getResources(location);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

}
