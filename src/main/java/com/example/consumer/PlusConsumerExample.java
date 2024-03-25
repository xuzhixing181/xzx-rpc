package com.example.consumer;

import com.example.plusrpc.config.RpcConfig;
import com.example.plusrpc.utils.ConfigUtils;

/**
 * @author https://github.com/xuzhixing181
 */
public class PlusConsumerExample {

    public static void main(String[] args) {
//        RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 配置文件中的属性要和实体对象中的属性名对应
        // 1)优先读取 application-dev.properties中, xzx.rpc为前缀的配置
        // 2)如果没有读取到对应配置,则RpcConfig中的其他属性由RpcConfig中的属性填充
        RpcConfig devConfig = ConfigUtils.loadConfig(RpcConfig.class, "xzx.rpc","dev");
        System.out.println(devConfig);

        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "xzx.rpc");
        System.out.println(rpcConfig);
    }
}