package cn.aaron911.rpc.example.client;

import cn.aaron911.rpc.annotation.RpcScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 07:25:00
 */
@RpcScan(basePackage = {"github.javaguide"})
public class NettyClientMain {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClientMain.class);
//        HelloController helloController = (HelloController) applicationContext.getBean("helloController");
//        helloController.test();

        cn.aaron911.rpc.example.client.YjkController yjkController = applicationContext.getBean(cn.aaron911.rpc.example.client.YjkController.class);
        yjkController.test();

    }
}
