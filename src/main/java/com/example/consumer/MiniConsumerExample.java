package com.example.consumer;

import com.example.common.model.Employee;
import com.example.common.service.EmployeeService;
import com.example.consumer.proxy.EmployeeStaticProxy;
import com.example.consumer.proxy.ServiceDynamicProxy;
import com.example.consumer.proxy.ServiceProxyFactory;

/**
 * @author https://github.com/xuzhixing181
 * description: mini版服务消费者示例
 */
public class MiniConsumerExample {

    public static void main(String[] args) {
        Employee emp = Employee.builder()
                .eid(20068L)
                .ename("张三")
                .build();

//        Employee newEmp = employeeService.getEmployee(emp);

        // 基于静态代理调用目标方法
//        EmployeeStaticProxy staticProxy = new EmployeeStaticProxy();
//        Employee newEmp = staticProxy.getEmployee(emp);

        // 动态代理 + 工厂模式
        EmployeeService employeeService = ServiceProxyFactory.getProxy(EmployeeService.class);
        Employee newEmp = employeeService.getEmployee(emp);
        if (newEmp != null) {
            System.out.println("newEmp =" + newEmp);
        }else {
            System.out.println("newEmp为空");
        }

    }
}