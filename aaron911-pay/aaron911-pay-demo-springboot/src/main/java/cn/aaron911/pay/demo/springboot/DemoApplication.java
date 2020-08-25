package cn.aaron911.pay.demo.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import cn.aaron911.pay.demo.springboot.config.StartupRunner;

/**
 *
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public StartupRunner startupRunner() {
        return new StartupRunner();
    }
}


