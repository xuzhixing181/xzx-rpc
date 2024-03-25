package com.example.consumer.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.example.plusrpc.model.RpcRequest;
import com.example.plusrpc.model.RpcResponse;
import com.example.plusrpc.serial.JdkSerializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 基于JDK实现动态代理: 根据要生成对象的类型，自动生成一个代理对象
 *      实现: 实现InvocationHandler接口,重写 invoke(Object proxy, Method method, Object[] args)方法
 * @author https://github.com/xuzhixing181
 */
public class ServiceDynamicProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        JdkSerializer jdkSerializer = new JdkSerializer();
        /**
         * 当用户调用某个接口的方法时,会触发调用invoke方法,在invoke方法中,可获取到要调用的方法信息,传入的参数列表(provider需要的参数)等
         */
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {
            byte[] bytes = jdkSerializer.serialize(rpcRequest);
            // todo 后续会使用注册中心和服务发现机制解决
            HttpResponse httpResponse = HttpRequest.post("http://localhost:8090").body(bytes).execute();

            RpcResponse rpcResponse = jdkSerializer.deserialize(httpResponse.bodyBytes(), RpcResponse.class);
            return rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}