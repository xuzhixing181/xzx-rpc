package com.example.consumer.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.example.common.model.Employee;
import com.example.common.service.EmployeeService;
import com.example.plusrpc.model.RpcRequest;
import com.example.plusrpc.model.RpcResponse;
import com.example.plusrpc.serial.JdkSerializer;

import java.io.IOException;

/**
 * 静态代理: 为每个特定类型的接口或对象,编写一个实现类 ==> EmployeeStaticProxy
 * @author  https://github.com/xuzhixing181
 */
public class EmployeeStaticProxy implements EmployeeService {
    @Override
    public Employee getEmployee(Employee employee) {
        JdkSerializer jdkSerializer = new JdkSerializer();

        // 1.构建Rpc请求对象
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(EmployeeService.class.getName())
                .methodName("getEmployee")
                .parameterTypes(new Class[]{Employee.class})
                .args(new Object[]{employee})
                .build();

        try {
            // 2.将RpcRequest对象序列化为字节数组
            byte[] bytes = jdkSerializer.serialize(rpcRequest);
            // 3.发送请求
            HttpResponse httpResponse = HttpRequest.post("http://localhost:8090").body(bytes).execute();
            byte[] resultBytes = httpResponse.bodyBytes();
            // 4.将响应结果反序列化为RpcResponse对象
            RpcResponse rpcResponse = jdkSerializer.deserialize(resultBytes, RpcResponse.class);
            return (Employee) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}