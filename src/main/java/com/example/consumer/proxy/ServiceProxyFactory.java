package com.example.consumer.proxy;

import java.lang.reflect.Proxy;

/**
 * 创建动态代理工厂,作用: 可根据指定类创建动态代理对象
 * @author https://github.com/xuzhixing181
 */
public class ServiceProxyFactory {

    public static <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class<?>[]{clazz}, new ServiceDynamicProxy());
    }

}